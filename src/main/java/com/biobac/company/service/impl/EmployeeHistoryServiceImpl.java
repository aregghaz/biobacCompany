package com.biobac.company.service.impl;

import com.biobac.company.dto.EmployeeHistoryDto;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Employee;
import com.biobac.company.entity.EmployeeHistory;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.EmployeeHistoryMapper;
import com.biobac.company.mapper.LineMapper;
import com.biobac.company.repository.EmployeeHistoryRepository;
import com.biobac.company.repository.EmployeeRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.EmployeeHistoryResponse;
import com.biobac.company.service.EmployeeHistoryService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import jakarta.persistence.criteria.JoinType;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {
    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeHistoryMapper employeeHistoryMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";
    private final LineMapper lineMapper;
    private final EmployeeRepository employeeRepository;

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
    public void recordEmployeeHistory(EmployeeHistoryDto dto) {
        if (dto == null || dto.getEmployeeId() == null) {
            return;
        }
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        String oldWages = dto.getWagesBefore();
        String newWages = dto.getWagesAfter();
        String oldCash = dto.getCashBefore();
        String newCash = dto.getCashAfter();
        String oldCart = dto.getCartBefore();
        String newCart = dto.getCartAfter();

        if (!equalsNullable(oldWages, newWages)) {
            EmployeeHistory h = new EmployeeHistory();
            h.setEmployee(employee);
            h.setTimestamp(LocalDateTime.now());
            h.setNotes(String.format("Изменение зарплаты: было '%s', стало '%s'.",
                    safeString(oldWages), safeString(newWages)));
            trySetQuantities(h, oldWages, newWages);
            employeeHistoryRepository.save(h);
        }
        if (!equalsNullable(oldCash, newCash)) {
            EmployeeHistory h = new EmployeeHistory();
            h.setEmployee(employee);
            h.setTimestamp(LocalDateTime.now());
            h.setNotes(String.format("Изменение наличных: было '%s', стало '%s'.",
                    safeString(oldCash), safeString(newCash)));
            employeeHistoryRepository.save(h);
        }
        if (!equalsNullable(oldCart, newCart)) {
            EmployeeHistory h = new EmployeeHistory();
            h.setEmployee(employee);
            h.setTimestamp(LocalDateTime.now());
            h.setNotes(String.format("Изменение карты: было '%s', стало '%s'.",
                    safeString(oldCart), safeString(newCart)));
            employeeHistoryRepository.save(h);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<EmployeeHistoryResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir, Long employeeId) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<EmployeeHistory> spec = SimpleEntitySpecification.<EmployeeHistory>buildSpecification(filters)
                .and((root, query, cb) ->
                        cb.equal(root.join("employee", JoinType.LEFT).get("id"), employeeId));

        Page<EmployeeHistory> pg = employeeHistoryRepository.findAll(spec, pageable);
        List<EmployeeHistoryResponse> content = pg.getContent().stream().map(employeeHistoryMapper::toResponse).toList();
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "lineTable"
        );
        return Pair.of(content, metadata);
    }

    private static boolean equalsNullable(String a, String b) {
        return Objects.equals(a, b);
    }

    private static String safeString(String s) {
        return (s == null || s.isBlank()) ? "нет данных" : s;
    }

    private static void trySetQuantities(EmployeeHistory h, String before, String after) {
        BigDecimal b = parseBigDecimalSafe(before);
        BigDecimal a = parseBigDecimalSafe(after);
        if (b != null) {
            h.setQuantityBefore(b);
        }
        if (a != null) {
            h.setQuantityAfter(a);
        }
    }

    private static BigDecimal parseBigDecimalSafe(String val) {
        if (val == null) return null;
        String s = val.trim().replace(',', '.');
        if (s.isEmpty()) return null;
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
