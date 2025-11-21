package com.biobac.company.service.impl;

import com.biobac.company.client.FnsClient;
import com.biobac.company.response.FnsCompany;
import com.biobac.company.response.FnsCompanyResponse;
import com.biobac.company.response.RawFnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FnsService {

    private final FnsClient fnsClient;

    @Value("${fns.api.key}")
    private String apiKey;

    public FnsCompanyResponse getExtractedData(String req) {
        RawFnsResponse response = fnsClient.getCompany(req, apiKey);

        FnsCompany data = response.getItems().get(0).getUl();

        return map(data);
    }

    private FnsCompanyResponse map(FnsCompany data) {
        FnsCompanyResponse info = new FnsCompanyResponse();

        info.setName(
                data.getFullName() != null
                        ? data.getFullName()
                        : data.getShortName()
        );

        info.setInn(data.getInn());
        info.setOgrn(data.getOgrn());

        return info;
    }

    private String getFirst(List<String> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
