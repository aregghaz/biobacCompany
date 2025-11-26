package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.OurCompanyRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.OurCompanyResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface OurCompanyService {
    List<OurCompanyResponse> getAll();

    Pair<List<OurCompanyResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );

    OurCompanyResponse getById(Long id);

    OurCompanyResponse create(OurCompanyRequest request);

    OurCompanyResponse update(Long id, OurCompanyRequest request);

    void delete(Long id);
}
