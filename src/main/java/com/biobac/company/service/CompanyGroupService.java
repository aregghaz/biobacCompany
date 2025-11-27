package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyGroupRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyGroupResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface CompanyGroupService {

    CompanyGroupResponse createCompanyGroup(CompanyGroupRequest request);

    List<CompanyGroupResponse> getAll();

    List<CompanyGroupResponse> getAllCompanyGroup();

    Pair<List<CompanyGroupResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );

    CompanyGroupResponse getById(Long id);

    CompanyGroupResponse update(Long id, CompanyGroupRequest request);

    void delete(Long id);
}
