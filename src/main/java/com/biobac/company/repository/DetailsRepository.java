package com.biobac.company.repository;

import com.biobac.company.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Details, Long>, JpaSpecificationExecutor<Details> {
    Optional<Details> findByCompanyId(Long id);
}
