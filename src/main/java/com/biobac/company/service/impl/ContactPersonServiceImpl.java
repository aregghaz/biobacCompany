package com.biobac.company.service.impl;

import com.biobac.company.entity.ContactPerson;
import com.biobac.company.mapper.ContactPersonMapper;
import com.biobac.company.repository.ContactPersonRepository;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactPersonServiceImpl implements ContactService {

    private final ContactPersonRepository contactPersonRepository;
    private final ContactPersonMapper contactPersonMapper;

    @Override
    public ContactPersonResponse createContact(ContactPersonRequest request) {
        ContactPerson contactPerson = contactPersonMapper.toContactPersonEntity(request);
        ContactPerson save = contactPersonRepository.save(contactPerson);
        return contactPersonMapper.toContactPersonResponse(save);
    }

    @Override
    public ContactPersonResponse getContactById(Long id) {
        return contactPersonRepository.findById(id)
                .map(contactPersonMapper::toContactPersonResponse)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }
}
