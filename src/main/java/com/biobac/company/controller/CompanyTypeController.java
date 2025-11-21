package com.biobac.company.controller;

import com.biobac.company.request.CreateCompanyTypeRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CreateCompanyTypeResponse;
import com.biobac.company.service.CompanyTypeService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company/type")
@RequiredArgsConstructor
public class CompanyTypeController {
    private final CompanyTypeService companyTypeService;

    @PostMapping("/create")
    public ApiResponse<CreateCompanyTypeResponse> create(@RequestBody CreateCompanyTypeRequest request) {
        CreateCompanyTypeResponse response = companyTypeService.createCompanyType(request);
        return ResponseUtil.success("Company Type created successfully", response);
    }

    @GetMapping("/all")
    public ApiResponse<List<CreateCompanyTypeResponse>> getAllCompanyType() {
        List<CreateCompanyTypeResponse> companyTypeResponses = companyTypeService.getAllCompanyType();
        return ResponseUtil.success("Company Types retrieved successfully", companyTypeResponses);
    }
}
