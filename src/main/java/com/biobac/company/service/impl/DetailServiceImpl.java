package com.biobac.company.service.impl;

import com.biobac.company.entity.Detail;
import com.biobac.company.mapper.DetailsMapper;
import com.biobac.company.repository.DetailsRepository;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;
import com.biobac.company.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailsRepository detailsRepository;
    private final DetailsMapper detailsMapper;

    @Override
    public DetailsResponse createDetail(DetailsRequest request) {
        Detail detail = detailsMapper.toDetailEntity(request);
        Detail savedDetail = detailsRepository.save(detail);
        return detailsMapper.toDetailsResponse(savedDetail);
    }

    @Override
    public DetailsResponse getDetailById(Long id) {
        return detailsRepository.findById(id)
                .map(detailsMapper::toDetailsResponse)
                .orElseThrow(() -> new RuntimeException("Detail not found"));
    }
}
