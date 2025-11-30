package com.biobac.company.mapper;

import com.biobac.company.entity.EmployeeHistory;
import com.biobac.company.response.EmployeeHistoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeHistoryMapper {
    EmployeeHistoryResponse toResponse(EmployeeHistory entity);
}
