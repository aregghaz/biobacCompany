package com.biobac.company.service.impl;

import com.biobac.company.client.UserClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.dto.PaymentHistoryDto;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.entity.PaymentHistory;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PaymentHistoryMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.PaymentCategoryRepository;
import com.biobac.company.repository.PaymentHistoryRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.PaymentHistoryResponse;
import com.biobac.company.response.UserResponse;
import com.biobac.company.service.PaymentHistoryService;
import com.biobac.company.utils.SecurityUtil;
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
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final AccountRepository accountRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final UserClient userClient;
    private final SecurityUtil securityUtil;

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
    public void recordHistory(PaymentHistoryDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        PaymentCategory paymentCategory = paymentCategoryRepository.findById(dto.getPaymentCategoryId())
                .orElseThrow(() -> new NotFoundException("Payment category not found"));
        Long userId = securityUtil.getCurrentUserId();

        PaymentHistory history = new PaymentHistory();
        history.setSum(dto.getSum());
        history.setNotes(dto.getNotes());
        history.setDate(dto.getDate());
        history.setAccount(account);
        history.setPaymentCategory(paymentCategory);
        history.setUserId(userId);

        paymentHistoryRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<PaymentHistoryResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<PaymentHistory> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<PaymentHistory> pg = paymentHistoryRepository.findAll(spec, pageable);
        List<PaymentHistoryResponse> content = pg.getContent().stream().map(entity -> {
            PaymentHistoryResponse resp = paymentHistoryMapper.toResponse(entity);
            try {
                ApiResponse<UserResponse> ur = userClient.getUser(entity.getUserId());
                if (ur != null && Boolean.TRUE.equals(ur.getSuccess()) && ur.getData() != null) {
                    String fn = ur.getData().getFirstname();
                    String ln = ur.getData().getLastname();
                    String un = ur.getData().getUsername();
                    StringBuilder sb = new StringBuilder();
                    if (fn != null && !fn.isBlank()) sb.append(fn);
                    if (ln != null && !ln.isBlank()) {
                        if (!sb.isEmpty()) sb.append(' ');
                        sb.append(ln);
                    }
                    if (un != null && !un.isBlank()) {
                        if (!sb.isEmpty()) sb.append(' ');
                        sb.append('(').append(un).append(')');
                    }
                    resp.setUsername(sb.toString());
                }
            } catch (Exception ignored) {
                // In case user service fails, leave username unset
            }
            return resp;
        }).toList();
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "paymentHistoryTable"
        );
        return Pair.of(content, metadata);
    }
}
