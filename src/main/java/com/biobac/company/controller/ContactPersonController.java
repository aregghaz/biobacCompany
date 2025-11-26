package com.biobac.company.controller;

import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactPersonService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact-person")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    @PostMapping("/create")
    public ApiResponse<ContactPersonResponse> createContact(@RequestBody ContactPersonRequest request) {
        ContactPersonResponse contact = contactPersonService.createContactPerson(request);
        return ResponseUtil.success("Contact created successfully", contact);
    }

    @GetMapping("/{id}")
    public ApiResponse<ContactPersonResponse> getContactById(@PathVariable Long id) {
        ContactPersonResponse contact = contactPersonService.getContactPersonById(id);
        return ResponseUtil.success("success", contact);
    }

    @GetMapping
    public ApiResponse<List<ContactPersonResponse>> getAllContactPersons(){
        List<ContactPersonResponse> responses = contactPersonService.getAllContactPerson();
        return ResponseUtil.success("Contacts retrieved successfully", responses);
    }
}