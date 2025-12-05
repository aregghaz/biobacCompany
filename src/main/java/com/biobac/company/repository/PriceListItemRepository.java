package com.biobac.company.repository;

import com.biobac.company.entity.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
}
