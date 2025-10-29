package com.biobac.company.repository;

import com.biobac.company.entity.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long>, JpaSpecificationExecutor<BalanceHistory> {
}
