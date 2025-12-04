package com.biobac.company.service.impl;

import com.biobac.company.entity.Branch;
import com.biobac.company.entity.Company;
import com.biobac.company.mapper.BranchMapper;
import com.biobac.company.repository.BranchRepository;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.response.BranchResponse;
import com.biobac.company.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public BranchResponse createBranch(BranchRequest request) {
        Branch branch = branchMapper.toBranchEntity(request);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toBranchResponse(savedBranch);
    }

    @Override
    public Branch createBranchForCompany(BranchRequest request, Company company) {
        Branch branch = branchMapper.toBranchEntity(request);
        branch.setCompany(company);
        return branchRepository.save(branch);
    }

    @Override
    public BranchResponse getBranchById(Long id) {
        return branchRepository.findById(id)
                .map(branch -> branchMapper.toBranchResponse(branch))
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    @Override
    public Branch fetchBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }
}
