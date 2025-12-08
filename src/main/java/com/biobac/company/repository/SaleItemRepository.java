package com.biobac.company.repository;

import com.biobac.company.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long>, JpaSpecificationExecutor<SaleItem> {
}
