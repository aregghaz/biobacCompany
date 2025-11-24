package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.DeliveryMethod;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.DeliveryMethodMapper;
import com.biobac.company.repository.DeliveryMethodRepository;
import com.biobac.company.request.DeliveryMethodRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.DeliveryMethodResponse;
import com.biobac.company.service.DeliveryMethodService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryMethodServiceImpl implements DeliveryMethodService {
    private final DeliveryMethodRepository repository;
    private final DeliveryMethodMapper deliveryMethodMapper;

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

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMethodResponse> getAllDeliveryMethods() {
        return repository.findAll().stream()
                .map(deliveryMethodMapper::toDeliveryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<DeliveryMethodResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<DeliveryMethod> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<DeliveryMethod> pg = repository.findAll(spec, pageable);
        List<DeliveryMethodResponse> content = pg.getContent().stream().map(deliveryMethodMapper::toDeliveryResponse)
                .collect(Collectors.toList());
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "deliveryMethodTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    public DeliveryMethodResponse getDeliveryMethodById(Long id) {
        return null;
    }

    @Override
    public DeliveryMethodResponse createDeliveryMethod(DeliveryMethodRequest request) {
        DeliveryMethod deliveryMethod = deliveryMethodMapper.toDeliveryMethodEntity(request);
        DeliveryMethod saved = repository.save(deliveryMethod);
        return deliveryMethodMapper.toDeliveryResponse(saved);
    }

    @Override
    @Transactional
    public DeliveryMethodResponse update(Long id, DeliveryMethodRequest request) {
        DeliveryMethod entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DeliveryMethod not found"));
        entity.setName(request.getName());
        DeliveryMethod saved = repository.save(entity);
        return deliveryMethodMapper.toDeliveryResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DeliveryMethod entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DeliveryMethod not found"));
        repository.delete(entity);
    }
}
