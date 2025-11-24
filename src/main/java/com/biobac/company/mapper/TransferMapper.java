package com.biobac.company.mapper;

import com.biobac.company.entity.Transfer;
import com.biobac.company.request.TransferRequest;
import com.biobac.company.response.TransferResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferResponse toResponse(Transfer entity);

    Transfer toEntity(TransferRequest request);
}
