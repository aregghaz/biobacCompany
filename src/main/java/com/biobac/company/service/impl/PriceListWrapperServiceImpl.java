package com.biobac.company.service.impl;

import com.biobac.company.client.ProductClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.PriceListItem;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PriceListWrapperMapper;
import com.biobac.company.repository.PriceListWrapperRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.request.ProductRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PriceListWrapperResponse;
import com.biobac.company.response.ProductResponse;
import com.biobac.company.service.PriceListWrapperService;
import com.biobac.company.utils.ProductClientUtil;
import com.biobac.company.utils.specifications.PriceListWrapperSpecification;
import lombok.NonNull;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PriceListWrapperServiceImpl implements PriceListWrapperService {

    private final PriceListWrapperRepository priceListWrapperRepository;
    private final PriceListWrapperMapper priceListWrapperMapper;
    private final ProductClient productClient;

    @Override
    @Transactional
    public PriceListWrapperResponse createPriceListWrapper(PriceListWrapperRequest request) {
        PriceListWrapper priceListWrapper = priceListWrapperMapper.toPriceListWrapper(request);
        List<ProductResponse> products = new ArrayList<>();
        List<PriceListItem> priceListItems = new ArrayList<>();

        processProductRequests(request, products, priceListWrapper, priceListItems);
        priceListWrapper.setPriceListItems(priceListItems);

        PriceListWrapper save = priceListWrapperRepository.save(priceListWrapper);

        PriceListWrapperResponse response = priceListWrapperMapper.toPriceListWrapperResponse(save);
        response.setProduct(products);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListWrapperResponse getPriceListWrapperById(Long id) {
        return priceListWrapperRepository.findById(id)
                .map(buildPriceListResponse())
                .orElseThrow(() -> new NotFoundException("Price list not found"));
    }

    @Override
    public Pair<List<PriceListWrapperResponse>, PaginationMetadata> getPriceListWrapperPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<PriceListWrapper> spec = PriceListWrapperSpecification.buildSpecification(filters);

        Page<PriceListWrapper> companyPage = priceListWrapperRepository.findAll(spec, pageable);

        List<PriceListWrapperResponse> content = companyPage.getContent()
                .stream()
                .map(buildPriceListResponse())
                .toList();

        PaginationMetadata metadata = new PaginationMetadata(
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages(),
                companyPage.isLast(),
                filters,
                sortDir,
                sortBy,
                "companyTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    public List<PriceListWrapperResponse> getAllPriceList() {
        return priceListWrapperRepository.findAll()
                .stream()
                .map(priceList -> priceListWrapperMapper.toPriceListWrapperResponse(priceList))
                .toList();
    }

    @Override
    public PriceListWrapperResponse updatePriceListWrapper(Long id, PriceListWrapperRequest request) {
        PriceListWrapper priceListWrapper = priceListWrapperRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Price list not found"));

        priceListWrapperMapper.updatePriceListWrapper(request, priceListWrapper);

        List<ProductResponse> products = new ArrayList<>();
        List<PriceListItem> priceListItems = new ArrayList<>();

        processProductRequests(request, products, priceListWrapper, priceListItems);

        if (priceListWrapper.getPriceListItems() == null) {
            priceListWrapper.setPriceListItems(new ArrayList<>());
        }
        priceListWrapper.getPriceListItems().clear();
        priceListWrapper.getPriceListItems().addAll(priceListItems);

        PriceListWrapper saved = priceListWrapperRepository.save(priceListWrapper);

        PriceListWrapperResponse response = priceListWrapperMapper.toPriceListWrapperResponse(saved);
        response.setProduct(products);
        return response;
    }

    @Override
    public void deletePriceListWrapper(Long id) {
        priceListWrapperRepository.deleteById(id);
    }


    private void processProductRequests(
            PriceListWrapperRequest request,
            List<ProductResponse> products,
            PriceListWrapper priceListWrapper,
            List<PriceListItem> priceListItems
    ) {
        if (request.getProducts() != null && !request.getProducts().isEmpty()) {
            for (ProductRequest productReq : request.getProducts()) {
                if (productReq.getId() == null) continue;

                ApiResponse<ProductResponse> response = productClient.getProductById(productReq.getId());
                ProductResponse product = response.getData();

                BigDecimal priceToSet = productReq.getPrice() != null
                        ? productReq.getPrice()
                        : product.getPrice();

                product.setPrice(priceToSet);
                products.add(product);
                PriceListItem item = PriceListItem.builder()
                        .productId(product.getId())
                        .price(priceToSet)
                        .priceListWrapper(priceListWrapper)
                        .build();
                priceListItems.add(item);
            }
        }
    }

    private @NonNull Function<PriceListWrapper, PriceListWrapperResponse> buildPriceListResponse() {
        return item -> {
            PriceListWrapperResponse response = priceListWrapperMapper.toPriceListWrapperResponse(item);
            if (response != null) {
                List<ProductResponse> products = item.getPriceListItems() != null
                        ? ProductClientUtil.enrichProductsWithPrices(item.getPriceListItems(), productClient)
                        : Collections.emptyList();
                response.setProduct(products);
            }
            return response;
        };
    }
}