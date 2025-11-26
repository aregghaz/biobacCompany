package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Cooperation;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.CooperationMapper;
import com.biobac.company.repository.CooperationRepository;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CooperationResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.CooperationService;
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
public class CooperationServiceImpl implements CooperationService {
    private final CooperationRepository repository;
    private final CooperationMapper cooperationMapper;

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

    private SimpleNameResponse toResponse(Cooperation entity) {
        return new SimpleNameResponse(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CooperationResponse> getAllCooperation() {
        return repository.findAll().stream().map(cooperationMapper::toCooperationResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<CooperationResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<Cooperation> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<Cooperation> pg = repository.findAll(spec, pageable);
        List<CooperationResponse> content = pg.getContent().stream().map(cooperationMapper::toCooperationResponse)
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
                "cooperationTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public CooperationResponse getCooperationById(Long id) {
        return repository.findById(id)
                .map(cooperationMapper::toCooperationResponse)
                .orElseThrow(() -> new NotFoundException("Cooperation not found"));
    }

    @Override
    @Transactional
    public CooperationResponse create(CooperationRequest request) {
        Cooperation cooperation = cooperationMapper.toCooperationEntity(request);
        Cooperation saved = repository.save(cooperation);
        return cooperationMapper.toCooperationResponse(saved);
    }

    @Override
    @Transactional
    public CooperationResponse update(Long id, CooperationRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cooperation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cooperation not found"));
        repository.delete(entity);
    }
}
