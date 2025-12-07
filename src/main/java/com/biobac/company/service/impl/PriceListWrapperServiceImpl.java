package com.biobac.company.service.impl;

import com.biobac.company.client.ProductClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PriceListItemMapper;
import com.biobac.company.mapper.PriceListWrapperMapper;
import com.biobac.company.repository.PriceListWrapperRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PriceListWrapperResponse;
import com.biobac.company.response.ProductResponse;
import com.biobac.company.service.PriceListItemService;
import com.biobac.company.service.PriceListWrapperService;
import com.biobac.company.utils.specifications.PriceListWrapperSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceListWrapperServiceImpl implements PriceListWrapperService {

    private final PriceListWrapperRepository priceListWrapperRepository;
    private final PriceListItemService priceListItemService;
    private final PriceListWrapperMapper priceListWrapperMapper;
    private final PriceListItemMapper priceListItemMapper;
    private final ProductClient productClient;

    @Override
    @Transactional
    public PriceListWrapperResponse createPriceListWrapper(PriceListWrapperRequest request) {
        PriceListWrapper priceListWrapper = priceListWrapperMapper.toPriceListWrapper(request);
        PriceListWrapper save = priceListWrapperRepository.save(priceListWrapper);

        List<ProductResponse> products = new ArrayList<>();

        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            List<ProductResponse> productResponses = request.getProductIds().stream()
                    .map(productId -> {
                        ApiResponse<ProductResponse> response = productClient.getProductById(productId);
                        ProductResponse product = response.getData();

                        if (product == null) {
                            throw new NotFoundException(String.format("Product with id %s not found", productId));
                        }
                        return product;
                    })
                    .toList();
            products.addAll(productResponses);
        }


//        List<PriceListItem> priceListItems = new ArrayList<>();
//
//        if (request.getPriceListItems() != null && !request.getPriceListItems().isEmpty()) {
//            request.getPriceListItems().forEach(priceListItem -> {
//                PriceListItem item = priceListItemService.createPriceListItem(priceListItem, priceListWrapper);
//                priceListItems.add(item);
//            });
//        }

//        save.setPriceListItems(priceListItems);
        PriceListWrapperResponse response = priceListWrapperMapper.toPriceListWrapperResponse(save);
        response.setProduct(products);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListWrapperResponse getPriceListWrapperById(Long id) {
        return priceListWrapperRepository.findById(id)
                .map(item -> priceListWrapperMapper.toPriceListWrapperResponse(item))
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
                .map(priceListWrapperMapper::toPriceListWrapperResponse)
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
    public PriceListWrapperResponse updatePriceListWrapper(Long id, PriceListWrapperRequest request) {
        PriceListWrapper wrapper = priceListWrapperRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Price list not found"));

        priceListWrapperMapper.updatePriceListWrapper(request, wrapper);
        if (wrapper.getPriceListItems() == null) {
            wrapper.setPriceListItems(new ArrayList<>());
        }

//        Map<Long, PriceListItem> existingItems = wrapper.getPriceListItems().stream()
//                .collect(Collectors.toMap(PriceListItem::getId, i -> i));
//
//        List<PriceListItem> updatedList = new ArrayList<>();
//
//        for (PriceListItemRequest itemRequest : request.getPriceListItems()) {
//
//            if (itemRequest.getId() != null && existingItems.containsKey(itemRequest.getId())) {
//                PriceListItem existing = existingItems.get(itemRequest.getId());
//                priceListItemMapper.updatePriceListItem(itemRequest, existing);
//                existing.setPriceListWrapper(wrapper);
//                updatedList.add(existing);
//
//            } else {
//                PriceListItem newItem = priceListItemMapper.toPriceListItemEntity(itemRequest);
//                newItem.setPriceListWrapper(wrapper);
//                updatedList.add(newItem);
//            }
//        }
//
//        wrapper.getPriceListItems().clear();
//        wrapper.getPriceListItems().addAll(updatedList);

        PriceListWrapper saved = priceListWrapperRepository.save(wrapper);

        return priceListWrapperMapper.toPriceListWrapperResponse(saved);
    }

    @Override
    public void deletePriceListWrapper(Long id) {
        priceListWrapperRepository.deleteById(id);
    }

}
