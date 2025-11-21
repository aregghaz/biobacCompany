package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.ClientType;
import com.biobac.company.mapper.ClientTypeMapper;
import com.biobac.company.repository.ClientTypeRepository;
import com.biobac.company.request.ClientTypeRequest;
import com.biobac.company.request.CreateClientTypeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CreateClientTypeResponse;
import com.biobac.company.service.ClientTypeService;
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
public class ClientTypeServiceImpl implements ClientTypeService {
    private final ClientTypeRepository repository;
    private final ClientTypeMapper clientTypeMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    @Override
    public CreateClientTypeResponse createClientType(CreateClientTypeRequest request) {
        ClientType clientType = clientTypeMapper.toClientTypeEntity(request);
        ClientType savedClientType = repository.save(clientType);
        return clientTypeMapper.toClientTypeResponse(savedClientType);
    }

    @Override
    public CreateClientTypeResponse update(Long id, ClientTypeRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

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
    public List<CreateClientTypeResponse> getAllClientType() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<CreateClientTypeResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<ClientType> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<ClientType> pg = repository.findAll(spec, pageable);
        List<CreateClientTypeResponse> content = pg.getContent().stream()
                .map(clientType -> clientTypeMapper.toClientTypeResponse(clientType))
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
                "clientTypeTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    public CreateClientTypeResponse getClientTypeById(Long id) {
        return repository.findById(id)
                .map(clientTypeMapper::toClientTypeResponse)
                .orElseThrow(() -> new RuntimeException("Client type not found"));
    }
}
