package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.LineResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface LineService {

    LineResponse createLine(LineRequest request);

    LineResponse getLineById(Long id);

    Pair<List<LineResponse>, PaginationMetadata> getLinePagination(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir
    );

    LineResponse updateLine(Long id, LineRequest request);

    List<LineResponse> getAllLine();

    void deleteLine(Long id);
}
