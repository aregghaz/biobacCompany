package com.biobac.company.service;

import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.SourceResponse;

import java.util.List;

public interface SourceService {

    SourceResponse createSource(SourceRequest request);

    List<SourceResponse> getAll();
}
