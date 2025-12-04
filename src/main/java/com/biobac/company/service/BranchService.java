package com.biobac.company.service;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.request.BranchRequest;

public interface BranchService {

    Branch createBranchForCompany(BranchRequest request, Company company);

    Branch updateBranch(Long companyId, BranchRequest branchRequest, Company company);
}
