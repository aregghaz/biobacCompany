package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Source;
import com.biobac.company.mapper.SourceMapper;
import com.biobac.company.repository.SourceRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.SourceResponse;
import com.biobac.company.service.SourceService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final SourceMapper sourceMapper;

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
    public SourceResponse createSource(SourceRequest request) {
        Source source = sourceMapper.toSourceEntity(request);
        Source savedSource = sourceRepository.save(source);
        return sourceMapper.toResponse(savedSource);
    }

    @Override
    public SourceResponse getSourceById(Long id) {
        return sourceRepository.findById(id)
                .map(sourceMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Source not found"));
    }

    @Override
    public List<SourceResponse> getAll() {
        return sourceRepository.findAll().stream().map(sourceMapper::toResponse).toList();
    }

    @Override
    public Pair<List<SourceResponse>, PaginationMetadata> getLinePagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<Source> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<Source> pg = sourceRepository.findAll(spec, pageable);
        List<SourceResponse> content = pg.getContent().stream().map(sourceMapper::toResponse).toList();
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "lineTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    public SourceResponse updateSource(Long id, SourceRequest request) {
        return sourceRepository.findById(id)
                .map(source -> {
                    source.setName(request.getName());
                    return sourceMapper.toResponse(sourceRepository.save(source));
                })
                .orElseThrow(() -> new RuntimeException("Source not found"));
    }

    @Override
    public void delete(Long id) {
        sourceRepository.deleteById(id);
    }
}
