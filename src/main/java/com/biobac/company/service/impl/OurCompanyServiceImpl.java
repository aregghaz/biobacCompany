package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.OurCompany;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.OurCompanyMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.OurCompanyRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.OurCompanyRequest;
import com.biobac.company.response.OurCompanyResponse;
import com.biobac.company.service.OurCompanyService;
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

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OurCompanyServiceImpl implements OurCompanyService {
    private final OurCompanyRepository ourCompanyRepository;
    private final OurCompanyMapper ourCompanyMapper;
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
    public OurCompanyResponse create(OurCompanyRequest request) {
        List<Account> accounts = accountRepository.findAllById(request.getAccountIds());

        OurCompany ourCompany = new OurCompany();
        ourCompany.setName(request.getName());
        ourCompany.setAccounts(accounts);
        OurCompany saved = ourCompanyRepository.save(ourCompany);
        accounts.forEach(a -> a.setOurCompany(saved));
        accountRepository.saveAll(accounts);

        return ourCompanyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OurCompanyResponse update(Long id, OurCompanyRequest request) {
        OurCompany existing = ourCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Our company not found"));

        List<Account> newAccounts = accountRepository.findAllById(request.getAccountIds());
        List<Account> oldAccounts = existing.getAccounts();

        existing.setName(request.getName());

        for (Account oldAcc : oldAccounts) {
            if (!newAccounts.contains(oldAcc)) {
                oldAcc.setOurCompany(null);
            }
        }

        for (Account newAcc : newAccounts) {
            if (newAcc.getOurCompany() == null || !newAcc.getOurCompany().getId().equals(id)) {
                newAcc.setOurCompany(existing);
            }
        }

        existing.setAccounts(newAccounts);

        accountRepository.saveAll(oldAccounts);
        accountRepository.saveAll(newAccounts);
        OurCompany updated = ourCompanyRepository.save(existing);

        return ourCompanyMapper.toResponse(updated);
    }


    @Override
    @Transactional(readOnly = true)
    public List<OurCompanyResponse> getAll() {
        return ourCompanyRepository.findAll().stream()
                .map(ourCompanyMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<OurCompanyResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<OurCompany> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<OurCompany> pg = ourCompanyRepository.findAll(spec, pageable);
        List<OurCompanyResponse> content = pg.getContent().stream().map(ourCompanyMapper::toResponse).toList();
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "ourCompanyTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public OurCompanyResponse getById(Long id) {
        OurCompany ourCompany = ourCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Our company not found"));
        return ourCompanyMapper.toResponse(ourCompany);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        OurCompany ourCompany = ourCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Our company not found"));
        List<Account> accounts = ourCompany.getAccounts();

        for (Account account : accounts) {
            account.setOurCompany(null);
            accountRepository.save(account);
        }

        ourCompanyRepository.delete(ourCompany);
    }
}
