package com.biobac.company.mapper;

import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.response.CompanyGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyGroupMapper {
    CompanyGroupResponse toResponse(CompanyGroup entity);
}
