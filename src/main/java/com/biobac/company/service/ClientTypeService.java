package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ClientTypeRequest;
import com.biobac.company.request.CreateClientTypeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CreateClientTypeResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface ClientTypeService {
    List<CreateClientTypeResponse> getAllClientType();

    Pair<List<CreateClientTypeResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                           Integer page,
                                                                           Integer size,
                                                                           String sortBy,
                                                                           String sortDir);

    CreateClientTypeResponse getClientTypeById(Long id);

    CreateClientTypeResponse createClientType(CreateClientTypeRequest request);

    CreateClientTypeResponse update(Long id, ClientTypeRequest request);

    void delete(Long id);
}
