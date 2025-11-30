package com.biobac.company.repository;

import com.biobac.company.entity.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Long>, JpaSpecificationExecutor<EmployeeHistory> {
}
