package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.SimpleNameResponse;
import com.biobac.company.service.DeliveryPayerService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/delivery-payer")
@RequiredArgsConstructor
public class DeliveryPayerController {
    private final DeliveryPayerService deliveryPayerService;

    @GetMapping
    public ApiResponse<List<SimpleNameResponse>> getAll() {
        List<SimpleNameResponse> responses = deliveryPayerService.getAll();
        return ResponseUtil.success("Delivery payers retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<SimpleNameResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                        @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<SimpleNameResponse>, PaginationMetadata> result = deliveryPayerService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Delivery payers retrieved successfully", result.getFirst(), result.getSecond());
    }

    @GetMapping("/{id}")
    public ApiResponse<SimpleNameResponse> getById(@PathVariable Long id) {
        SimpleNameResponse response = deliveryPayerService.getById(id);
        return ResponseUtil.success("Delivery payer retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<SimpleNameResponse> create(@RequestBody CooperationRequest request) {
        SimpleNameResponse created = deliveryPayerService.create(request);
        return ResponseUtil.success("Delivery payer created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<SimpleNameResponse> update(@PathVariable Long id, @RequestBody CooperationRequest request) {
        SimpleNameResponse updated = deliveryPayerService.update(id, request);
        return ResponseUtil.success("Delivery payer updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        deliveryPayerService.delete(id);
        return ResponseUtil.success("Delivery payer deleted successfully");
    }
}
