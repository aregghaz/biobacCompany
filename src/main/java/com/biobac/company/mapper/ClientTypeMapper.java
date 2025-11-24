package com.biobac.company.mapper;

import com.biobac.company.entity.ClientType;
import com.biobac.company.request.CreateClientTypeRequest;
import com.biobac.company.response.CreateClientTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientTypeMapper {

    ClientType toClientTypeEntity(CreateClientTypeRequest clientTypeRequest);

    CreateClientTypeResponse toClientTypeResponse(ClientType clientType);

}
