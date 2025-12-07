package com.biobac.company.service;

import com.biobac.company.entity.PriceListItem;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.request.PriceListItemRequest;

public interface PriceListItemService {

    PriceListItem createPriceListItem(PriceListItemRequest request, PriceListWrapper wrapper);

    PriceListItem updatePriceListItem(Long id, PriceListItemRequest request, PriceListWrapper wrapper);

}
