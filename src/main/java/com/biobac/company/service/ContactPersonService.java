package com.biobac.company.service;

import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;

import java.util.List;

public interface ContactPersonService {
    ContactPersonResponse createContactPerson(ContactPersonRequest request);
    ContactPersonResponse getContactPersonById(Long id);
    List<ContactPersonResponse> getAllContactPerson();
}
