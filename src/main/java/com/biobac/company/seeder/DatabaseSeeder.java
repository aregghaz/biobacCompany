package com.biobac.company.seeder;

import com.biobac.company.entity.CompanyType;
import com.biobac.company.repository.CompanyTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanyTypeRepository companyTypeRepository;

    public DatabaseSeeder(CompanyTypeRepository companyTypeRepository) {
        this.companyTypeRepository = companyTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (companyTypeRepository.count() == 0) {
            CompanyType companyType1 = new CompanyType("buyer");
            companyTypeRepository.save(companyType1);

            CompanyType companyType2 = new CompanyType("seller");
            companyTypeRepository.save(companyType2);
        }
    }
}