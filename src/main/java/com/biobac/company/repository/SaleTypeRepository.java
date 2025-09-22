package com.biobac.company.repository;

import com.biobac.company.entity.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleTypeRepository extends JpaRepository<SaleType, Long>, JpaSpecificationExecutor<SaleType> {
}
