package com.biobac.company.mapper;

import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailRequest;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    @Mapping(target = "company", ignore = true)
    Detail toDetailEntity(DetailRequest detailsRequest);

    DetailsResponse toDetailsResponse(Detail detail);
}