package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ClientTypeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.ClientTypeService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/client-type")
@RequiredArgsConstructor
public class ClientTypeController {
    private final ClientTypeService clientTypeService;

    @GetMapping
    public ApiResponse<List<SimpleNameResponse>> getAll() {
        List<SimpleNameResponse> responses = clientTypeService.getAll();
        return ResponseUtil.success("Client types retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<SimpleNameResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                        @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<SimpleNameResponse>, PaginationMetadata> result = clientTypeService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Client types retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SimpleNameResponse> getById(@PathVariable Long id) {
        SimpleNameResponse response = clientTypeService.getById(id);
        return ResponseUtil.success("Client type retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<SimpleNameResponse> create(@RequestBody ClientTypeRequest request) {
        SimpleNameResponse created = clientTypeService.create(request);
        return ResponseUtil.success("Client type created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<SimpleNameResponse> update(@PathVariable Long id, @RequestBody ClientTypeRequest request) {
        SimpleNameResponse updated = clientTypeService.update(id, request);
        return ResponseUtil.success("Client type updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        clientTypeService.delete(id);
        return ResponseUtil.success("Client type deleted successfully");
    }
}
