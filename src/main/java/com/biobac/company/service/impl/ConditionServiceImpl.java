package com.biobac.company.service.impl;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Condition;
import com.biobac.company.exception.ConditionNotFoundException;
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
        condition.setCompany(company);
        return conditionsRepository.save(condition);
    }

    @Override
    public Condition updatedCondition(Long id, ConditionsRequest request, Company company) {
        return conditionsRepository.findByCompanyId(id)
                .map(condition -> {
                    Condition updatedCondition = conditionMapper.updateConditionFromRequest(company.getCondition(), request);
                    updatedCondition.setCompany(company);
                    return conditionsRepository.save(updatedCondition);
                })
                .orElseThrow(() -> new ConditionNotFoundException("Condition with given " + id + " not found"));
    }

    @Override
    public Condition fetchConditionById(Long id) {
        return conditionsRepository.findByCompanyId(id)
                .orElseThrow(() -> new ConditionNotFoundException("Condition with given " + id + " not found"));
    }
}
