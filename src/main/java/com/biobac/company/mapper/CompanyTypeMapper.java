package com.biobac.company.mapper;

import com.biobac.company.entity.CompanyType;
import com.biobac.company.response.CompanyTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {
    CompanyTypeResponse toResponse(CompanyType companyType);
}
