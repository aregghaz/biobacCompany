package com.biobac.company.controller;

import com.biobac.company.request.CreateRegionRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.CreateRegionResponse;
import com.biobac.company.response.RegionResponse;
import com.biobac.company.service.RegionService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateRegionResponse>> createRegion(@RequestBody CreateRegionRequest request) {
        CreateRegionResponse regionResponse = regionService.createRegion(request);
        return ResponseEntity.status(201).body(ResponseUtil.success("created", regionResponse));
    }

    @GetMapping
    public ApiResponse<List<RegionResponse>> getAll() {
        List<RegionResponse> responses = regionService.getAll();
        return ResponseUtil.success("Regions retrieved successfully", responses);
    }
}
