package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PriceListWrapperResponse;
import com.biobac.company.service.PriceListWrapperService;
import com.biobac.company.utils.ResponseUtil;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company/price-list")
public class PriceListController {

    private final PriceListWrapperService priceListWrapperService;

    @PostMapping
    public ApiResponse<PriceListWrapperResponse> createPriceList(@RequestBody PriceListWrapperRequest request) {
        PriceListWrapperResponse response = priceListWrapperService.createPriceListWrapper(request);
        return ResponseUtil.success("Price list created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PriceListWrapperResponse> getPriceListById(@PathVariable Long id) {
        PriceListWrapperResponse response = priceListWrapperService.getPriceListWrapperById(id);
        return ResponseUtil.success("Price list retrieved successfully", response);
    }

    @PostMapping("/all")
    public ApiResponse<List<PriceListWrapperResponse>> listCompaniesWithPagination(
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters) {
        Map<String, FilterCriteria> safeFilters = (filters == null) ? Collections.emptyMap() : filters;
        Pair<List<PriceListWrapperResponse>, PaginationMetadata> result = priceListWrapperService.getPriceListWrapperPagination(
                safeFilters, page, size, sortBy, sortDir
        );
        return ResponseUtil.success("Companies retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping
    public ApiResponse<List<PriceListWrapperResponse>> listCompaniesWithPagination() {
        List<PriceListWrapperResponse> response = priceListWrapperService.getAllPriceList();
        return ResponseUtil.success("Companies retrieved successfully", response);
    }

    @PutMapping("/{id}")
    public ApiResponse<PriceListWrapperResponse> updatePriceList(@PathVariable Long id, @RequestBody PriceListWrapperRequest request) {
        PriceListWrapperResponse response = priceListWrapperService.updatePriceListWrapper(id, request);
        return ResponseUtil.success("Price list updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePriceList(@PathVariable Long id) {
        priceListWrapperService.deletePriceListWrapper(id);
        return ResponseUtil.success("Price list deleted successfully");
    }
}