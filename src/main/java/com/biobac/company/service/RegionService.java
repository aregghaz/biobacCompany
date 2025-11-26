package com.biobac.company.service;

import com.biobac.company.request.CreateRegionRequest;
import com.biobac.company.response.CreateRegionResponse;
import com.biobac.company.response.RegionResponse;

import java.util.List;

public interface RegionService {
    CreateRegionResponse createRegion(CreateRegionRequest request);

    List<RegionResponse> getAll();
}
