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

    @Query(value = """
                WITH RECURSIVE subordinates AS (
                    SELECT id FROM payment_category WHERE id = :currentId
                    UNION ALL
                    SELECT c.id FROM payment_category c
                    INNER JOIN subordinates s ON s.id = c.parent_id
                )
                SELECT COUNT(*) FROM subordinates WHERE id = :targetParentId
            """, nativeQuery = true)
    Long countDescendants(@Param("currentId") Long currentId, @Param("targetParentId") Long targetParentId);

    default boolean isDescendant(Long currentId, Long targetParentId) {
        return countDescendants(currentId, targetParentId) > 0;
    }
}
