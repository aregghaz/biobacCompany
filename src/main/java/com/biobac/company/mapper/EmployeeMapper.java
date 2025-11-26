package com.biobac.company.mapper;

import com.biobac.company.client.DepartmentClient;
import com.biobac.company.entity.Employee;
import com.biobac.company.request.EmployeeRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.DepartmentResponse;
import com.biobac.company.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

    @Autowired
    protected DepartmentClient departmentClient;

    @Mapping(target = "department", expression = "java(loadDepartment(entity.getDepartmentId()))")
    public abstract EmployeeResponse toResponse(Employee entity);

    @Mapping(target = "ourCompany", ignore = true)
    public abstract Employee toEntity(EmployeeRequest request);

    @Mapping(target = "ourCompany", ignore = true)
    public abstract void updateEmployeeFromRequest(@MappingTarget Employee employee, EmployeeRequest request);

    protected DepartmentResponse loadDepartment(Long id) {
        try {
            ApiResponse<DepartmentResponse> departmentResponseApiResponse = departmentClient.getDepartmentById(id);
            return departmentResponseApiResponse.getData();
        } catch (Exception ignore) {
            return null;
        }
    }
}
