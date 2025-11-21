package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CooperationResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface CooperationService {
    List<CooperationResponse> getAllCooperation();

    Pair<List<CooperationResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );

    CooperationResponse getCooperationById(Long id);

    CooperationResponse create(CooperationRequest request);

    CooperationResponse update(Long id, CooperationRequest request);

    void delete(Long id);
}
