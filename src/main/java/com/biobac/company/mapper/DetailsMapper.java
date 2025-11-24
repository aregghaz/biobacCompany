package com.biobac.company.mapper;

import com.biobac.company.entity.Detail;
import com.biobac.company.request.DetailsRequest;
import com.biobac.company.response.DetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    Detail toDetailEntity(DetailsRequest detailsRequest);

    DetailsResponse toDetailsResponse(Detail detail);
}