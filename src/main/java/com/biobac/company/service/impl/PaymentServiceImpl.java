package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.dto.PaymentHistoryDto;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PaymentMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.repository.PaymentCategoryRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.request.PaymentRequest;
import com.biobac.company.response.PaymentCategoryResponse;
import com.biobac.company.service.PaymentHistoryService;
import com.biobac.company.service.PaymentService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentHistoryService paymentHistoryService;

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
    public PaymentCategoryResponse createCategory(PaymentCategoryRequest request) {
        PaymentCategory category = new PaymentCategory();
        category.setName(request.getName());

        if (request.getParentId() != null) {
            PaymentCategory parent = paymentCategoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent not found"));
            category.setParent(parent);
        }

        PaymentCategory saved = paymentCategoryRepository.save(category);
        return paymentMapper.toCategoryResponse(saved, new PaymentMapper.CycleAvoidingMappingContext());
    }

    @Override
    @Transactional
    public PaymentCategoryResponse updateCategory(Long id, PaymentCategoryRequest request) {
        PaymentCategory category = paymentCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));
        category.setName(request.getName());

        if (request.getParentId() != null) {
            PaymentCategory parent = paymentCategoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent not found"));
            category.setParent(parent);
        }

        PaymentCategory saved = paymentCategoryRepository.save(category);
        return paymentMapper.toCategoryResponse(saved, new PaymentMapper.CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<PaymentCategoryResponse>, PaginationMetadata> getCategoryPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<PaymentCategory> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<PaymentCategory> pg = paymentCategoryRepository.findAll(spec, pageable);
        List<PaymentCategoryResponse> content = pg.getContent().stream().map(pc -> paymentMapper.toCategoryResponse(pc, new PaymentMapper.CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "paymentCategoryTable"
        );
        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentCategoryResponse getById(Long id) {
        PaymentCategory category = paymentCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));
        return paymentMapper.toCategoryResponse(category, new PaymentMapper.CycleAvoidingMappingContext());
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        PaymentCategory category = paymentCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            for (PaymentCategory child : category.getChildren()) {
                child.setParent(null);
            }
            paymentCategoryRepository.saveAll(category.getChildren());
            category.getChildren().clear();
        }

        category.setParent(null);

        paymentCategoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentCategoryResponse> getCategoryAll() {
        return paymentCategoryRepository.findAll()
                .stream()
                .map(pc -> paymentMapper.toCategoryResponse(pc, new PaymentMapper.CycleAvoidingMappingContext()))
                .toList();
    }

    @Override
    @Transactional
    public void payment(PaymentRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        PaymentCategory category = paymentCategoryRepository.findById(request.getPaymentCategoryId())
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));

        if (request.getSum() == null) {
            throw new IllegalArgumentException("Payment sum is required");
        }

        account.setBalance(account.getBalance().subtract(request.getSum()));
        accountRepository.save(account);

        PaymentHistoryDto dto = new PaymentHistoryDto();
        dto.setDate(request.getDate() == null ? LocalDateTime.now() : request.getDate());
        dto.setAccountId(account.getId());
        dto.setPaymentCategoryId(category.getId());
        dto.setIncreased(false);
        dto.setNotes(request.getNotes());
        dto.setSum(request.getSum());
        paymentHistoryService.recordHistory(dto);
    }
}
