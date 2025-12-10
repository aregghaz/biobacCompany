package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PaymentCategoryMapper;
import com.biobac.company.repository.PaymentCategoryRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.response.PaymentCategoryResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.service.EmployeeService;
import com.biobac.company.service.PaymentCategoryService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentCategoryServiceImpl implements PaymentCategoryService {
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final PaymentCategoryMapper paymentCategoryMapper;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

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
        PaymentCategory category = paymentCategoryMapper.toEntity(request);

        if (request.getParentId() != null) {
            PaymentCategory parent = paymentCategoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent not found"));
            parent.addChild(category);
        }

        if (request.getCategory() != null) {
            category.setCategory(request.getCategory());
        }

        PaymentCategory saved = paymentCategoryRepository.save(category);
        PaymentCategory loadChildren = paymentCategoryRepository.findByIdWithChildren(saved.getId())
                .orElseThrow();

        return paymentCategoryMapper.toCategoryResponse(loadChildren, new PaymentCategoryMapper.CycleAvoidingContext());
    }

    @Override
    @Transactional
    public PaymentCategoryResponse updateCategory(Long id, PaymentCategoryRequest request) {
        PaymentCategory category = paymentCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));
        category.setName(request.getName());
        category.setCategory(request.getCategory());

        if (request.getParentId() != null) {
            if (id.equals(request.getParentId())) {
                throw new IllegalArgumentException("Category cannot be its own parent");
            }
            PaymentCategory parent = paymentCategoryRepository.getReferenceById(request.getParentId());

            if (paymentCategoryRepository.isDescendant(id, request.getParentId())) {
                throw new IllegalArgumentException("Category cannot be its own descendant (cycle detected)");
            }

            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        PaymentCategory saved = paymentCategoryRepository.save(category);
        PaymentCategory load = paymentCategoryRepository.findByIdWithChildren(saved.getId()).orElseThrow();
        return paymentCategoryMapper.toCategoryResponse(load, new PaymentCategoryMapper.CycleAvoidingContext());
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<PaymentCategoryResponse>, PaginationMetadata> getCategoryPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<PaymentCategory> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<PaymentCategory> pg = paymentCategoryRepository.findAll(spec, pageable);
        List<PaymentCategoryResponse> content = pg.getContent().stream().map(pc -> paymentCategoryMapper.toCategoryResponse(pc, new PaymentCategoryMapper.CycleAvoidingContext()))
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
        return paymentCategoryMapper.toCategoryResponse(category, new PaymentCategoryMapper.CycleAvoidingContext());
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
        List<PaymentCategory> categories = paymentCategoryRepository.findAll();
        PaymentCategoryMapper.CycleAvoidingContext ctx = new PaymentCategoryMapper.CycleAvoidingContext();
        return categories.stream()
                .map(pc -> paymentCategoryMapper.toCategoryResponse(pc, ctx))
                .collect(Collectors.toList());
    }
}