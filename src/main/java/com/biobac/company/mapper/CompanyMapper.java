package com.biobac.company.mapper;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.entity.*;
import com.biobac.company.entity.enums.AttributeTargetType;
import com.biobac.company.repository.*;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.AttributeResponse;
import com.biobac.company.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {
        ConditionMapper.class,
        DetailsMapper.class,
        BranchMapper.class,
        PriceListWrapperMapper.class
})
public abstract class CompanyMapper {

    @Autowired
    protected CompanyGroupRepository companyGroupRepository;

    @Autowired
    protected SaleTypeRepository saleTypeRepository;

    @Autowired
    protected RegionRepository regionRepository;

    @Autowired
    protected CompanyTypeRepository companyTypeRepository;

    @Autowired
    protected ClientTypeRepository clientTypeRepository;

    @Autowired
    protected LineRepository lineRepository;

    @Autowired
    protected CooperationRepository cooperationRepository;

    @Autowired
    protected SourceRepository sourceRepository;

    @Autowired
    protected AttributeClient attributeClient;

    @Mapping(source = "localAddress", target = "address.localAddress")
    @Mapping(source = "actualAddress", target = "address.actualAddress")
    @Mapping(source = "warehouseAddress", target = "address.warehouseAddress")
    @Mapping(source = "longitude", target = "location.longitude")
    @Mapping(source = "latitude", target = "location.latitude")
    @Mapping(target = "companyGroup", expression = "java(getCompanyGroup(request.getCompanyGroupId()))")
    @Mapping(target = "saleType", expression = "java(getSaleType(request.getSaleTypeId()))")
    @Mapping(target = "region", expression = "java(getRegion(request.getRegionId()))")
    @Mapping(target = "types", expression = "java(getCompanyType(request.getTypeIds()))")
    @Mapping(target = "clientType", expression = "java(getClientType(request.getClientTypeId()))")
    @Mapping(target = "lines", expression = "java(getLines(request.getLineIds()))")
    @Mapping(target = "cooperation", expression = "java(getCooperation(request.getCooperationId()))")
    @Mapping(target = "source", expression = "java(getSource(request.getSourceId()))")
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "contactPerson", ignore = true)
    @Mapping(target = "branches", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    public abstract Company toCompanyEntity(CompanyRequest request);

    @Mapping(source = "address.localAddress", target = "localAddress")
    @Mapping(source = "address.actualAddress", target = "actualAddress")
    @Mapping(source = "address.warehouseAddress", target = "warehouseAddress")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "location.latitude", target = "latitude")
    @Mapping(target = "attributes", expression = "java(fetchAttributes(company.getId()))")
    @Mapping(target = "priceList", ignore = true)
    public abstract CompanyResponse toCompanyResponse(Company company);

    @Mapping(source = "request.localAddress", target = "address.localAddress")
    @Mapping(source = "request.actualAddress", target = "address.actualAddress")
    @Mapping(source = "request.warehouseAddress", target = "address.warehouseAddress")
    @Mapping(source = "request.longitude", target = "location.longitude")
    @Mapping(source = "request.latitude", target = "location.latitude")
    @Mapping(target = "companyGroup", expression = "java(getCompanyGroup(request.getCompanyGroupId()))")
    @Mapping(target = "saleType", expression = "java(getSaleType(request.getSaleTypeId()))")
    @Mapping(target = "region", expression = "java(getRegion(request.getRegionId()))")
    @Mapping(target = "types", expression = "java(getCompanyType(request.getTypeIds()))")
    @Mapping(target = "clientType", expression = "java(getClientType(request.getClientTypeId()))")
    @Mapping(target = "lines", expression = "java(getLines(request.getLineIds()))")
    @Mapping(target = "cooperation", expression = "java(getCooperation(request.getCooperationId()))")
    @Mapping(target = "source", expression = "java(getSource(request.getSourceId()))")
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "contactPerson", ignore = true)
    @Mapping(target = "branches", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    public abstract Company toUpdateCompany(CompanyRequest request, @MappingTarget Company company);

    protected Source getSource(Long id) {
        if (id == null) return null;
        return sourceRepository.findById(id)
                .orElse(null);
    }

    protected Cooperation getCooperation(Long id) {
        if (id == null) return null;
        return cooperationRepository.findById(id)
                .orElse(null);
    }

    protected CompanyGroup getCompanyGroup(Long id) {
        if (id == null) return null;
        return companyGroupRepository.findById(id)
                .orElse(null);
    }

    protected SaleType getSaleType(Long id) {
        if (id == null) return null;
        return saleTypeRepository.findById(id)
                .orElse(null);
    }

    protected Region getRegion(Long id) {
        if (id == null) return null;
        return regionRepository.findById(id)
                .orElse(null);
    }

    protected List<CompanyType> getCompanyType(List<Long> id) {
        if (id == null) return Collections.emptyList();
        return companyTypeRepository.findAllById(id);
    }

    protected ClientType getClientType(Long id) {
        if (id == null) return null;
        return clientTypeRepository.findById(id)
                .orElse(null);
    }

    protected List<Line> getLines(List<Long> id) {
        if (id == null) return Collections.emptyList();
        return lineRepository.findAllById(id);
    }

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