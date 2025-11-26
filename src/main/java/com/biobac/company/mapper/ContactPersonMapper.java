package com.biobac.company.mapper;

import com.biobac.company.entity.ContactPerson;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ContactPersonMapper {

    public abstract ContactPerson toContactPersonEntity(ContactPersonRequest request);

    public abstract ContactPersonResponse toContactPersonResponse(ContactPerson contactPerson);
}