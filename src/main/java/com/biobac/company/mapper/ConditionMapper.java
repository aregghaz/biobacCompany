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

    @Mapping(target = "deliveryMethod", expression = "java(getDeliveryMethod(request.getDeliveryMethodId()))")
    @Mapping(target = "deliveryPayer", expression = "java(getDeliveryPayer(request.getDeliveryPayerId()))")
    @Mapping(target = "financialTerms", expression = "java(getFinancialTerms(request.getFinancialTermsId()))")
    @Mapping(target = "contractForm", expression = "java(getContractForm(request.getContractFormId()))")
    public abstract Condition toConditionEntity(ConditionsRequest request);

    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryPayer.id", target = "deliveryPayerId")
    @Mapping(source = "financialTerms.id", target = "financialTermsId")
    @Mapping(source = "contractForm.id", target = "contractFormId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "deliveryPayer.name", target = "deliveryPayerName")
    @Mapping(source = "financialTerms.name", target = "financialTermsName")
    @Mapping(source = "contractForm.name", target = "contractFormName")
    public abstract ConditionsResponse toConditionResponse(Condition condition);

    protected DeliveryMethod getDeliveryMethod(Long id) {
        return deliveryMethodRepository.findById(id)
                .orElse(null);
    }

    protected DeliveryPayer getDeliveryPayer(Long id) {
        return deliveryPayerRepository.findById(id)
                .orElse(null);
    }

    protected FinancialTerms getFinancialTerms(Long id) {
        return financialTermsRepository.findById(id)
                .orElse(null);
    }

    protected ContractForm getContractForm(Long id) {
        return contractFormRepository.findById(id)
                .orElse(null);
    }
}
