package com.biobac.company.repository;

import com.biobac.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    @Query("select c.name from Company c where c.id = :id")
    String findCompanyNameById(@Param("id") Long id);

    @Query("SELECT c from Company c join CompanyHistory ch on c.id = ch.company.id where ch.id = :historyId")
    Optional<Company> findByHistoryId(Long historyId);

    boolean existsByNameAndIdNot(String name, Long id);
}