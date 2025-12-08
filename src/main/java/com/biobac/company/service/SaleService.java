package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.OnSiteSaleRequest;
import com.biobac.company.request.PreSaleRequest;
import com.biobac.company.request.FinalizeSaleRequest;
import com.biobac.company.response.SaleResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface SaleService {
    SaleResponse getById(Long id);

    SaleResponse createOnSiteSale(OnSiteSaleRequest request);

    SaleResponse createPreSale(PreSaleRequest request);

    SaleResponse finalizeSale(FinalizeSaleRequest request);

    Pair<List<SaleResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );
}
