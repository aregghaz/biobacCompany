package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.DeliveryMethodRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.DeliveryMethodService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/delivery-method")
@RequiredArgsConstructor
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;

    @GetMapping
    public ApiResponse<List<SimpleNameResponse>> getAll() {
        List<SimpleNameResponse> responses = deliveryMethodService.getAll();
        return ResponseUtil.success("Delivery methods retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<SimpleNameResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                        @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<SimpleNameResponse>, PaginationMetadata> result = deliveryMethodService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Delivery methods retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SimpleNameResponse> getById(@PathVariable Long id) {
        SimpleNameResponse response = deliveryMethodService.getById(id);
        return ResponseUtil.success("Delivery method retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<SimpleNameResponse> create(@RequestBody DeliveryMethodRequest request) {
        SimpleNameResponse created = deliveryMethodService.create(request);
        return ResponseUtil.success("Delivery method created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<SimpleNameResponse> update(@PathVariable Long id, @RequestBody DeliveryMethodRequest request) {
        SimpleNameResponse updated = deliveryMethodService.update(id, request);
        return ResponseUtil.success("Delivery method updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        deliveryMethodService.delete(id);
        return ResponseUtil.success("Delivery method deleted successfully");
    }
}
