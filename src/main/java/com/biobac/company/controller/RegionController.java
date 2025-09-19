package com.biobac.company.controller;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.RegionResponse;
import com.biobac.company.service.RegionService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public ApiResponse<List<RegionResponse>> getAll() {
        List<RegionResponse> responses = regionService.getAll();
        return ResponseUtil.success("Regions retrieved successfully", responses);
    }
}
