package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.EmployeeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.EmployeeResponse;
import com.biobac.company.service.EmployeeService;
import com.biobac.company.utils.ResponseUtil;
import jakarta.validation.Valid;
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
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getAll() {
        List<EmployeeResponse> responses = employeeService.getAll();
        return ResponseUtil.success("Employee retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<EmployeeResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<EmployeeResponse>, PaginationMetadata> result = employeeService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Employee retrieved successfully", result.getFirst(), result.getSecond());
    }

    @PostMapping("/fired/all")
    public ApiResponse<List<EmployeeResponse>> getFiredAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<EmployeeResponse>, PaginationMetadata> result = employeeService.getFiredPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Employee retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> getById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getById(id);
        return ResponseUtil.success("Employee retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest request) {
        EmployeeResponse created = employeeService.create(request);
        return ResponseUtil.success("Employee created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> update(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        EmployeeResponse updated = employeeService.update(id, request);
        return ResponseUtil.success("Employee updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseUtil.success("Employee deleted successfully");
    }
}
