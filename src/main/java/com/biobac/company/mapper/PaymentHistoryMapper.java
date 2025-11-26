package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentHistory;
import com.biobac.company.response.PaymentHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    @Mapping(source = "paymentCategory.parent.id", target = "paymentCategory.parentId")
    PaymentHistoryResponse toResponse(PaymentHistory entity);
}
