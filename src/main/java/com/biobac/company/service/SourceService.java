package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.SourceResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface SourceService {

    SourceResponse createSource(SourceRequest request);

    SourceResponse getSourceById(Long id);

    List<SourceResponse> getAll();


    Pair<List<SourceResponse>, PaginationMetadata> getLinePagination(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir
    );

    SourceResponse updateSource(Long id, SourceRequest request);

    void delete(Long id);
}
