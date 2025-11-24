package com.biobac.company.mapper;

import com.biobac.company.entity.ContactPerson;
import com.biobac.company.request.ContactPersonRequest;
import com.biobac.company.response.ContactPersonResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactPersonMapper {

    ContactPerson toContactPersonEntity(ContactPersonRequest request);

    ContactPersonResponse toContactPersonResponse(ContactPerson contactPerson);

}
