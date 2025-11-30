package com.biobac.company.service;

import com.biobac.company.dto.EmployeeHistoryDto;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.EmployeeHistoryResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface EmployeeHistoryService {
    void recordEmployeeHistory(EmployeeHistoryDto dto);

    Pair<List<EmployeeHistoryResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );
}
