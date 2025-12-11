package com.biobac.company.repository;

import com.biobac.company.entity.CompanyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyHistoryRepository extends JpaRepository<CompanyHistory, Long> {
}
