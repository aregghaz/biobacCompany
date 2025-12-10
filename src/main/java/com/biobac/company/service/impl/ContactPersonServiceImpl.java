package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.ContactPerson;
import com.biobac.company.mapper.ContactPersonMapper;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.repository.ContactPersonRepository;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactPersonService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactPersonServiceImpl implements ContactPersonService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    private final ContactPersonRepository contactPersonRepository;
    private final ContactPersonMapper contactPersonMapper;
    private final CompanyRepository companyRepository;

    private final Map<Long, Long> lastUpdatedCompanyMap = new ConcurrentHashMap<>();


    @Override
    @Transactional
    public ContactPersonResponse createContactPerson(ContactPersonRequest request) {
        ContactPerson contactPerson = contactPersonMapper.toContactPersonEntity(request);

        if (contactPerson.getCompany() == null) {
            contactPerson.setCompany(new ArrayList<>());
        }

        if (request.getCompanyId() != null) {
            companyRepository.findById(request.getCompanyId()).ifPresent(company -> {
                contactPerson.getCompany().add(company);

                if (company.getContactPerson() == null) {
                    company.setContactPerson(new ArrayList<>());
                }
                company.getContactPerson().add(contactPerson);
            });
        }


        ContactPerson save = contactPersonRepository.save(contactPerson);
        return syncContactPersonResponse(save);
    }


    @Override
    @Transactional(readOnly = true)
    public ContactPersonResponse getContactPersonById(Long id) {
        return contactPersonRepository.findByIdWithCompany(id).map(contactPerson -> {
            ContactPersonResponse response = contactPersonMapper.toContactPersonResponse(contactPerson);
            Long lastUpdatedCompanyId = lastUpdatedCompanyMap.get(id);

            if (lastUpdatedCompanyId != null) {
                boolean companyExists = contactPerson.getCompany().stream()
                        .anyMatch(company -> company.getId().equals(lastUpdatedCompanyId));

                if (companyExists) {
                    response.setCompanyId(lastUpdatedCompanyId);
                } else if (!contactPerson.getCompany().isEmpty()) {
                    response.setCompanyId(contactPerson.getCompany().get(0).getId());
                }
            } else if (!contactPerson.getCompany().isEmpty()) {
                response.setCompanyId(contactPerson.getCompany().get(0).getId());
            }

            return response;
        }).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactPersonResponse> getAllContactPerson() {
        return contactPersonRepository.findAll().stream().map(contactPerson -> {
            Long lastElement = contactPerson.getCompany().stream().map(Company::getId).reduce((first, second) -> second).orElse(null);
            ContactPersonResponse response = contactPersonMapper.toContactPersonResponse(contactPerson);
            response.setCompanyId(lastElement);
            return response;
        }).toList();
    }

    @Override
    @Transactional
    public ContactPersonResponse updateContactPerson(Long id, ContactPersonRequest request) {
        return contactPersonRepository.findByIdWithCompany(id).map(contact -> {
            contactPersonMapper.toUpdatedContactPersonFromResponse(contact, request);
            if (request.getCompanyId() != null) {
                Company newCompany = companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

                contact.getCompany().clear();
                contact.getCompany().add(newCompany);

                if (!newCompany.getContactPerson().contains(contact)) {
                    newCompany.getContactPerson().add(contact);
                }
            }

            ContactPerson updated = contactPersonRepository.save(contact);

            if (request.getCompanyId() != null) {
                lastUpdatedCompanyMap.put(id, request.getCompanyId());
            }
            return syncContactPersonResponse(updated);
        }).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<ContactPersonResponse>, PaginationMetadata> getAll(Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<ContactPerson> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<ContactPerson> pg = contactPersonRepository.findAll(spec, pageable);
        List<ContactPersonResponse> content = pg.getContent().stream().map(contactPersonMapper::toContactPersonResponse).toList();
        PaginationMetadata metadata = new PaginationMetadata(pg.getNumber(), pg.getSize(), pg.getTotalElements(), pg.getTotalPages(), pg.isLast(), filters, pageable.getSort().toString().contains("ASC") ? "asc" : "desc", pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(DEFAULT_SORT_BY), "lineTable");
        return Pair.of(content, metadata);
    }

    @Override
    @Transactional
    public void deleteContactPerson(Long id) {
        contactPersonRepository.deleteById(id);
    }

    private ContactPersonResponse syncContactPersonResponse(ContactPerson contactPerson) {
        ContactPersonResponse response = contactPersonMapper.toContactPersonResponse(contactPerson);
        if (contactPerson.getCompany() != null && !contactPerson.getCompany().isEmpty()) {
            response.setCompanyId(contactPerson.getCompany().stream().findFirst().map(Company::getId).orElse(null));
        }
        return response;
    }

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
}
