package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.LineResponse;
import com.biobac.company.service.LineService;
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
@RequestMapping("/api/company/line")
@RequiredArgsConstructor
public class LineController {
    private final LineService lineService;

    @GetMapping
    public ApiResponse<List<LineResponse>> getAll() {
        List<LineResponse> responses = lineService.getAllLine();
        return ResponseUtil.success("Lines retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<LineResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<LineResponse>, PaginationMetadata> result = lineService.getLinePagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Lines retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<LineResponse> getById(@PathVariable Long id) {
        LineResponse response = lineService.getLineById(id);
        return ResponseUtil.success("Line retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<LineResponse> create(@RequestBody LineRequest request) {
        LineResponse created = lineService.createLine(request);
        return ResponseUtil.success("Line created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<LineResponse> update(@PathVariable Long id, @RequestBody LineRequest request) {
        LineResponse updated = lineService.updateLine(id, request);
        return ResponseUtil.success("Line updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        lineService.deleteLine(id);
        return ResponseUtil.success("Line deleted successfully");
    }
}
