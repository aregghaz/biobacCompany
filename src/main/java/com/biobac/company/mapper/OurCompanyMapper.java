package com.biobac.company.mapper;

import com.biobac.company.entity.OurCompany;
import com.biobac.company.request.OurCompanyRequest;
import com.biobac.company.response.OurCompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface OurCompanyMapper {

    @Mapping(target = "accounts", ignore = true)
    OurCompany toOurCompanyEntity(OurCompanyRequest request);

    OurCompanyResponse toResponse(OurCompany entity);
}
