package com.biobac.company.service.impl;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Detail;
import com.biobac.company.mapper.DetailsMapper;
import com.biobac.company.repository.DetailsRepository;
import com.biobac.company.request.DetailRequest;
import com.biobac.company.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailsRepository detailsRepository;
    private final DetailsMapper detailsMapper;

    @Override
    public Detail createDetail(DetailRequest request, Company company) {
        Detail detail = detailsMapper.toDetailEntity(request);
        detail.setCompany(company);
        return detailsRepository.save(detail);
    }

    @Override
    public Detail updateDetail(Long id, DetailRequest request, Company company) {
        return detailsRepository.findByCompanyId(id)
                .map(detail -> {
                    Detail updatedDetail = detailsMapper.upadateDetail(request, company.getDetail());
                    updatedDetail.setCompany(company);
                    return detailsRepository.save(updatedDetail);
                })
                .orElseThrow(() -> new RuntimeException("Detail not found"));
    }

    @Override
    public Detail fetchDetailById(Long id) {
        return detailsRepository.findByCompanyId(id)
                .orElseThrow(() -> new RuntimeException("Detail not found"));
    }
}
