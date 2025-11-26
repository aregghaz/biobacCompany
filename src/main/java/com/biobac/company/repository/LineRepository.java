package com.biobac.company.repository;

import com.biobac.company.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LineRepository extends JpaRepository<Line, Long>, JpaSpecificationExecutor<Line> {

}
