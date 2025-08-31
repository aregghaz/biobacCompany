package com.biobac.company.mapper;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.entity.Company;
import com.biobac.company.response.CompanyResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company warehouse);

    Company toEntity(CompanyDto dto);

    CompanyResponse toResponse(Company company);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CompanyDto dto, @MappingTarget Company entity);

}
