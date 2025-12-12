package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.OurCompanyRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.OurCompanyResponse;
import com.biobac.company.service.OurCompanyService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/our-company")
@RequiredArgsConstructor
public class OurCompanyController {
    private final OurCompanyService ourCompanyService;

    @GetMapping
    public ApiResponse<List<OurCompanyResponse>> getAll() {
        List<OurCompanyResponse> responses = ourCompanyService.getAll();
        return ResponseUtil.success("Our companies retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<OurCompanyResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<OurCompanyResponse>, PaginationMetadata> result = ourCompanyService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Our companies retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<OurCompanyResponse> getById(@PathVariable Long id) {
        OurCompanyResponse response = ourCompanyService.getById(id);
        return ResponseUtil.success("Our company retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<OurCompanyResponse> create(@RequestBody OurCompanyRequest request) {
        OurCompanyResponse created = ourCompanyService.create(request);
        return ResponseUtil.success("Our company created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<OurCompanyResponse> update(@PathVariable Long id, @RequestBody OurCompanyRequest request) {
        OurCompanyResponse updated = ourCompanyService.update(id, request);
        return ResponseUtil.success("Our company updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        ourCompanyService.delete(id);
        return ResponseUtil.success("Our company deleted successfully");
    }
}
