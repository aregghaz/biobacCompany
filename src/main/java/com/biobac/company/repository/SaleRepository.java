package com.biobac.company.repository;

import com.biobac.company.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

//    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.buyerCompany.companyHistories WHERE s.id = :id")
//    Optional<Sale> findByIdWithCompanyHistory(Long id);
}
