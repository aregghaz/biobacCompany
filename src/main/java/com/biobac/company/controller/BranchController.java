package com.biobac.company.controller;

import com.biobac.company.request.BranchRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.BranchResponse;
import com.biobac.company.service.BranchService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ApiResponse<BranchResponse> createBranch(@RequestBody BranchRequest request) {
        BranchResponse response = branchService.createBranch(request);
        return ResponseUtil.success("Branch created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<BranchResponse> getBranchById(@PathVariable Long id) {
        BranchResponse response = branchService.getBranchById(id);
        return ResponseUtil.success("Branch retrieved successfully", response);
    }

}
