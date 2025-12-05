package com.biobac.company.service.impl;

import com.biobac.company.entity.PriceListItem;
import com.biobac.company.entity.PriceListWrapper;
import com.biobac.company.mapper.PriceListItemMapper;
import com.biobac.company.repository.PriceListItemRepository;
import com.biobac.company.request.PriceListItemRequest;
import com.biobac.company.service.PriceListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceListItemServiceImpl implements PriceListItemService {

    private final PriceListItemRepository priceListItemRepository;
    private final PriceListItemMapper priceListItemMapper;

    @Override
    public PriceListItem createPriceListItem(PriceListItemRequest request, PriceListWrapper wrapper) {
        PriceListItem priceListItem = priceListItemMapper.toPriceListItemEntity(request);
        priceListItem.setPriceListWrapper(wrapper);
        return priceListItemRepository.save(priceListItem);
    }

    @Override
    public PriceListItem updatePriceListItem(Long id, PriceListItemRequest request, PriceListWrapper wrapper) {
        return priceListItemRepository.findById(id)
                .map(item -> {
                    PriceListItem priceListItem = priceListItemMapper.updatePriceListItem(request, item);
                    priceListItem.setPriceListWrapper(wrapper);
                    item.setPriceListWrapper(wrapper);
                    return priceListItemRepository.save(priceListItem);
                })
                .orElseThrow(() -> new RuntimeException("Price list item not found"));
    }
}
