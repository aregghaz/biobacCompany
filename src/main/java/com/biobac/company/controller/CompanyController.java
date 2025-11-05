package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.utils.ResponseUtil;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Validated
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/all")
    public ApiResponse<List<CompanyResponse>> listCompaniesWithPagination(
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters) {
        Map<String, FilterCriteria> safeFilters = (filters == null) ? Collections.emptyMap() : filters;
        Pair<List<CompanyResponse>, PaginationMetadata> result = companyService.listCompaniesWithPagination(page, size, sortBy, sortDir, safeFilters);
        return ResponseUtil.success("Companies retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping
    public ApiResponse<List<CompanyResponse>> listAllCompanies() {
        List<CompanyResponse> companies = companyService.listAllCompanies();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/buyer")
    public ApiResponse<List<CompanyResponse>> listAllBuyersCompanies() {
        List<CompanyResponse> companies = companyService.listAllBuyersCompanies();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/seller")
    public ApiResponse<List<CompanyResponse>> listAllSellersCompanies() {
        List<CompanyResponse> companies = companyService.listAllSellersCompanies();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> getCompany(@PathVariable Long id) {
        CompanyResponse company = companyService.getCompany(id);
        return ResponseUtil.success("Company retrieved successfully", company);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyRequest request) {
        CompanyResponse updatedCompany = companyService.updateCompany(id, request);
        return ResponseUtil.success("Company updated successfully", updatedCompany);
    }

    @PostMapping
    public ApiResponse<CompanyResponse> registerCompany(@RequestBody CompanyRequest request) {
        CompanyResponse registeredCompany = companyService.registerCompany(request);
        return ResponseUtil.success("Company registered successfully", registeredCompany);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseUtil.success("Company deleted successfully");
    }

    @GetMapping("/name/{id}")
    public ApiResponse<String> getCompanyName(@PathVariable Long id) {
        String name = companyService.getCompanyName(id);
        return ResponseUtil.success("Company name retrieved successfully", name);
    }
}
