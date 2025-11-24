package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ClientTypeRequest;
import com.biobac.company.request.CreateClientTypeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CreateClientTypeResponse;
import com.biobac.company.service.ClientTypeService;
import com.biobac.company.utils.ResponseUtil;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/client-type")
@RequiredArgsConstructor
public class ClientTypeController {
    private final ClientTypeService clientTypeService;

    @GetMapping
    public ApiResponse<List<CreateClientTypeResponse>> getAll() {
        List<CreateClientTypeResponse> responses = clientTypeService.getAllClientType();
        return ResponseUtil.success("Client types retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<CreateClientTypeResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<CreateClientTypeResponse>, PaginationMetadata> result = clientTypeService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Client types retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<CreateClientTypeResponse> getById(@PathVariable Long id) {
        CreateClientTypeResponse response = clientTypeService.getClientTypeById(id);
        return ResponseUtil.success("Client type retrieved successfully", response);
    }

    @PostMapping("/create")
    public ApiResponse<CreateClientTypeResponse> create(@RequestBody CreateClientTypeRequest request) {
        CreateClientTypeResponse created = clientTypeService.createClientType(request);
        return ResponseUtil.success("Client type created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<CreateClientTypeResponse> update(@PathVariable Long id, @RequestBody ClientTypeRequest request) {
        CreateClientTypeResponse updated = clientTypeService.update(id, request);
        return ResponseUtil.success("Client type updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        clientTypeService.delete(id);
        return ResponseUtil.success("Client type deleted successfully");
    }
}
