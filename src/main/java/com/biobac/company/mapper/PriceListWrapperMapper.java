package com.biobac.company.mapper;

import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.request.PriceListWrapperRequest;
import com.biobac.company.response.PriceListWrapperResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PriceListWrapperMapper {

    @Mapping(target = "priceListItems", ignore = true)
    PriceListWrapper toPriceListWrapper(PriceListWrapperRequest request);

//    @Mapping(target = "priceListItems", ignore = true)
    PriceListWrapperResponse toPriceListWrapperResponse(PriceListWrapper priceListWrapper);

    void updatePriceListWrapper(PriceListWrapperRequest request, @MappingTarget PriceListWrapper wrapper);
}
