package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.AccountRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.AccountResponse;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    AccountResponse create(AccountRequest request);

    AccountResponse getById(Long id);

    AccountResponse update(Long id, AccountRequest request);

    void delete(Long id);

    List<AccountResponse> getAll();

    Pair<List<AccountResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String sortBy,
                                                                  String sortDir);

    BigDecimal sumBalances();
}
