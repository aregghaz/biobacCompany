package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.TransferRequest;
import com.biobac.company.response.TransferResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface TransferService {
    TransferResponse transfer(TransferRequest request);

    Pair<List<TransferResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                   Integer page,
                                                                   Integer size,
                                                                   String sortBy,
                                                                   String sortDir);
}
