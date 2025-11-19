package com.biobac.company.repository;

import com.biobac.company.entity.Cooperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CooperationRepository  extends JpaRepository<Cooperation, Long>, JpaSpecificationExecutor<Cooperation> {
}
