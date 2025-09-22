package com.biobac.company.service.impl;

import com.biobac.company.repository.SaleTypeRepository;
import com.biobac.company.response.SaleTypeResponse;
import com.biobac.company.service.SaleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleTypeServiceImpl implements SaleTypeService {
    private final SaleTypeRepository saleTypeRepository;

    @Override
    public List<SaleTypeResponse> getAll() {
        return saleTypeRepository.findAll()
                .stream()
                .map(s -> new SaleTypeResponse(s.getId(), s.getName())).toList();
    }
}
