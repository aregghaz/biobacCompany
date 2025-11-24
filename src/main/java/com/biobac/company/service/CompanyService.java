package com.biobac.company.service;

import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.CompanyResponse;

public interface CompanyService {

    CompanyResponse registerCompany(CompanyRequest request);

    CompanyResponse getCompanyById(Long companyId);
//
//    CompanyResponse updateCompany(Long id, CompanyRequest dto);
//
//    void deleteCompany(Long companyId);
//
//    List<CompanyResponse> listAllCompanies();
//
//    Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters);
//
//    String getCompanyName(Long id);
//
//    List<CompanyResponse> listAllBuyersCompanies();
//
//    List<CompanyResponse> listAllSellersCompanies();
}
