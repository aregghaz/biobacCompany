package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.AccountRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.AccountBalanceMetadata;
import com.biobac.company.response.AccountResponse;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.service.AccountService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ApiResponse<List<AccountResponse>> getAll() {
        List<AccountResponse> responses = accountService.getAll();
        return ResponseUtil.success("Accounts retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<AccountResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters) {
        BigDecimal sum = accountService.sumBalances();
        Pair<List<AccountResponse>, PaginationMetadata> result = accountService.getPagination(filters, page, size, sortBy, sortDir);
        AccountBalanceMetadata metadata = new AccountBalanceMetadata(result.getSecond(), sum);
        return ResponseUtil.success("Accounts retrieved successfully", result.getFirst(), metadata);
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getById(@PathVariable Long id) {
        AccountResponse response = accountService.getById(id);
        return ResponseUtil.success("Account retrieved successfully", response);
    }

    @PostMapping
    public ApiResponse<AccountResponse> create(@RequestBody AccountRequest request) {
        AccountResponse created = accountService.create(request);
        return ResponseUtil.success("Account created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<AccountResponse> update(@PathVariable Long id, @RequestBody AccountRequest request) {
        AccountResponse updated = accountService.update(id, request);
        return ResponseUtil.success("Account updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseUtil.success("Account deleted successfully");
    }
}
