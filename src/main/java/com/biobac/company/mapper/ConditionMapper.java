package com.biobac.company.mapper;

import com.biobac.company.entity.Condition;
import com.biobac.company.entity.ContractForm;
import com.biobac.company.entity.DeliveryMethod;
import com.biobac.company.entity.DeliveryPayer;
import com.biobac.company.entity.FinancialTerms;
import com.biobac.company.repository.ContractFormRepository;
import com.biobac.company.repository.DeliveryMethodRepository;
import com.biobac.company.repository.DeliveryPayerRepository;
import com.biobac.company.repository.FinancialTermsRepository;
import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.response.ConditionsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ConditionMapper {

    @Autowired
    protected DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    protected DeliveryPayerRepository deliveryPayerRepository;

    @Autowired
    protected FinancialTermsRepository financialTermsRepository;

    @Autowired
    protected ContractFormRepository contractFormRepository;

    @Mapping(target = "deliveryMethods", expression = "java(getDeliveryMethods(request.getDeliveryMethodIds()))")
    @Mapping(target = "deliveryPayer", expression = "java(getDeliveryPayer(request.getDeliveryPayerId()))")
    @Mapping(target = "financialTerms", expression = "java(getFinancialTerms(request.getFinancialTermIds()))")
    @Mapping(target = "contractForm", expression = "java(getContractForm(request.getContractFormId()))")
    public abstract Condition toConditionEntity(ConditionsRequest request);

    @Mapping(source = "deliveryPayer.id", target = "deliveryPayerId")
    @Mapping(source = "contractForm.id", target = "contractFormId")
    @Mapping(source = "deliveryPayer.name", target = "deliveryPayerName")
    @Mapping(source = "contractForm.name", target = "contractFormName")
    @Mapping(source = "financialTerms", target = "financialTerms")
    public abstract ConditionsResponse toConditionResponse(Condition condition);

    protected List<DeliveryMethod> getDeliveryMethods(List<Long> id) {
        return deliveryMethodRepository.findAllById(id);
    }

    protected DeliveryPayer getDeliveryPayer(Long id) {
        return deliveryPayerRepository.findById(id)
                .orElse(null);
    }

    protected List<FinancialTerms> getFinancialTerms(List<Long> id) {
        return financialTermsRepository.findAllById(id);
    }

    protected ContractForm getContractForm(Long id) {
        return contractFormRepository.findById(id)
                .orElse(null);
    }
}
