package com.biobac.company.service.impl;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.mapper.BranchMapper;
import com.biobac.company.repository.BranchRepository;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public Branch createBranchForCompany(BranchRequest request, Company company) {
        Branch branch = branchMapper.toBranchEntity(request);
        branch.setCompany(company);
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Long companyId, BranchRequest request, Company company) {
        return branchRepository.findByCompanyId(companyId)
                .map(branch -> {
                    Branch updatedBranch = branchMapper.updateBranch(request, company.getBranches().get(0));
                    updatedBranch.setCompany(company);
                    return branchRepository.save(updatedBranch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }
}
