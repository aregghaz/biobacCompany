package com.biobac.company.controller;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SaleTypeResponse;
import com.biobac.company.service.SaleTypeService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company/sale-type")
@RequiredArgsConstructor
public class SaleTypeController {
    private final SaleTypeService saleTypeService;

    @GetMapping
    public ApiResponse<List<SaleTypeResponse>> getAll() {
        List<SaleTypeResponse> responses = saleTypeService.getAll();
        return ResponseUtil.success("Sale Types retrieved successfully", responses);
    }
}
