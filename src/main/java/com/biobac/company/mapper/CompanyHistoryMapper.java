package com.biobac.company.mapper;

import com.biobac.company.entity.CompanyHistory;
import com.biobac.company.request.CompanyHistoryRequest;
import com.biobac.company.response.CompanyHistoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyHistoryMapper {

    CompanyHistory toCompanyHistoryEntity(CompanyHistoryRequest request);
    CompanyHistoryResponse toCompanyHistoryResponse(CompanyHistory entity);
}
