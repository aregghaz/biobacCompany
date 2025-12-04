package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ContactPersonResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface ContactPersonService {
    ContactPersonResponse createContactPerson(ContactPersonRequest request);

    ContactPersonResponse getContactPersonById(Long id);

    List<ContactPersonResponse> getAllContactPerson();

    ContactPersonResponse updateContactPerson(Long id, ContactPersonRequest request);

    Pair<List<ContactPersonResponse>, PaginationMetadata> getAll(
            Map<String, FilterCriteria> filters, Integer page, Integer size, String sortBy, String sortDir
    );

    void deleteContactPerson(Long id);

}
