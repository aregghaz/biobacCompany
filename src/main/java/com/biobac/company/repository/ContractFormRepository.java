package com.biobac.company.repository;

import com.biobac.company.entity.ContractForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractFormRepository extends JpaRepository<ContractForm, Long>, JpaSpecificationExecutor<ContractForm> {
}
