package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.request.PaymentRequest;
import com.biobac.company.response.PaymentCategoryResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    PaymentCategoryResponse createCategory(PaymentCategoryRequest request);

    PaymentCategoryResponse updateCategory(Long id, PaymentCategoryRequest request);

    Pair<List<PaymentCategoryResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                          Integer page,
                                                                          Integer size,
                                                                          String sortBy,
                                                                          String sortDir);

    PaymentCategoryResponse getById(Long id);

    void deleteCategory(Long id);

    void payment(PaymentRequest request);

    List<PaymentCategoryResponse> getAll();
}
