package com.biobac.company.mapper;

import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    @Mapping(target = "company", ignore = true)
    Detail toDetailEntity(DetailsRequest detailsRequest);

    DetailsResponse toDetailsResponse(Detail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    Detail upadateDetail(DetailsRequest detailsRequest, @MappingTarget Detail detail);
}