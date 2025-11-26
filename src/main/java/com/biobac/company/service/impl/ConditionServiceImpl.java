package com.biobac.company.service.impl;

import com.biobac.company.entity.Condition;
import com.biobac.company.mapper.ConditionMapper;
import com.biobac.company.repository.ConditionsRepository;
import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.response.ConditionsResponse;
import com.biobac.company.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionsRepository conditionsRepository;
    private final ConditionMapper conditionMapper;

    @Override
    public ConditionsResponse createCondition(ConditionsRequest request) {
        Condition condition = conditionMapper.toConditionEntity(request);
        Condition saved = conditionsRepository.save(condition);
        return conditionMapper.toConditionResponse(saved);
    }

    @Override
    public ConditionsResponse getConditionById(Long id) {
        return conditionsRepository.findById(id)
                .map(conditionMapper::toConditionResponse)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
    }
}
