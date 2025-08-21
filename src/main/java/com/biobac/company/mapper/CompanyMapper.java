package com.biobac.company.mapper;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.entity.Company;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company warehouse);
    Company toEntity(CompanyDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CompanyDto dto, @MappingTarget Company entity);
}
