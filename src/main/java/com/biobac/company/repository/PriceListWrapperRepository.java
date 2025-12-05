package com.biobac.company.repository;

import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.projection.PriceListItemParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceListWrapperRepository extends JpaRepository<PriceListWrapper, Long>, JpaSpecificationExecutor<PriceListWrapper> {
//    void bulkInsertItems(@Param("wrapperId") Long wrapperId, List<PriceListItemParams> items);
}
