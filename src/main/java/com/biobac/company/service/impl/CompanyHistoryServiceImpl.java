package com.biobac.company.service.impl;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyHistory;
import com.biobac.company.mapper.CompanyHistoryMapper;
import com.biobac.company.repository.CompanyHistoryRepository;
import com.biobac.company.request.CompanyHistoryRequest;
import com.biobac.company.response.CompanyHistoryResponse;
import com.biobac.company.service.CompanyHistoryService;
import com.biobac.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyHistoryServiceImpl implements CompanyHistoryService {

    private final CompanyHistoryRepository companyHistoryRepository;
    private final CompanyHistoryMapper companyHistoryMapper;
    private final CompanyService companyService;

    @Override
    public void recordCompanyHistory(CompanyHistoryRequest request) {
        Company company = companyService.fetchCompanyById(request.getCompanyId());
        CompanyHistory companyHistory = companyHistoryMapper.toCompanyHistoryEntity(request);
        companyHistory.setCompany(company);
        CompanyHistory savedHistory = companyHistoryRepository.save(companyHistory);
        companyHistoryMapper.toCompanyHistoryResponse(savedHistory);
    }

    @Override
    public CompanyHistoryResponse getCompanyHistoryById(Long id) {
        return companyHistoryRepository.findById(id)
                .map(companyHistoryMapper::toCompanyHistoryResponse)
                .orElseThrow(() -> new RuntimeException("Company history not found with id: " + id));
    }
}
