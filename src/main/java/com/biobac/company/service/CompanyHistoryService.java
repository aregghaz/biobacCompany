package com.biobac.company.service;

import com.biobac.company.request.CompanyHistoryRequest;
import com.biobac.company.response.CompanyHistoryResponse;

public interface CompanyHistoryService {
    void recordCompanyHistory(CompanyHistoryRequest request);

    CompanyHistoryResponse getCompanyHistoryById(Long id);

}
