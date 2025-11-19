package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.DeliveryPayer;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.repository.DeliveryPayerRepository;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.DeliveryPayerService;
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
public class DeliveryPayerServiceImpl implements DeliveryPayerService {
    private final DeliveryPayerRepository repository;

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

    private SimpleNameResponse toResponse(DeliveryPayer entity) {
        return new SimpleNameResponse(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<SimpleNameResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<DeliveryPayer> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<DeliveryPayer> pg = repository.findAll(spec, pageable);
        List<SimpleNameResponse> content = pg.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "deliveryPayerTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleNameResponse getById(Long id) {
        DeliveryPayer entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DeliveryPayer not found"));
        return toResponse(entity);
    }

    @Override
    @Transactional
    public SimpleNameResponse create(CooperationRequest request) {
        DeliveryPayer entity = new DeliveryPayer();
        entity.setName(request.getName());
        DeliveryPayer saved = repository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public SimpleNameResponse update(Long id, CooperationRequest request) {
        DeliveryPayer entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DeliveryPayer not found"));
        entity.setName(request.getName());
        DeliveryPayer saved = repository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DeliveryPayer entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DeliveryPayer not found"));
        repository.delete(entity);
    }
}
