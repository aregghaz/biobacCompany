package com.biobac.company.mapper;

import com.biobac.company.entity.Sale;
import com.biobac.company.response.SaleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OurCompanyMapper.class, CompanyMapper.class, SaleItemMapper.class, ContactPersonMapper.class})
public interface SaleMapper {
    @Mapping(source = "buyerCompany", target = "company")
    SaleResponse toResponse(Sale entity);
}
