package com.biobac.company.service;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailRequest;

public interface DetailService {

    Detail createDetail(DetailRequest request, Company company);
    Detail updateDetail(Long id, DetailRequest request, Company company);
    Detail fetchDetailById(Long id);
}
