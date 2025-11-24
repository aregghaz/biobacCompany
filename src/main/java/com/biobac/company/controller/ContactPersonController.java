package com.biobac.company.controller;

import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact-person")
public class ContactPersonController {

    private final ContactService contactService;

    @PostMapping("/create")
    public ApiResponse<ContactPersonResponse> createContact(@RequestBody ContactPersonRequest request) {
        ContactPersonResponse contact = contactService.createContact(request);
        return ResponseUtil.success("Contact created successfully", contact);
    }

    @GetMapping("/{id}")
    public ApiResponse<ContactPersonResponse> getContactById(@PathVariable Long id) {
        ContactPersonResponse contact = contactService.getContactById(id);
        return ResponseUtil.success("success", contact);
    }

}
