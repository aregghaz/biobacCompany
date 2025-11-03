package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    CompanyResponse registerCompany(CompanyRequest dto);

    CompanyResponse getCompany(Long companyId);

    CompanyResponse updateCompany(Long id, CompanyRequest dto);

    void deleteCompany(Long companyId);

    List<CompanyResponse> listAllCompanies();

    Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters);

    String getCompanyName(Long id);
}
