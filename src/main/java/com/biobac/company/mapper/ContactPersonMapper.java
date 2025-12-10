package com.biobac.company.mapper;

import com.biobac.company.entity.ContactPerson;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ContactPersonMapper {

    @Mapping(target = "company", ignore = true)
    public abstract ContactPerson toContactPersonEntity(ContactPersonRequest request);

    @Mapping(target = "companyId",  ignore = true)
    public abstract ContactPersonResponse toContactPersonResponse(ContactPerson contactPerson);

    public abstract ContactPerson toUpdatedContactPersonResponse(
            @MappingTarget ContactPerson contactPerson,
            ContactPersonRequest contactPersonRequest
    );

}