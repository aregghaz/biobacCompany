package com.biobac.company.service.impl;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Condition;
import com.biobac.company.mapper.ConditionMapper;
import com.biobac.company.repository.ConditionsRepository;
import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionsRepository conditionsRepository;
    private final ConditionMapper conditionMapper;

    @Override
    @Transactional
    public Condition createCondition(ConditionsRequest request, Company company) {
        Condition condition = conditionMapper.toConditionEntity(request);
        Condition saved = conditionsRepository.save(condition);
        company.setCondition(saved);
        return condition;
    }
}
