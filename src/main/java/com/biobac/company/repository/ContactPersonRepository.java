package com.biobac.company.repository;

import com.biobac.company.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long>, JpaSpecificationExecutor<ContactPerson> {
}
