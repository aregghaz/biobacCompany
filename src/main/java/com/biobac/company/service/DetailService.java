package com.biobac.company.service;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailsRequest;

public interface DetailService {

    Detail createDetail(DetailsRequest request, Company company);
    Detail updateDetail(Long id, DetailsRequest request, Company company);
    Detail fetchDetailById(Long id);
}
