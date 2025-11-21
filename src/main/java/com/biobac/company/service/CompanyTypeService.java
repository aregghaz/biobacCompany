package com.biobac.company.service;

import com.biobac.company.request.CreateCompanyTypeRequest;
import com.biobac.company.response.CreateCompanyTypeResponse;

import java.util.List;

public interface CompanyTypeService {

    CreateCompanyTypeResponse createCompanyType(CreateCompanyTypeRequest request);

    List<CreateCompanyTypeResponse> getAllCompanyType();
}
