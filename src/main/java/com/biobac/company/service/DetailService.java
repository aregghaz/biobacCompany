package com.biobac.company.service;

import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;

public interface DetailService {

    DetailsResponse createDetail(DetailsRequest request);

    DetailsResponse getDetailById(Long id);

}
