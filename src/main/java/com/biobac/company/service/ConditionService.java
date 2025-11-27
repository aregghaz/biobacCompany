package com.biobac.company.service;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.Condition;
import com.biobac.company.request.ConditionsRequest;

public interface ConditionService {
    Condition createCondition(ConditionsRequest request, Company company);
}
