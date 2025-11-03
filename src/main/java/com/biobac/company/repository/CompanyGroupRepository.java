package com.biobac.company.repository;

import com.biobac.company.entity.CompanyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long>, JpaSpecificationExecutor<CompanyGroup> {
}
