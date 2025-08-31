package com.biobac.company.service.impl;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Company;
import com.biobac.company.exception.DuplicateException;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.CompanyMapper;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.utils.specifications.CompanySpecification;
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
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyDto registerCompany(CompanyDto dto) {
        if (companyRepository.existsByName(dto.getName())) {
            throw new DuplicateException("Company with name " + dto.getName() + " already exists.");
        }
        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .map(companyMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " does not exist."));
    }

    @Override
    @Transactional
    public CompanyDto updateCompany(Long id, CompanyDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " does not exist."));

        if (companyRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateException("Company with name " + dto.getName() + " already exists.");
        }

        companyMapper.updateEntityFromDto(dto, company);

        Company updatedCompany = companyRepository.save(company);
        return companyMapper.toDto(updatedCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundException("Company with ID " + companyId + " does not exist.");
        }
        companyRepository.deleteById(companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Company> spec = CompanySpecification.buildSpecification(filters);

        Page<Company> companyPage = companyRepository.findAll(spec, pageable);

        List<CompanyResponse> content = companyPage.getContent()
                .stream()
                .map(companyMapper::toResponse)
                .toList();

        PaginationMetadata metadata = new PaginationMetadata(
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages(),
                companyPage.isLast(),
                filters,
                sortDir,
                sortBy,
                "companyTable"
        );

        return Pair.of(content, metadata);
    }
}
