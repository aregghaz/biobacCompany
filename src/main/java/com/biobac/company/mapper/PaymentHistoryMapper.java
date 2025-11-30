package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentHistory;
import com.biobac.company.response.PaymentHistoryResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PaymentCategoryMapper.class})
public interface PaymentHistoryMapper {
    default PaymentHistoryResponse toResponse(PaymentHistory entity) {
        return toResponse(entity, new PaymentCategoryMapper.CycleAvoidingMappingContext());
    }

    PaymentHistoryResponse toResponse(PaymentHistory entity, @Context PaymentCategoryMapper.CycleAvoidingMappingContext context);
}
