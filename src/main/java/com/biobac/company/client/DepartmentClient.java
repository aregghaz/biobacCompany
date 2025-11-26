package com.biobac.company.client;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service", url = "${services.warehouse-url}")
public interface DepartmentClient {
    @GetMapping("/department/{id}")
    ApiResponse<DepartmentResponse> getDepartmentById(@PathVariable Long id);
}
