package com.biobac.company.service;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.response.BranchResponse;

public interface BranchService {
    BranchResponse createBranch(BranchRequest request);

    Branch createBranchForCompany(BranchRequest request, Company company);

    BranchResponse getBranchById(Long id);

    Branch fetchBranchById(Long id);
}
