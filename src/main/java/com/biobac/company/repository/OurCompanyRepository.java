package com.biobac.company.repository;

import com.biobac.company.entity.OurCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OurCompanyRepository extends JpaRepository<OurCompany, Long>, JpaSpecificationExecutor<OurCompany> {
}
