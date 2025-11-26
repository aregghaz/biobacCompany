package com.biobac.company.mapper;

import com.biobac.company.entity.OurCompany;
import com.biobac.company.response.OurCompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OurCompanyMapper {
    OurCompanyResponse toResponse(OurCompany entity);
}
