package com.biobac.company.mapper;

import com.biobac.company.entity.Cooperation;
import com.biobac.company.request.CooperationRequest;
import com.biobac.company.response.CooperationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CooperationMapper {

    Cooperation toCooperationEntity(CooperationRequest cooperationRequest);

    CooperationResponse toCooperationResponse(Cooperation cooperation);

}
