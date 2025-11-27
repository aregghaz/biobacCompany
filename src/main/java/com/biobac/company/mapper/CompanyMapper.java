package com.biobac.company.mapper;

import com.biobac.company.entity.ClientType;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.entity.CompanyType;
import com.biobac.company.entity.ContactPerson;
import com.biobac.company.entity.Cooperation;
import com.biobac.company.entity.Line;
import com.biobac.company.entity.Region;
import com.biobac.company.entity.SaleType;
import com.biobac.company.entity.Source;
import com.biobac.company.repository.ClientTypeRepository;
import com.biobac.company.repository.CompanyGroupRepository;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.repository.ContactPersonRepository;
import com.biobac.company.repository.CooperationRepository;
import com.biobac.company.repository.LineRepository;
import com.biobac.company.repository.RegionRepository;
import com.biobac.company.repository.SaleTypeRepository;
import com.biobac.company.repository.SourceRepository;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {ConditionMapper.class, DetailMapper.class})
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
    protected ContactPersonRepository contactPersonRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Mapping(source = "localAddress", target = "address.localAddress")
    @Mapping(source = "actualAddress", target = "address.actualAddress")
    @Mapping(source = "warehouseAddress", target = "address.warehouseAddress")
    @Mapping(target = "companyGroup", expression = "java(getCompanyGroup(request.getCompanyGroupId()))")
    @Mapping(target = "saleType", expression = "java(getSaleType(request.getSaleTypeId()))")
    @Mapping(target = "region", expression = "java(getRegion(request.getRegionId()))")
    @Mapping(target = "types", expression = "java(getCompanyType(request.getTypeIds()))")
    @Mapping(target = "customerType", expression = "java(getClientType(request.getCustomerTypeId()))")
    @Mapping(target = "lines", expression = "java(getLines(request.getLineIds()))")
    @Mapping(target = "cooperation", expression = "java(getCooperation(request.getCooperationId()))")
    @Mapping(target = "contactPerson", expression = "java(getContactPerson(request.getContactPersonIds()))")
    @Mapping(target = "source", expression = "java(getSource(request.getSourceId()))")
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "condition", ignore = true)
    public abstract Company toCompanyEntity(CompanyRequest request);

    @Mapping(source = "address.localAddress", target = "localAddress")
    @Mapping(source = "address.actualAddress", target = "actualAddress")
    @Mapping(source = "address.warehouseAddress", target = "warehouseAddress")
    public abstract CompanyResponse toCompanyResponse(Company company);

    @Mapping(source = "request.localAddress", target = "address.localAddress")
    @Mapping(source = "request.actualAddress", target = "address.actualAddress")
    @Mapping(source = "request.warehouseAddress", target = "address.warehouseAddress")
    @Mapping(target = "companyGroup", expression = "java(getCompanyGroup(request.getCompanyGroupId()))")
    @Mapping(target = "saleType", expression = "java(getSaleType(request.getSaleTypeId()))")
    @Mapping(target = "region", expression = "java(getRegion(request.getRegionId()))")
    @Mapping(target = "types", expression = "java(getCompanyType(request.getTypeIds()))")
    @Mapping(target = "customerType", expression = "java(getClientType(request.getCustomerTypeId()))")
    @Mapping(target = "lines", expression = "java(getLines(request.getLineIds()))")
    @Mapping(target = "cooperation", expression = "java(getCooperation(request.getCooperationId()))")
    @Mapping(target = "contactPerson", expression = "java(getContactPerson(request.getContactPersonIds()))")
    @Mapping(target = "source", expression = "java(getSource(request.getSourceId()))")
    public abstract Company toUpdateCompany(CompanyRequest request, Long id);

    protected Source getSource(Long id) {
        if (id == null) return null;
        return sourceRepository.findById(id)
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

    protected Cooperation getCooperation(Long id) {
        if (id == null) return null;
        return cooperationRepository.findById(id)
                .orElse(null);
    }

    protected List<ContactPerson> getContactPerson(List<Long> id) {
        if (id == null) return Collections.emptyList();
        return contactPersonRepository.findAllById(id);
    }
}