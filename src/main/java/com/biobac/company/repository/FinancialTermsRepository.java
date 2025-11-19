package com.biobac.company.repository;

import com.biobac.company.entity.FinancialTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FinancialTermsRepository extends JpaRepository<FinancialTerms, Long>, JpaSpecificationExecutor<FinancialTerms> {
}
