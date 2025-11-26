package com.biobac.company.service.impl;

import com.biobac.company.client.FnsClient;
import com.biobac.company.response.FnsCompany;
import com.biobac.company.response.FnsCompanyResponse;
import com.biobac.company.response.RawFnsResponse;
import com.biobac.company.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

        info.setInn(data.getInn());
        info.setOgrn(data.getOgrn());
        info.setKpp(data.getKpp());
        info.setFullName(data.getFullName());
        info.setShortName(data.getShortName());
        info.setOkpo(data.getOkpo());
        info.setWebsites(data.getContacts().getWebsites());
        info.setPhones(data.getContacts().getPhones());
        info.setEmails(data.getContacts().getEmails());
        info.setSeo(data.getSeo());
        info.setDateOfRegistration(DateUtil.toLocalDateTime(data.getDateOfRegistration()));
        info.setDateOfOgrn(DateUtil.toLocalDateTime(data.getDateOfOgrn()));

        return info;
    }
}
