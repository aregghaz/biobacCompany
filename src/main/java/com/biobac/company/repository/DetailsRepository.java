package com.biobac.company.repository;

import com.biobac.company.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Detail, Long>, JpaSpecificationExecutor<Detail> {
    Optional<Detail> findByCompanyId(Long id);
}
