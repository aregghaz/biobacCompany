package com.biobac.company.mapper;

import com.biobac.company.dto.CompanyDto;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyType;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.CompanyResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company company);

    @Mapping(target = "types", ignore = true)
    Company toEntity(CompanyRequest dto);

    CompanyResponse toResponse(Company company);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "types", ignore = true)
    void updateEntityFromDto(CompanyRequest dto, @MappingTarget Company entity);

    default List<CompanyType> mapTypeIds(List<Long> typeIds, CompanyTypeRepository repo) {
        if (typeIds == null) return null;

        return typeIds.stream()
                .map(id -> repo.findById(id)
                        .orElseThrow(() -> new NotFoundException("Company Type not found")))
                .collect(Collectors.toList());
    }

}
