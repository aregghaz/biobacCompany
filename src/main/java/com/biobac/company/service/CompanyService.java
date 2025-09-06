package com.biobac.company.service;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    @Transactional
    CompanyDto registerCompany(CompanyDto dto);

    @Transactional(readOnly = true)
    CompanyResponse getCompany(Long companyId);

    @Transactional
    CompanyDto updateCompany(Long id, CompanyDto dto);

    @Transactional
    void deleteCompany(Long companyId);

    @Transactional(readOnly = true)
    List<CompanyResponse> listAllCompanies();

    @Transactional(readOnly = true)
    Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters);

    @Transactional(readOnly = true)
    String getCompanyName(Long id);
}
