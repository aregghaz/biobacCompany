package com.biobac.company.service;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailRequest;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;

public interface DetailService {

    Detail createDetail(DetailRequest request, Company company);

    DetailsResponse getDetailById(Long id);

}
