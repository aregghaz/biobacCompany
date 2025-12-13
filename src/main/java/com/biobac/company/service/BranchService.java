package com.biobac.company.service;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.request.BranchUpdateRequest;

import java.util.List;

public interface BranchService {

    Branch createBranchForCompany(BranchRequest request, Company company);

    Branch createBranchForCompanyDefault(BranchUpdateRequest request, Company company);

    Branch updateBranch(Long id, BranchUpdateRequest request, Company company);

    void deleteBranches(List<Branch> branchesToDelete);
}
