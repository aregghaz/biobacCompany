package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ContractFormRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.ContractFormService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/contract-form")
@RequiredArgsConstructor
public class ContractFormController {
    private final ContractFormService contractFormService;

    @GetMapping
    public ApiResponse<List<SimpleNameResponse>> getAll() {
        List<SimpleNameResponse> responses = contractFormService.getAll();
        return ResponseUtil.success("Contract forms retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<SimpleNameResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                        @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<SimpleNameResponse>, PaginationMetadata> result = contractFormService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Contract forms retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SimpleNameResponse> getById(@PathVariable Long id) {
        SimpleNameResponse response = contractFormService.getById(id);
        return ResponseUtil.success("Contract form retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<SimpleNameResponse> create(@RequestBody ContractFormRequest request) {
        SimpleNameResponse created = contractFormService.create(request);
        return ResponseUtil.success("Contract form created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<SimpleNameResponse> update(@PathVariable Long id, @RequestBody ContractFormRequest request) {
        SimpleNameResponse updated = contractFormService.update(id, request);
        return ResponseUtil.success("Contract form updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        contractFormService.delete(id);
        return ResponseUtil.success("Contract form deleted successfully");
    }
}
