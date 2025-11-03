package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CompanyGroupRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CompanyGroupResponse;
import com.biobac.company.service.CompanyGroupService;
import com.biobac.company.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company-groups")
@RequiredArgsConstructor
public class CompanyGroupController {
    private final CompanyGroupService companyGroupService;

    @GetMapping
    public ApiResponse<List<CompanyGroupResponse>> getAll() {
        List<CompanyGroupResponse> ingredientGroupDtos = companyGroupService.getAll();
        return ResponseUtil.success("Company groups retrieved successfully", ingredientGroupDtos);
    }

    @PostMapping("/all")
    public ApiResponse<List<CompanyGroupResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(required = false, defaultValue = "10") Integer size,
                                                             @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                             @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                             @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<CompanyGroupResponse>, PaginationMetadata> result = companyGroupService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Company groups retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyGroupResponse> getById(@PathVariable Long id) {
        CompanyGroupResponse ingredientGroup = companyGroupService.getById(id);
        return ResponseUtil.success("Company group retrieved successfully", ingredientGroup);
    }

    @PostMapping
    public ApiResponse<CompanyGroupResponse> create(@RequestBody CompanyGroupRequest request) {
        CompanyGroupResponse createdGroup = companyGroupService.create(request);

        return ResponseUtil.success("Company group created successfully", createdGroup);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyGroupResponse> update(@PathVariable Long id, @RequestBody CompanyGroupRequest request) {
        CompanyGroupResponse updatedGroup = companyGroupService.update(id, request);
        return ResponseUtil.success("Company group updated successfully", updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id, HttpServletRequest request) {
        companyGroupService.delete(id);
        return ResponseUtil.success("Company group deleted successfully");
    }
}
