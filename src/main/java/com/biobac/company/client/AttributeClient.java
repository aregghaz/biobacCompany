package com.biobac.company.client;

import com.biobac.company.request.AttributeUpsertRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.AttributeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "attribute-service", url = "${services.attribute-url}")
public interface AttributeClient {
    @PostMapping("/attribute-value")
    ApiResponse<List<AttributeResponse>> createValues(
            @RequestParam("targetId") Long targetId,
            @RequestParam("targetType") String targetType,
            @RequestBody List<AttributeUpsertRequest> attributes
    );

    @PutMapping("/attribute-value")
    ApiResponse<List<AttributeResponse>> updateValues(
            @RequestParam("targetId") Long targetId,
            @RequestParam("targetType") String targetType,
            @RequestParam(value = "attributeGroupIds", required = false)  List<Long> attributeGroupIds,
            @RequestBody List<AttributeUpsertRequest> attributes
    );

    @GetMapping("/attribute-value/{targetId}/{targetType}")
    ApiResponse<List<AttributeResponse>> getValues(
            @PathVariable("targetId") Long targetId,
            @PathVariable("targetType") String targetType
    );

    @DeleteMapping("/attribute-value/{targetId}/{targetType}")
    ApiResponse<List<String>> deleteValues(
            @PathVariable("targetId") Long targetId,
            @PathVariable("targetType") String targetType
    );
}
