package com.biobac.company.client;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${services.warehouse-url}")
public interface ProductClient {

    @GetMapping("/product/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long id);
}
