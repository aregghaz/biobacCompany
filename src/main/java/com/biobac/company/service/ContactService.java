package com.biobac.company.service;

import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;

public interface ContactService {

    ContactPersonResponse createContact(ContactPersonRequest request);

    ContactPersonResponse getContactById(Long id);

}
