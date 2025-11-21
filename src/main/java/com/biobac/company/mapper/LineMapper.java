package com.biobac.company.mapper;

import com.biobac.company.entity.Line;
import com.biobac.company.request.LineRequest;
import com.biobac.company.response.LineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LineMapper {

    Line toLineEntity(LineRequest lineRequest);

    LineResponse toLineResponse(Line line);

}
