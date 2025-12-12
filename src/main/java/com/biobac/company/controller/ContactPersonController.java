package com.biobac.company.controller;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ContactPersonResponse;
import com.biobac.company.service.ContactPersonService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company/contact-person")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    @PostMapping
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
    public ApiResponse<List<ContactPersonResponse>> getAllContactPersons() {
        List<ContactPersonResponse> responses = contactPersonService.getAllContactPerson();
        return ResponseUtil.success("Contacts retrieved successfully", responses);
    }

    @PostMapping("/all")
    public ApiResponse<List<ContactPersonResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestBody Map<String, FilterCriteria> filters
    ) {
        Pair<List<ContactPersonResponse>, PaginationMetadata> result = contactPersonService.getAll(filters, page, size, sortBy, sortDir);
        return ResponseUtil.success("Contact retrieved successfully", result.getFirst(), result.getSecond());
    }

    @PutMapping("/{id}")
    public ApiResponse<ContactPersonResponse> updateContact(@PathVariable Long id, @RequestBody ContactPersonRequest request) {
        ContactPersonResponse updatedContact = contactPersonService.updateContactPerson(id, request);
        return ResponseUtil.success("Contact updated successfully", updatedContact);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteContact(@PathVariable Long id) {
        contactPersonService.deleteContactPerson(id);
        return ResponseUtil.success("Contact deleted successfully");
    }
}