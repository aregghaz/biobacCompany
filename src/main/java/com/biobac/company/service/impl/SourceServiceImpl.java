package com.biobac.company.service.impl;

import com.biobac.company.entity.Source;
import com.biobac.company.mapper.SourceMapper;
import com.biobac.company.repository.SourceRepository;
import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.SourceResponse;
import com.biobac.company.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final SourceMapper sourceMapper;

    @Override
    public SourceResponse createSource(SourceRequest request) {
        Source source = sourceMapper.toSourceEntity(request);
        Source savedSource = sourceRepository.save(source);
        return sourceMapper.toResponse(savedSource);
    }

    @Override
    public List<SourceResponse> getAll() {
        return sourceRepository.findAll().stream().map(sourceMapper::toResponse).toList();
    }
}
