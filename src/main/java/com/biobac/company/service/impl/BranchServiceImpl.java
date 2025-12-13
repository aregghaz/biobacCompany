package com.biobac.company.service.impl;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.mapper.BranchMapper;
import com.biobac.company.repository.BranchRepository;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.request.BranchUpdateRequest;
import com.biobac.company.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public Branch createBranchForCompany(BranchRequest request, Company company) {
        Branch branch = branchMapper.toBranchEntity(request);
        branch.setCompany(company);
        return branch;
    }

    @Override
    public Branch createBranchForCompanyDefault(BranchUpdateRequest request, Company company) {
        Branch branch = branchMapper.toBranchEntityDefault(request);
        branch.setCompany(company);
        return branch;
    }

    @Override
    public Branch updateBranch(Long id, BranchUpdateRequest request, Company company) {
        return branchRepository.findById(id)
                .map(branch -> {
                    Branch updatedBranch = branchMapper.updateBranch(request, company.getBranches().get(0));
                    updatedBranch.setCompany(company);
                    return branchRepository.save(updatedBranch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    @Override
    public void deleteBranches(List<Branch> branches) {
        if (branches != null && !branches.isEmpty()) {
            branches.forEach(branch -> {
                if (branch.getId() != null) {
                    branchRepository.deleteById(branch.getId());
                }
            });
        }
    }
}
