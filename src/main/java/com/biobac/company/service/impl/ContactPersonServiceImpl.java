package com.biobac.company.service.impl;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.ContactPerson;
import com.biobac.company.mapper.ContactPersonMapper;
import com.biobac.company.repository.ContactPersonRepository;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactPersonService;
import com.biobac.company.utils.specifications.SimpleEntitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContactPersonServiceImpl implements ContactPersonService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIR = "desc";

    private final ContactPersonRepository contactPersonRepository;
    private final ContactPersonMapper contactPersonMapper;

    @Override
    public ContactPersonResponse createContactPerson(ContactPersonRequest request) {
        ContactPerson contactPerson = contactPersonMapper.toContactPersonEntity(request);
        ContactPerson save = contactPersonRepository.save(contactPerson);
        return contactPersonMapper.toContactPersonResponse(save);
    }

    @Override
    public ContactPersonResponse getContactPersonById(Long id) {
        return contactPersonRepository.findById(id)
                .map(contactPersonMapper::toContactPersonResponse)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public List<ContactPersonResponse> getAllContactPerson() {
        return contactPersonRepository.findAll().stream()
                .map(contactPersonMapper::toContactPersonResponse)
                .toList();
    }

    @Override
    public ContactPersonResponse updateContactPerson(Long id, ContactPersonRequest request) {
        return contactPersonRepository.findById(id)
                .map(contact -> {
                    ContactPerson contactPerson = contactPersonMapper.toUpdatedContactPersonResponse(contact, request);
                    ContactPerson updatedContactPerson = contactPersonRepository.save(contactPerson);
                    return contactPersonMapper.toContactPersonResponse(updatedContactPerson);
                })
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public Pair<List<ContactPersonResponse>, PaginationMetadata> getAll(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);
        Specification<ContactPerson> spec = SimpleEntitySpecification.buildSpecification(filters);
        Page<ContactPerson> pg = contactPersonRepository.findAll(spec, pageable);
        List<ContactPersonResponse> content = pg.getContent().stream().map(contactPersonMapper::toContactPersonResponse).toList();
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

    @Override
    public void deleteContactPerson(Long id) {
        contactPersonRepository.deleteById(id);
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
