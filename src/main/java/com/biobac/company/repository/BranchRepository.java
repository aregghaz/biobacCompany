package com.biobac.company.repository;

import com.biobac.company.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCompanyId(Long companyId);
}
