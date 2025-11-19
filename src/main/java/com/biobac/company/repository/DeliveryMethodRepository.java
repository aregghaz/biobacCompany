package com.biobac.company.repository;

import com.biobac.company.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long>, JpaSpecificationExecutor<DeliveryMethod> {
}
