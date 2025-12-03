package com.biobac.company.mapper;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.entity.OurCompany;
import com.biobac.company.entity.enums.AttributeTargetType;
import com.biobac.company.request.OurCompanyRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.AttributeResponse;
import com.biobac.company.response.OurCompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public abstract class OurCompanyMapper {

    @Autowired
    private AttributeClient attributeClient;

    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(source = "localAddress", target = "address.localAddress")
    @Mapping(source = "actualAddress", target = "address.actualAddress")
    @Mapping(source = "warehouseAddress", target = "address.warehouseAddress")
    public abstract OurCompany toOurCompanyEntity(OurCompanyRequest request);

    @Mapping(source = "address.localAddress", target = "localAddress")
    @Mapping(source = "address.actualAddress", target = "actualAddress")
    @Mapping(source = "address.warehouseAddress", target = "warehouseAddress")
    @Mapping(target = "attributes", expression = "java(fetchAttributes(entity.getId()))")
    public abstract OurCompanyResponse toResponse(OurCompany entity);

    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(source = "localAddress", target = "address.localAddress")
    @Mapping(source = "actualAddress", target = "address.actualAddress")
    @Mapping(source = "warehouseAddress", target = "address.warehouseAddress")
    public abstract void updateOurCompanyFromRequest(OurCompanyRequest request, @MappingTarget OurCompany entity);

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
}
