package com.biobac.company.repository;

import com.biobac.company.entity.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Long>, JpaSpecificationExecutor<PaymentCategory> {

    @Query("""
            SELECT c FROM PaymentCategory c
            LEFT JOIN FETCH c.children
                WHERE c.id = :id
            """)
    Optional<PaymentCategory> findByIdWithChildren(@Param("id") Long id);
}
