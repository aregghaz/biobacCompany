package com.biobac.company.controller;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/pagination")
    public ApiResponse<List<CompanyDto>> listCompaniesWithPagination(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<CompanyDto>, PaginationMetadata> result = companyService.listCompaniesWithPagination(page, size, sortBy, sortDir, filters);
        return ResponseUtil.success("Companies retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping
    public ApiResponse<List<CompanyDto>> listAllCompanies() {
        List<CompanyDto> companies = companyService.listAllCompanies();
        return ResponseUtil.success("Companies retrieved successfully", companies);
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyDto> getCompany(@PathVariable Long id) {
        CompanyDto company = companyService.getCompany(id);
        return ResponseUtil.success("Company retrieved successfully", company);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        CompanyDto updatedCompany = companyService.updateCompany(id, companyDto);
        return ResponseUtil.success("Company updated successfully", updatedCompany);
    }

    @PostMapping
    public ApiResponse<CompanyDto> registerCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto registeredCompany = companyService.registerCompany(companyDto);
        return ResponseUtil.success("Company registered successfully", registeredCompany);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseUtil.success("Company deleted successfully");
    }
}
