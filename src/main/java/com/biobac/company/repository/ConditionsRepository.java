package com.biobac.company.repository;

import com.biobac.company.entity.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ConditionsRepository extends JpaRepository<Conditions, Long>, JpaSpecificationExecutor<Conditions> {
    Optional<Conditions> findByCompanyId(Long id);
}
