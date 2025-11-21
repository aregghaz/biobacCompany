package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.TransferRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.TransferResponse;
import com.biobac.company.service.TransferService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public ApiResponse<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = transferService.transfer(request);
        return ResponseUtil.success("Sum transferred successfully", response);
    }

    @PostMapping("/all")
    public ApiResponse<List<TransferResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters) {
        Pair<List<TransferResponse>, PaginationMetadata> result = transferService.getPagination(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Transfers retrieved successfully", result.getFirst(), result.getSecond());
    }
}
