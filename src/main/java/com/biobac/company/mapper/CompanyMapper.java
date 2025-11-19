package com.biobac.company.mapper;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.entity.*;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.*;
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
    @Mapping(target = "conditions", ignore = true)
    @Mapping(target = "details", ignore = true)
    public abstract Company toEntity(CompanyRequest dto);

    @Mapping(target = "attributes", expression = "java(fetchAttributes(company.getId()))")
    @Mapping(target = "details", expression = "java(toDetailsResponse(company.getDetails()))")
    @Mapping(target = "conditions", expression = "java(toConditionsResponse(company.getConditions()))")
    @Mapping(target = "cooperation", source = "cooperation")
    @Mapping(target = "line", source = "line")
    @Mapping(target = "clientType", source = "customerType")
    @Mapping(target = "companyGroup", source = "companyGroup")
    public abstract CompanyResponse toResponse(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "conditions", ignore = true)
    public abstract void updateEntityFromDto(CompanyRequest dto, @MappingTarget Company entity);

    protected DetailsResponse toDetailsResponse(Details details) {
        if (details == null) return null;
        return new DetailsResponse(
                details.getInn(),
                details.getKpp(),
                details.getOgrn(),
                details.getOkpo(),
                details.getBankAccount(),
                details.getBik(),
                details.getKs(),
                details.getBankName()
        );
    }

    protected ConditionsResponse toConditionsResponse(Conditions conditions) {
        if (conditions == null) return null;

        return new ConditionsResponse(
                conditions.getDeliveryMethod() != null ? conditions.getDeliveryMethod().getId() : null,
                conditions.getDeliveryMethod() != null ? conditions.getDeliveryMethod().getName() : null,

                conditions.getDeliveryPayer() != null ? conditions.getDeliveryPayer().getId() : null,
                conditions.getDeliveryPayer() != null ? conditions.getDeliveryPayer().getName() : null,

                conditions.getFinancialTerms() != null ? conditions.getFinancialTerms().getId() : null,
                conditions.getFinancialTerms() != null ? conditions.getFinancialTerms().getName() : null,

                conditions.getContractForm() != null ? conditions.getContractForm().getId() : null,
                conditions.getContractForm() != null ? conditions.getContractForm().getName() : null,

                conditions.getBonus()
        );
    }

    protected EntityReferenceResponse mapCooperation(Cooperation entity) {
        if (entity == null) return null;
        EntityReferenceResponse dto = new EntityReferenceResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    protected EntityReferenceResponse mapLine(Line entity) {
        if (entity == null) return null;
        EntityReferenceResponse dto = new EntityReferenceResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    protected EntityReferenceResponse mapClientType(ClientType entity) {
        if (entity == null) return null;
        EntityReferenceResponse dto = new EntityReferenceResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    protected EntityReferenceResponse mapCompanyGroup(CompanyGroup entity) {
        if (entity == null) return null;
        EntityReferenceResponse dto = new EntityReferenceResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    protected abstract RegionResponse toResponse(Region region);

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
