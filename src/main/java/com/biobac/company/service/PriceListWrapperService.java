package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.response.PriceListWrapperResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PriceListWrapperService {
    PriceListWrapperResponse createPriceListWrapper(PriceListWrapperRequest request);

    PriceListWrapperResponse getPriceListWrapperById(Long id);

    Optional<PriceListWrapper> fetchPriceListById(Long id);

    Pair<List<PriceListWrapperResponse>, PaginationMetadata> getPriceListWrapperPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );


    List<PriceListWrapperResponse> getAllPriceList();

    PriceListWrapperResponse updatePriceListWrapper(Long id, PriceListWrapperRequest request);

    void deletePriceListWrapper(Long id);
}
