package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.SimpleNameResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface LineService {
    List<SimpleNameResponse> getAll();

    Pair<List<SimpleNameResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                     Integer page,
                                                                     Integer size,
                                                                     String sortBy,
                                                                     String sortDir);
    SimpleNameResponse getById(Long id);
    SimpleNameResponse create(LineRequest request);
    SimpleNameResponse update(Long id, LineRequest request);
    void delete(Long id);
}
