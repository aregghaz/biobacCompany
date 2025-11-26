package com.biobac.company.mapper;

import com.biobac.company.entity.Source;
import com.biobac.company.request.SourceRequest;
import com.biobac.company.response.SourceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SourceMapper {

    public abstract Source toSourceEntity(SourceRequest request);

    public abstract SourceResponse toResponse(Source source);

}
