package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.FinalizeSaleRequest;
import com.biobac.company.request.OnSiteSaleRequest;
import com.biobac.company.request.PreSaleRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SaleResponse;
import com.biobac.company.service.SaleService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @GetMapping("/{id}")
    public ApiResponse<SaleResponse> getById(@PathVariable Long id) {
        SaleResponse response = saleService.getById(id);
        return ResponseUtil.success("Sale retrieved successfully", response);
    }

    @PostMapping("/oni-site")
    public ApiResponse<SaleResponse> createOnSiteSale(@RequestBody OnSiteSaleRequest request) {
        SaleResponse response = saleService.createOnSiteSale(request);
        return ResponseUtil.success("On Site sale created successfully", response);
    }

    @PostMapping("/pre")
    public ApiResponse<SaleResponse> createPreSale(@RequestBody PreSaleRequest request) {
        SaleResponse response = saleService.createPreSale(request);
        return ResponseUtil.success("Pre sale created successfully", response);
    }

    @PostMapping("/finalize")
    public ApiResponse<SaleResponse> finalizeSale(@RequestBody FinalizeSaleRequest request) {
        SaleResponse response = saleService.finalizeSale(request);
        return ResponseUtil.success("Sale finalized successfully", response);
    }

    @PostMapping("/success/all")
    public ApiResponse<List<SaleResponse>> getFinalizedAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<SaleResponse>, PaginationMetadata> result = saleService.getFinalizedPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Sales retrieved successfully", result.getFirst(), result.getSecond());
    }

    @PostMapping("/pending/all")
    public ApiResponse<List<SaleResponse>> getPendingAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<SaleResponse>, PaginationMetadata> result = saleService.getPendingPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Sales retrieved successfully", result.getFirst(), result.getSecond());
    }
}
