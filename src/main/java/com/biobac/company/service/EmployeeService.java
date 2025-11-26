package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.EmployeeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.EmployeeResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse getById(Long id);

    EmployeeResponse update(Long id, EmployeeRequest request);

    void delete(Long id);

    List<EmployeeResponse> getAll();

    Pair<List<EmployeeResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String sortBy,
                                                                  String sortDir);
}
