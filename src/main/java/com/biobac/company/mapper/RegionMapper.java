package com.biobac.company.mapper;

import com.biobac.company.entity.Region;
import com.biobac.company.request.CreateRegionRequest;
import com.biobac.company.response.CreateRegionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(source = "regionName", target = "name")
    Region toRegionEntity(CreateRegionRequest request);

    @Mapping(source = "name", target = "regionName")
    CreateRegionResponse toRegionResponse(Region region);

}
