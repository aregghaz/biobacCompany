package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CooperationResponse;
import com.biobac.company.service.CooperationService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/cooperation")
@RequiredArgsConstructor
public class CooperationController {
    private final CooperationService cooperationService;

    @GetMapping
    public ApiResponse<List<CooperationResponse>> getAll() {
        List<CooperationResponse> responses = cooperationService.getAllCooperation();
        return ResponseUtil.success("Cooperations retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<CooperationResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<CooperationResponse>, PaginationMetadata> result = cooperationService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Cooperations retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<CooperationResponse> getById(@PathVariable Long id) {
        CooperationResponse response = cooperationService.getCooperationById(id);
        return ResponseUtil.success("Cooperation retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<CooperationResponse> create(@RequestBody CooperationRequest request) {
        CooperationResponse created = cooperationService.create(request);
        return ResponseUtil.success("Cooperation created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<CooperationResponse> update(@PathVariable Long id, @RequestBody CooperationRequest request) {
        CooperationResponse updated = cooperationService.update(id, request);
        return ResponseUtil.success("Cooperation updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        cooperationService.delete(id);
        return ResponseUtil.success("Cooperation deleted successfully");
    }
}
