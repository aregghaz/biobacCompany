package com.biobac.company.controller;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.DetailsResponse;
import com.biobac.company.service.DetailService;
import com.biobac.company.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/details")
public class DetailsController {

    private final DetailService detailService;

    @GetMapping("/{id}")
    public ApiResponse<DetailsResponse> getDetailsById(@PathVariable Long id) {
        DetailsResponse detail = detailService.getDetailById(id);
        return ResponseUtil.success("success", detail);
    }

}
