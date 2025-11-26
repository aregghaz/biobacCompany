package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Line;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.LineMapper;
import com.biobac.company.repository.LineRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.LineResponse;
import com.biobac.company.service.LineService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository repository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";
    private final LineMapper lineMapper;

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
    public LineResponse createLine(LineRequest request) {
        Line entity = lineMapper.toLineEntity(request);
        Line saved = repository.save(entity);
        return lineMapper.toLineResponse(saved);
    }

    @Override
    public LineResponse getLineById(Long id) {
        return repository.findById(id)
                .map(lineMapper::toLineResponse)
                .orElseThrow(() -> new NotFoundException("Line not found"));
    }

    @Override
    public Pair<List<LineResponse>, PaginationMetadata> getLinePagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<Line> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<Line> pg = repository.findAll(spec, pageable);
        List<LineResponse> content = pg.getContent().stream().map(lineMapper::toLineResponse).toList();
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
    public LineResponse updateLine(Long id, LineRequest request) {
        return null;
    }

    @Override
    public List<LineResponse> getAllLine() {
        return repository.findAll().stream().map(lineMapper::toLineResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteLine(Long id) {
        repository.findById(id)
                .ifPresentOrElse((line) -> repository.delete(line), () -> new NotFoundException("Line not found"));
    }
}
