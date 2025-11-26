package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SourceResponse;
import com.biobac.company.service.SourceService;
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
@RequiredArgsConstructor
@RequestMapping("/api/company/source")
public class SourceController {

    private final SourceService sourceService;

    @PostMapping
    public ApiResponse<SourceResponse> createSource(@RequestBody SourceRequest request) {
        SourceResponse response = sourceService.createSource(request);
        return ResponseUtil.success("Source created successfully", response);
    }

    @GetMapping
    public ApiResponse<List<SourceResponse>> getAllSources() {
        List<SourceResponse> response = sourceService.getAll();
        return ResponseUtil.success("Sources retrieved successfully", response);
    }

    @PostMapping("/all")
    public ApiResponse<List<SourceResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<SourceResponse>, PaginationMetadata> result = sourceService.getLinePagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Lines retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SourceResponse> getById(@PathVariable Long id) {
        SourceResponse response = sourceService.getSourceById(id);
        return ResponseUtil.success("Line retrieved successfully", response);
    }


    @PutMapping("/{id}")
    public ApiResponse<SourceResponse> update(@PathVariable Long id, @RequestBody SourceRequest request) {
        SourceResponse updated = sourceService.updateSource(id, request);
        return ResponseUtil.success("Line updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        sourceService.delete(id);
        return ResponseUtil.success("Line deleted successfully");
    }
}
