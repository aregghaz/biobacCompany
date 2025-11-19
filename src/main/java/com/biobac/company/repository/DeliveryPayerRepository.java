package com.biobac.company.repository;

import com.biobac.company.entity.DeliveryPayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryPayerRepository extends JpaRepository<DeliveryPayer, Long>, JpaSpecificationExecutor<DeliveryPayer> {
}
