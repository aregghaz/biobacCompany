package com.biobac.company.mapper;

import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.request.CompanyGroupRequest;
import com.biobac.company.response.CompanyGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyGroupMapper {

    CompanyGroup toEntity(CompanyGroupRequest request);

    CompanyGroupResponse toResponse(CompanyGroup entity);
}
