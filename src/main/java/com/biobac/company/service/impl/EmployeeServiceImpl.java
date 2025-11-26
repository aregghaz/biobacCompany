package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Employee;
import com.biobac.company.entity.OurCompany;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.EmployeeMapper;
import com.biobac.company.repository.EmployeeRepository;
import com.biobac.company.repository.OurCompanyRepository;
import com.biobac.company.request.EmployeeRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.EmployeeResponse;
import com.biobac.company.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OurCompanyRepository ourCompanyRepository;

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
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);

        OurCompany ourCompany = ourCompanyRepository.findById(request.getOurCompanyId())
                .orElseThrow(() -> new NotFoundException("Our company not found"));

        employee.setOurCompany(ourCompany);

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        employeeMapper.updateEmployeeFromRequest(existingEmployee, request);

        OurCompany ourCompany = ourCompanyRepository.findById(request.getOurCompanyId())
                .orElseThrow(() -> new NotFoundException("Our company not found"));

        existingEmployee.setOurCompany(ourCompany);

        Employee updated = employeeRepository.save(existingEmployee);

        return employeeMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employeeRepository.delete(existingEmployee);
    }

    @Override
    @Transactional
    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public Pair<List<EmployeeResponse>, PaginationMetadata> getPagination(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<Employee> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<Employee> pg = employeeRepository.findAll(spec, pageable);
        List<EmployeeResponse> content = pg.getContent().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
        PaginationMetadata metadata = new PaginationMetadata(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isLast(),
                filters,
                pageable.getSort().toString().contains("ASC") ? "asc" : "desc",
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY),
                "employeeTable"
        );
        return Pair.of(content, metadata);
    }
}
