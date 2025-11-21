package com.biobac.company.mapper;

import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    Detail toDetailEntity(DetailsRequest detailsRequest);

    DetailsResponse toDetailsResponse(Detail detail);

}
