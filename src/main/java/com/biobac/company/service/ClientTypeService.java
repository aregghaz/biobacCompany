package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ClientTypeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.SimpleNameResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface ClientTypeService {
    List<SimpleNameResponse> getAll();

    Pair<List<SimpleNameResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                     Integer page,
                                                                     Integer size,
                                                                     String sortBy,
                                                                     String sortDir);

    SimpleNameResponse getById(Long id);

    SimpleNameResponse create(ClientTypeRequest request);

    SimpleNameResponse update(Long id, ClientTypeRequest request);

    void delete(Long id);
}
