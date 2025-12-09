package com.biobac.company.service.impl;

import com.biobac.company.client.ProductClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.*;
import com.biobac.company.exception.ExternalServiceException;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.SaleMapper;
import com.biobac.company.repository.*;
import com.biobac.company.request.*;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SaleResponse;
import com.biobac.company.service.SaleService;
import com.biobac.company.utils.specifications.SaleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final SaleMapper saleMapper;
    private final OurCompanyRepository ourCompanyRepository;
    private final CompanyRepository companyRepository;
    private final SaleStatusRepository saleStatusRepository;
    private final ProductClient productClient;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    private Pageable buildPageable(Integer page, Integer size, String sortBy, String sortDir) {
        int safePage = page == null || page < 0 ? DEFAULT_PAGE : page;
        int safeSize = size == null || size <= 0 ? DEFAULT_SIZE : size;
        String safeSortBy = (sortBy == null || sortBy.isBlank()) ? DEFAULT_SORT_BY : sortBy;
        String sd = (sortDir == null || sortDir.isBlank()) ? DEFAULT_SORT_DIR : sortDir;
        Sort sort = sd.equalsIgnoreCase("asc") ? Sort.by(safeSortBy).ascending() : Sort.by(safeSortBy).descending();
        if (safeSize > 1000) {
            safeSize = 1000;
        }
        return PageRequest.of(safePage, safeSize, sort);
    }

    private Pair<List<SaleResponse>, PaginationMetadata> paginateSales(
            Map<String, FilterCriteria> filters,
            Integer page, Integer size,
            String sortBy, String sortDir,
            Specification<Sale> extraSpec
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);

        Specification<Sale> spec = SaleSpecification.buildSpecification(filters);
        if (extraSpec != null) {
            spec = spec.and(extraSpec);
        }

        Page<Sale> pg = saleRepository.findAll(spec, pageable);

        List<SaleResponse> content =
                pg.getContent().stream().map(saleMapper::toResponse).toList();

        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "saleTable"
        );

        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<SaleResponse>, PaginationMetadata> getFinalizedPagination(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        return paginateSales(
                filters,
                page, size, sortBy, sortDir,
                SaleSpecification.filterFinalizedSales()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<SaleResponse>, PaginationMetadata> getPendingPagination(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        return paginateSales(
                filters,
                page, size, sortBy, sortDir,
                SaleSpecification.filterPendingSales()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found"));
        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse createOnSiteSale(OnSiteSaleRequest request) {

        Sale sale = initializeSale(
                request.getOurCompanyId(),
                request.getBuyerCompanyId(),
                request.getTotalAmount(),
                resolveReceivedAmount(request.getReceivedAmount())
        );

        List<ProductConsumeSaleRequest> consumeRequests =
                saveSaleItems(request.getItems(), sale);

        consumeProducts(consumeRequests);

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse createPreSale(PreSaleRequest request) {
        Sale sale = initializeSale(
                request.getOurCompanyId(),
                request.getBuyerCompanyId(),
                request.getTotalAmount(),
                BigDecimal.ZERO
        );

        saveSaleItems(request.getItems(), sale);

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse finalizeSale(FinalizeSaleRequest request) {

        Sale sale = saleRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Sale not found"));

        BigDecimal received = resolveReceivedAmount(request.getReceivedAmount());

        sale.setReceivedAmount(received);
        sale.setStatus(resolveSaleStatus(received, sale.getTotalAmount()));

        saleRepository.save(sale);

        List<ProductConsumeSaleRequest> consumeRequests =
                buildConsumeRequests(sale.getItems());

        consumeProducts(consumeRequests);

        return saleMapper.toResponse(sale);
    }

    private Sale initializeSale(Long ourCompanyId,
                                Long buyerCompanyId,
                                BigDecimal totalAmount,
                                BigDecimal receivedAmount) {

        OurCompany ourCompany = fetchOurCompany(ourCompanyId);
        Company buyer = fetchBuyerCompany(buyerCompanyId);

        Sale sale = new Sale();
        sale.setOurCompany(ourCompany);
        sale.setBuyerCompany(buyer);
        sale.setTotalAmount(totalAmount);
        sale.setReceivedAmount(receivedAmount);
        sale.setStatus(resolveSaleStatus(receivedAmount, totalAmount));

        return saleRepository.save(sale);
    }

    private BigDecimal resolveReceivedAmount(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }

    private SaleStatus resolveSaleStatus(BigDecimal received, BigDecimal total) {

        long statusId;

        if (received.compareTo(BigDecimal.ZERO) == 0) {
            statusId = 2L;
        } else if (received.compareTo(total) == 0) {
            statusId = 1L;
        } else {
            statusId = 3L;
        }

        return saleStatusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status not found"));
    }

    private List<ProductConsumeSaleRequest> saveSaleItems(
            List<SaleItemRequest> items, Sale sale) {

        List<ProductConsumeSaleRequest> consumeRequests = new ArrayList<>();

        List<SaleItem> saleItems = items.stream().map(i -> {
            SaleItem item = new SaleItem();
            item.setProductId(i.getProductId());
            item.setQuantity(i.getQuantity());
            item.setUnitPrice(i.getUnitPrice());
            item.setTotalPrice(i.getTotalPrice());
            item.setSale(sale);

            saleItemRepository.save(item);

            ProductConsumeSaleRequest req = new ProductConsumeSaleRequest();
            req.setProductId(i.getProductId());
            req.setQuantity(i.getQuantity());
            consumeRequests.add(req);

            return item;
        }).toList();

        sale.setItems(saleItems);
        return consumeRequests;
    }

    private List<ProductConsumeSaleRequest> buildConsumeRequests(List<SaleItem> items) {
        return items.stream().map(i -> {
            ProductConsumeSaleRequest req = new ProductConsumeSaleRequest();
            req.setProductId(i.getProductId());
            req.setQuantity(i.getQuantity());
            return req;
        }).toList();
    }

    private void consumeProducts(List<ProductConsumeSaleRequest> requests) {
        ApiResponse<String> response = productClient.consumeProductForSale(requests);
        if (!response.getSuccess()) {
            throw new ExternalServiceException(response.getMessage());
        }
    }

    private OurCompany fetchOurCompany(Long id) {
        return ourCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Our company not found"));
    }

    private Company fetchBuyerCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Buyer not found"));
    }
}
