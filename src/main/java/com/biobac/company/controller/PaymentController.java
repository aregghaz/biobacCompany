package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PaymentRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PaymentHistoryResponse;
import com.biobac.company.response.PaymentResponse;
import com.biobac.company.service.PaymentHistoryService;
import com.biobac.company.service.PaymentService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/{id}")
    public ApiResponse<PaymentResponse> getById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getById(id);
        return ResponseUtil.success("Payment retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<String> pay(@RequestBody PaymentRequest request) {
        paymentService.payment(request);
        return ResponseUtil.success("Payment created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id, @RequestBody PaymentRequest request) {
        paymentService.updatePayment(id, request);
        return ResponseUtil.success("Payment updated successfully");
    }

    @PostMapping("/all")
    public ApiResponse<List<PaymentHistoryResponse>> getPagination(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<PaymentHistoryResponse>, PaginationMetadata> result = paymentHistoryService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Payments retrieved successfully", result.getFirst(), result.getSecond());
    }
}
