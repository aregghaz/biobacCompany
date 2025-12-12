package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyGroupRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyGroupResponse;
import com.biobac.company.service.CompanyGroupService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/company-group")
@RequiredArgsConstructor
public class CompanyGroupController {
    private final CompanyGroupService companyGroupService;

    @PostMapping
    public ApiResponse<CompanyGroupResponse> createCompanyGroup(@RequestBody CompanyGroupRequest request) {
        CompanyGroupResponse response = companyGroupService.createCompanyGroup(request);
        return ResponseUtil.success("Company group created successfully", response);
    }

    @GetMapping
    public ApiResponse<List<CompanyGroupResponse>> getAll() {
        List<CompanyGroupResponse> ingredientGroupDtos = companyGroupService.getAll();
        return ResponseUtil.success("Company groups retrieved successfully", ingredientGroupDtos);
    }

    @GetMapping("/company-groups")
    public ApiResponse<List<CompanyGroupResponse>> getAllCompanyGroups() {
        List<CompanyGroupResponse> responses = companyGroupService.getAllCompanyGroup();
        return ResponseUtil.success("Company groups retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<CompanyGroupResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<CompanyGroupResponse>, PaginationMetadata> result = companyGroupService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Company groups retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyGroupResponse> getById(@PathVariable Long id) {
        CompanyGroupResponse ingredientGroup = companyGroupService.getById(id);
        return ResponseUtil.success("Company group retrieved successfully", ingredientGroup);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyGroupResponse> update(@PathVariable Long id, @RequestBody CompanyGroupRequest request) {
        CompanyGroupResponse updatedGroup = companyGroupService.update(id, request);
        return ResponseUtil.success("Company group updated successfully", updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        companyGroupService.delete(id);
        return ResponseUtil.success("Company group deleted successfully");
    }
}
