package com.biobac.company.controller;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyTypeResponse;
import com.biobac.company.service.CompanyTypeService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company/type")
@RequiredArgsConstructor
public class CompanyTypeController {
    private final CompanyTypeService companyTypeService;

    @GetMapping
    public ApiResponse<List<CompanyTypeResponse>> get() {
        List<CompanyTypeResponse> companyTypeResponses = companyTypeService.getAll();
        return ResponseUtil.success("Company Types retrieved successfully", companyTypeResponses);
    }
}
