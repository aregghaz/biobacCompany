package com.biobac.company.repository;

import com.biobac.company.entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long>, JpaSpecificationExecutor<CompanyType> {
}
