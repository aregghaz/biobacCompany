package com.biobac.company.repository;

import com.biobac.company.entity.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientTypeRepository extends JpaRepository<ClientType, Long>, JpaSpecificationExecutor<ClientType> {
}
