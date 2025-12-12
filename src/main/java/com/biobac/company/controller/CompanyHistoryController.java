package com.biobac.company.controller;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyHistoryResponse;
import com.biobac.company.service.CompanyHistoryService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// TODO this controller is only testing purpose
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company/company-history")
public class CompanyHistoryController {
    private final CompanyHistoryService companyHistoryService;

    @GetMapping("/{id}")
    public ApiResponse<CompanyHistoryResponse> getCompanyHistoryById(@PathVariable Long id){
        CompanyHistoryResponse companyHistoryResponse = companyHistoryService.getCompanyHistoryById(id);
        return ResponseUtil.success("Company history retrieved successfully", companyHistoryResponse);
    }

}
