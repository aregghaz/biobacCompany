package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Company;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.CompanyUpdateRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    CompanyResponse registerCompany(CompanyRequest request);

    CompanyResponse getCompanyById(Long companyId);

    CompanyResponse getCompanyByHistoryId(Long historyId);

    Company fetchCompanyById(Long companyId);

    CompanyResponse updateCompany(Long id, CompanyUpdateRequest dto);

    void deleteCompany(Long companyId);

    List<CompanyResponse> listAllCompanies();

    Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters);

    String getCompanyName(Long id);

    List<CompanyResponse> listBuyerCompaniesWithCooperation();

    List<CompanyResponse> listSellerCompaniesWithCooperation();

    List<CompanyResponse> listAllCompaniesByBuyer();

    List<CompanyResponse> listAllCompaniesBySeller();

    List<CompanyResponse> getBuyerCompaniesByLines(List<Long> lineIds);
}
