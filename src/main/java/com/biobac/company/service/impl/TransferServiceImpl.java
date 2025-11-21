package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.Transfer;
import com.biobac.company.mapper.TransferMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.TransferRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.TransferRequest;
import com.biobac.company.response.TransferResponse;
import com.biobac.company.service.TransferService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final AccountRepository accountRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    private Pageable buildPageable(Integer page, Integer size, String sortBy, String sortDir) {
        int safePage = page == null || page < 0 ? DEFAULT_PAGE : page;
        int safeSize = size == null || size <= 0 ? DEFAULT_SIZE : size;
        String safeSortBy = (sortBy == null || sortBy.isBlank()) ? DEFAULT_SORT_BY : sortBy;
        String sd = (sortDir == null || sortDir.isBlank()) ? DEFAULT_SORT_DIR : sortDir;
        Sort sort = sd.equalsIgnoreCase("asc") ? Sort.by(safeSortBy).ascending() : Sort.by(safeSortBy).descending();
        if (safeSize > 1000) {
            safeSize = 1000;
        }
        return PageRequest.of(safePage, safeSize, sort);
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Account fromAccount = lockAccount(request.getFromAccountId(), request.getToAccountId());
        Account toAccount = lockAccount(request.getToAccountId(), request.getFromAccountId());

        transferBetweenAccounts(fromAccount, toAccount, request.getSum());

        Transfer transfer = transferMapper.toEntity(request);
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);

        Transfer saved = transferRepository.save(transfer);
        return transferMapper.toResponse(saved);
    }

    private void transferBetweenAccounts(Account from, Account to, BigDecimal sum) {
        from.setBalance(from.getBalance().subtract(sum));
        to.setBalance(to.getBalance().add(sum));

        accountRepository.saveAll(List.of(from, to));
    }

    private Account lockAccount(Long id, Long otherId) {
        if (id < otherId) {
            return accountRepository.lockById(id);
        } else {
            return accountRepository.lockById(id);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Pair<List<TransferResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<Transfer> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<Transfer> pg = transferRepository.findAll(spec, pageable);
        List<TransferResponse> content = pg.getContent().stream().map(transferMapper::toResponse).collect(Collectors.toList());
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "transferTable"
        );
        return Pair.of(content, metadata);
    }
}
