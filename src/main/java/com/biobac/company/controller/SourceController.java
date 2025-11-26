package com.biobac.company.controller;

import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SourceResponse;
import com.biobac.company.service.SourceService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
