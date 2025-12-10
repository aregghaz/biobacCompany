package com.biobac.company.repository;

import com.biobac.company.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long>, JpaSpecificationExecutor<ContactPerson> {

    @Query("""
               SELECT cp FROM ContactPerson cp
               LEFT JOIN FETCH cp.company
               WHERE cp.id = :id
            """)
    Optional<ContactPerson> findByIdWithCompany(@Param("id") Long id);

}
