package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.dto.PaymentHistoryDto;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.PaymentHistoryResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface PaymentHistoryService {
    void recordHistory(PaymentHistoryDto dto);

    Pair<List<PaymentHistoryResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir);
}
