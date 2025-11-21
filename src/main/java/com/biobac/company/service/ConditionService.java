package com.biobac.company.service;

import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.response.ConditionsResponse;

public interface ConditionService {
    ConditionsResponse createCondition(ConditionsRequest request);
}
