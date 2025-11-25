package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PaymentCategoryResponse;
import com.biobac.company.service.PaymentService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/category")
    public ApiResponse<PaymentCategoryResponse> createCategory(@RequestBody PaymentCategoryRequest request) {
        PaymentCategoryResponse response = paymentService.createCategory(request);
        return ResponseUtil.success("Payment category created successfully", response);
    }

    @PutMapping("/category/{id}")
    public ApiResponse<PaymentCategoryResponse> updateCategory(@PathVariable Long id, @RequestBody PaymentCategoryRequest request) {
        PaymentCategoryResponse response = paymentService.updateCategory(id, request);
        return ResponseUtil.success("Payment category updated successfully", response);
    }

    @DeleteMapping("/category/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        paymentService.deleteCategory(id);
        return ResponseUtil.success("Payment category deleted successfully");
    }

    @GetMapping("/category")
    public ApiResponse<List<PaymentCategoryResponse>> getAll() {
        List<PaymentCategoryResponse> responses = paymentService.getAll();
        return ResponseUtil.success("Payment categories retrieved successfully", responses);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<PaymentCategoryResponse> getById(@PathVariable Long id) {
        PaymentCategoryResponse response = paymentService.getById(id);
        return ResponseUtil.success("Payment category retrieved successfully", response);
    }

    @PostMapping("/category/all")
    public ApiResponse<List<PaymentCategoryResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<PaymentCategoryResponse>, PaginationMetadata> result = paymentService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Lines retrieved successfully", result.getFirst(), result.getSecond());
    }
}
