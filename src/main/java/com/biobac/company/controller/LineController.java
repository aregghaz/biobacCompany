package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.LineService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/line")
@RequiredArgsConstructor
public class LineController {
    private final LineService lineService;

    @GetMapping
    public ApiResponse<List<SimpleNameResponse>> getAll() {
        List<SimpleNameResponse> responses = lineService.getAll();
        return ResponseUtil.success("Lines retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<SimpleNameResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                        @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<SimpleNameResponse>, PaginationMetadata> result = lineService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Lines retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SimpleNameResponse> getById(@PathVariable Long id) {
        SimpleNameResponse response = lineService.getById(id);
        return ResponseUtil.success("Line retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<SimpleNameResponse> create(@RequestBody LineRequest request) {
        SimpleNameResponse created = lineService.create(request);
        return ResponseUtil.success("Line created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<SimpleNameResponse> update(@PathVariable Long id, @RequestBody LineRequest request) {
        SimpleNameResponse updated = lineService.update(id, request);
        return ResponseUtil.success("Line updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        lineService.delete(id);
        return ResponseUtil.success("Line deleted successfully");
    }
}
