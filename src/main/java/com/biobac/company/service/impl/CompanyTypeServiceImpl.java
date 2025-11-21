package com.biobac.company.service.impl;

import com.biobac.company.entity.CompanyType;
import com.biobac.company.mapper.CompanyTypeMapper;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.request.CreateCompanyTypeRequest;
import com.biobac.company.response.CreateCompanyTypeResponse;
import com.biobac.company.service.CompanyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyTypeServiceImpl implements CompanyTypeService {
    private final CompanyTypeMapper companyTypeMapper;
    private final CompanyTypeRepository companyTypeRepository;

    @Override
    public CreateCompanyTypeResponse createCompanyType(CreateCompanyTypeRequest request) {
        CompanyType companyType = companyTypeMapper.toCompanyTypeEntity(request);
        CompanyType savedCompanyType = companyTypeRepository.save(companyType);
        return companyTypeMapper.toCompanyTypeResponse(savedCompanyType);
    }

    @Override
    public List<CreateCompanyTypeResponse> getAllCompanyType() {
        return companyTypeRepository.findAll().stream().map(companyTypeMapper::toCompanyTypeResponse).toList();
    }
}
