package com.biobac.company.service.impl;

import com.biobac.company.mapper.CompanyTypeMapper;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.response.CompanyTypeResponse;
import com.biobac.company.service.CompanyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyTypeServiceImpl implements CompanyTypeService {
    private final CompanyTypeMapper companyTypeMapper;
    private final CompanyTypeRepository companyTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompanyTypeResponse> getAll() {
        return companyTypeRepository
                .findAll()
                .stream()
                .map(companyTypeMapper::toResponse).toList();
    }
}
