package com.biobac.company.mapper;

import com.biobac.company.entity.PriceListItem;
import com.biobac.company.request.PriceListItemRequest;
import com.biobac.company.response.PriceListItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceListItemMapper {

    @Mapping(target = "priceListWrapper", ignore = true)
    PriceListItem toPriceListItemEntity(PriceListItemRequest request);

    PriceListItemResponse toPriceListItemResponse(PriceListItem priceListItem);

    @Mapping(target = "priceListWrapper", ignore = true)
    PriceListItem updatePriceListItem(PriceListItemRequest request, @MappingTarget PriceListItem priceListItems);
}
