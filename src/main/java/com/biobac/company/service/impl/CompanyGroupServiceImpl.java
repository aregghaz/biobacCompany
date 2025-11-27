package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.CompanyGroupMapper;
import com.biobac.company.repository.CompanyGroupRepository;
import com.biobac.company.request.CompanyGroupRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyGroupResponse;
import com.biobac.company.service.CompanyGroupService;
import com.biobac.company.utils.GroupUtil;
import com.biobac.company.utils.specifications.CompanyGroupSpecification;
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
public class CompanyGroupServiceImpl implements CompanyGroupService {
    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyGroupMapper companyGroupMapper;
    private final GroupUtil groupUtil;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    @Override
    public CompanyGroupResponse createCompanyGroup(CompanyGroupRequest request) {
        CompanyGroup companyGroup = companyGroupMapper.toEntity(request);
        CompanyGroup saved = companyGroupRepository.save(companyGroup);
        return companyGroupMapper.toResponse(saved);
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
    @Transactional(readOnly = true)
    public List<CompanyGroupResponse> getAll() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<CompanyGroup> spec = CompanyGroupSpecification.belongsToGroups(groupIds);
        return companyGroupRepository.findAll(spec).stream().map(companyGroupMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyGroupResponse> getAllCompanyGroup() {
        return companyGroupRepository.findAll().stream().map(companyGroupMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<CompanyGroupResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);

        Specification<CompanyGroup> spec = CompanyGroupSpecification.buildSpecification(filters)
                .and(CompanyGroupSpecification.belongsToGroups(groupIds));

        Page<CompanyGroup> companyGroupPage = companyGroupRepository.findAll(spec, pageable);

        List<CompanyGroupResponse> content = companyGroupPage.getContent().stream()
                .map(companyGroupMapper::toResponse)
                .collect(Collectors.toList());

        PaginationMetadata metadata = new PaginationMetadata(
                companyGroupPage.getNumber(),
                companyGroupPage.getSize(),
                companyGroupPage.getTotalElements(),
                companyGroupPage.getTotalPages(),
                companyGroupPage.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "companyGroupTable"
        );

        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyGroupResponse getById(Long id) {
        CompanyGroup companyGroup = companyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company group not found"));
        return companyGroupMapper.toResponse(companyGroup);
    }

    @Override
    @Transactional
    public CompanyGroupResponse update(Long id, CompanyGroupRequest request) {
        CompanyGroup companyGroup = companyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company group not found"));
        companyGroup.setName(request.getName());
        CompanyGroup saved = companyGroupRepository.save(companyGroup);
        return companyGroupMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CompanyGroup companyGroup = companyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company group not found"));
        companyGroupRepository.delete(companyGroup);
    }
}