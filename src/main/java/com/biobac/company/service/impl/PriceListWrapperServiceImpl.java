package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.PriceListItem;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PriceListItemMapper;
import com.biobac.company.mapper.PriceListWrapperMapper;
import com.biobac.company.repository.PriceListWrapperRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PriceListItemRequest;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.PriceListWrapperResponse;
import com.biobac.company.service.PriceListItemService;
import com.biobac.company.service.PriceListWrapperService;
import com.biobac.company.utils.GroupUtil;
import com.biobac.company.utils.specifications.CompanySpecification;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceListWrapperServiceImpl implements PriceListWrapperService {

    private final PriceListWrapperRepository priceListWrapperRepository;
    private final PriceListItemService priceListItemService;
    private final PriceListWrapperMapper priceListWrapperMapper;
    private final PriceListItemMapper priceListItemMapper;
    private final GroupUtil groupUtil;

    @Override
    @Transactional
    public PriceListWrapperResponse createPriceListWrapper(PriceListWrapperRequest request) {
        PriceListWrapper priceListWrapper = priceListWrapperMapper.toPriceListWrapper(request);
        PriceListWrapper save = priceListWrapperRepository.save(priceListWrapper);

        List<PriceListItem> priceListItems = new ArrayList<>();

        if (request.getPriceListItems() != null && !request.getPriceListItems().isEmpty()) {
            request.getPriceListItems().forEach(priceListItem -> {
                PriceListItem item = priceListItemService.createPriceListItem(priceListItem, priceListWrapper);
                priceListItems.add(item);
            });
        }

        save.setPriceListItems(priceListItems);
        return priceListWrapperMapper.toPriceListWrapperResponse(save);
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
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<PriceListWrapper> spec = PriceListWrapperSpecification.buildSpecification(filters)
                .and(PriceListWrapperSpecification.belongsToGroups(groupIds));

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

        Map<Long, PriceListItem> existingItems = wrapper.getPriceListItems().stream()
                .collect(Collectors.toMap(PriceListItem::getId, i -> i));

        List<PriceListItem> updatedList = new ArrayList<>();

        for (PriceListItemRequest itemRequest : request.getPriceListItems()) {

            if (itemRequest.getId() != null && existingItems.containsKey(itemRequest.getId())) {
                PriceListItem existing = existingItems.get(itemRequest.getId());
                priceListItemMapper.updatePriceListItem(itemRequest, existing);
                existing.setPriceListWrapper(wrapper);
                updatedList.add(existing);

            } else {
                PriceListItem newItem = priceListItemMapper.toPriceListItemEntity(itemRequest);
                newItem.setPriceListWrapper(wrapper);
                updatedList.add(newItem);
            }
        }

        wrapper.getPriceListItems().clear();
        wrapper.getPriceListItems().addAll(updatedList);

        PriceListWrapper saved = priceListWrapperRepository.save(wrapper);

        return priceListWrapperMapper.toPriceListWrapperResponse(saved);
    }

    @Override
    public void deletePriceListWrapper(Long id) {
        priceListWrapperRepository.deleteById(id);
    }

}
