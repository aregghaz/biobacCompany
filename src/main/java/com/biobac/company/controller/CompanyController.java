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
import org.springframework.web.bind.annotation.*;

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
        return ResponseUtil.success("FNS received", fnsService.getExtractedData(inn));
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

    @GetMapping("/buyer/cooperation")
    public ApiResponse<List<CompanyResponse>> listBuyerCompaniesWithCooperation() {
        List<CompanyResponse> companies = companyService.listBuyerCompaniesWithCooperation();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/seller/cooperation")
    public ApiResponse<List<CompanyResponse>> listSellerCompaniesWithCooperation() {
        List<CompanyResponse> companies = companyService.listSellerCompaniesWithCooperation();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/buyer")
    public ApiResponse<List<CompanyResponse>> listAllCompanyByBuyer() {
        List<CompanyResponse> companies = companyService.listAllCompaniesByBuyer();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/seller")
    public ApiResponse<List<CompanyResponse>> listAllCompanyBySeller() {
        List<CompanyResponse> companies = companyService.listAllCompaniesBySeller();
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

    @GetMapping("/by-lines")
    public ApiResponse<List<CompanyResponse>> getBuyerCompaniesByLines(@RequestParam(value = "lineIds", required = false) List<Long> lineIds) {
        List<CompanyResponse> responses = companyService.getBuyerCompaniesByLines(lineIds);
        return ResponseUtil.success("Companies retrieved successfully", responses);
    }
}
