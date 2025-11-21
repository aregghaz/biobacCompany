package com.biobac.company.service.impl;

import com.biobac.company.entity.Region;
import com.biobac.company.mapper.RegionMapper;
import com.biobac.company.repository.RegionRepository;
import com.biobac.company.request.CreateRegionRequest;
import com.biobac.company.response.CreateRegionResponse;
import com.biobac.company.response.RegionResponse;
import com.biobac.company.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public CreateRegionResponse createRegion(CreateRegionRequest request) {
        Region region = regionMapper.toRegionEntity(request);
        Region savedRegion = regionRepository.save(region);
        return regionMapper.toRegionResponse(savedRegion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionResponse> getAll() {
        return regionRepository.findAll()
                .stream()
                .map(r -> new RegionResponse(r.getId(), r.getName(), r.getCode()))
                .toList();
    }
}
