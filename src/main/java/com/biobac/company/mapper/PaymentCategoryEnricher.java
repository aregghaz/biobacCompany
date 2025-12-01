package com.biobac.company.mapper;

import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.EmployeeResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentCategoryEnricher {
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<EmployeeResponse> getEmployeesSafe() {
        try {
            return employeeService.getAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CompanyResponse> getSellersSafe() {
        try {
            return companyService.listAllSellersCompanies();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CompanyResponse> getBuyersSafe() {
        try {
            return companyService.listAllBuyersCompanies();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CompanyResponse> getSafeSeller() {
        try {
            return companyService.listAllCompaniesBySeller();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
