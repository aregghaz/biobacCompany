package com.biobac.company.mapper;

import com.biobac.company.entity.CompanyType;
import com.biobac.company.request.CreateCompanyTypeRequest;
import com.biobac.company.response.CreateCompanyTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {

    CompanyType toCompanyTypeEntity(CreateCompanyTypeRequest request);

    CreateCompanyTypeResponse toCompanyTypeResponse(CompanyType companyType);

}
