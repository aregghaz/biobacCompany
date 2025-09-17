package com.biobac.company.mapper;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.entity.AttributeTargetType;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyType;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.AttributeResponse;
import com.biobac.company.response.CompanyResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    @Autowired
    protected AttributeClient attributeClient;

    @Mapping(target = "types", ignore = true)
    public abstract Company toEntity(CompanyRequest dto);

    @Mapping(target = "attributes", expression = "java(fetchAttributes(company.getId()))")
    public abstract CompanyResponse toResponse(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "types", ignore = true)
    public abstract void updateEntityFromDto(CompanyRequest dto, @MappingTarget Company entity);

    protected List<AttributeResponse> fetchAttributes(Long companyId) {
        if (companyId == null) return Collections.emptyList();
        try {
            ApiResponse<List<AttributeResponse>> apiResponse =
                    attributeClient.getValues(companyId, AttributeTargetType.COMPANY.name());
            return apiResponse.getData();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public List<CompanyType> mapTypeIds(List<Long> typeIds, CompanyTypeRepository repo) {
        if (typeIds == null) return null;
        return typeIds.stream()
                .map(id -> repo.findById(id)
                        .orElseThrow(() -> new NotFoundException("Company Type not found")))
                .collect(Collectors.toList());
    }
}

