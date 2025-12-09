package com.biobac.company.repository;

import com.biobac.company.entity.PriceListWrapper;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PriceListWrapperRepository extends JpaRepository<PriceListWrapper, Long>, JpaSpecificationExecutor<PriceListWrapper> {

    @NonNull
    @EntityGraph(attributePaths = "priceListItems")
    Page<PriceListWrapper> findAll(@NonNull Specification<PriceListWrapper> spec, @NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = "priceListItems")
    Optional<PriceListWrapper> findById(@NonNull Long id);

    @Query("SELECT p FROM PriceListWrapper p LEFT JOIN FETCH p.priceListItems WHERE p.id = :id")
    Optional<PriceListWrapper> fetchWithItems(@Param("id") Long id);
}
