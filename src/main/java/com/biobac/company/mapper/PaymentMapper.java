package com.biobac.company.mapper;

import com.biobac.company.entity.Payment;
import com.biobac.company.response.PaymentResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PaymentCategoryMapper.class, AccountMapper.class})
public interface PaymentMapper {
    default PaymentResponse toResponse(Payment entity) {
        return toResponse(entity, new PaymentCategoryMapper.CycleAvoidingMappingContext());
    }

    PaymentResponse toResponse(Payment entity, @Context PaymentCategoryMapper.CycleAvoidingMappingContext context);
}
