package com.biobac.company.service.impl;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.Detail;
import com.biobac.company.entity.OurCompany;
import com.biobac.company.entity.enums.AttributeTargetType;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.DetailsMapper;
import com.biobac.company.mapper.OurCompanyMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.OurCompanyRepository;
import com.biobac.company.request.AttributeUpsertRequest;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OurCompanyServiceImpl implements OurCompanyService {
    private final OurCompanyRepository ourCompanyRepository;
    private final OurCompanyMapper ourCompanyMapper;
    private final AccountRepository accountRepository;
    private final AttributeClient attributeClient;
    private final DetailsMapper detailsMapper;

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
        OurCompany ourCompany = ourCompanyMapper.toOurCompanyEntity(request);

        List<Account> accounts = request.getAccountIds() != null
                ? accountRepository.findAllById(request.getAccountIds())
                : Collections.emptyList();

        if (request.getDetail() != null) {
            Detail detail = detailsMapper.toDetailEntity(request.getDetail());
            detail.setOurCompany(ourCompany);
            ourCompany.setDetail(detail);
        }

        ourCompany.setName(request.getName());
        ourCompany.setAccounts(accounts);
        OurCompany saved = ourCompanyRepository.save(ourCompany);
        accounts.forEach(a -> a.setOurCompany(saved));
        accountRepository.saveAll(accounts);

        List<AttributeUpsertRequest> attributes = request.getAttributeGroupIds() == null || request.getAttributeGroupIds().isEmpty()
                ? Collections.emptyList()
                : request.getAttributes();

        attributeClient.updateValues(
                saved.getId(),
                AttributeTargetType.COMPANY.name(),
                request.getAttributeGroupIds(),
                attributes
        );

        return ourCompanyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OurCompanyResponse update(Long id, OurCompanyRequest request) {
        OurCompany existing = ourCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Our company not found"));

        existing.setName(request.getName());
        ourCompanyMapper.updateOurCompanyFromRequest(request, existing);

        List<Long> newIds = request.getAccountIds() == null
                ? Collections.emptyList()
                : request.getAccountIds();

        List<Account> existingAccounts = existing.getAccounts();
        List<Account> accountsToRemove = new ArrayList<>(existingAccounts);
        Detail existingDetail = existing.getDetail();
        accountsToRemove.removeIf(acc -> newIds.contains(acc.getId()));

        if (request.getDetail() != null) {
            if (existingDetail != null) {
                detailsMapper.upadateDetail(request.getDetail(), existingDetail);
            } else {
                Detail newDetail = detailsMapper.toDetailEntity(request.getDetail());
                newDetail.setOurCompany(existing);
                existing.setDetail(newDetail);
            }
        } else if (existingDetail != null) {
            existingDetail.setOurCompany(null);
            existing.setDetail(null);
        }

        for (Account account : accountsToRemove) {
            account.setOurCompany(null);
            existingAccounts.remove(account);
        }

        List<Account> allNewAccounts = accountRepository.findAllById(newIds);

        for (Account newAccount : allNewAccounts) {
            if (!existingAccounts.contains(newAccount)) {
                newAccount.setOurCompany(existing);
                existingAccounts.add(newAccount);
            }
        }

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
