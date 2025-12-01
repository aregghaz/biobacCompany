package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.FnsCompanyResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.service.impl.FnsService;
import com.biobac.company.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;
    private final FnsService fnsService;

    @GetMapping("/fns/{inn}")
    public ApiResponse<FnsCompanyResponse> getCompanyInfo(@PathVariable String inn) {
        return ResponseUtil.success("FNS received" ,fnsService.getExtractedData(inn));
    }

    @PostMapping
    public ApiResponse<CompanyResponse> registerCompany(@RequestBody @Valid CompanyRequest request) {
        CompanyResponse response = companyService.registerCompany(request);
        return ResponseUtil.success("created", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> getCompanyById(@PathVariable Long id) {
        CompanyResponse response = companyService.getCompanyById(id);
        return ResponseUtil.success("success", response);
    }

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

    @GetMapping("/buyer/yes")
    public ApiResponse<List<CompanyResponse>> listAllCompanyByBuyerYes() {
        List<CompanyResponse> companies = companyService.listAllCompaniesByBuyerYes();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/buyer/no")
    public ApiResponse<List<CompanyResponse>> listAllCompanyByBuyerNo() {
        List<CompanyResponse> companies = companyService.listAllCompaniesByBuyerNo();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyRequest request) {
        CompanyResponse updatedCompany = companyService.updateCompany(id, request);
        return ResponseUtil.success("Company updated successfully", updatedCompany);
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
