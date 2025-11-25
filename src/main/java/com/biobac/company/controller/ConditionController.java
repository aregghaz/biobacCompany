package com.biobac.company.controller;

import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ConditionsResponse;
import com.biobac.company.service.ConditionService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/condition")
public class ConditionController {

    private final ConditionService conditionService;

    @PostMapping("/create")
    public ApiResponse<ConditionsResponse> createCondition(@RequestBody ConditionsRequest request) {
        ConditionsResponse response = conditionService.createCondition(request);
        return ResponseUtil.success("Condition created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<ConditionsResponse> getConditionById(@PathVariable Long id) {
        ConditionsResponse response = conditionService.getConditionById(id);
        return ResponseUtil.success("Condition retrieved successfully", response);
    }

}
