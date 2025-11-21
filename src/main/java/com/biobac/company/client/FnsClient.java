package com.biobac.company.client;

import com.biobac.company.response.RawFnsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fns-client", url = "${services.fns-url}")
public interface FnsClient {
    @GetMapping("/egr")
    RawFnsResponse getCompany(
            @RequestParam("req") String innOrOgrn,
            @RequestParam("key") String apiKey
    );
}
