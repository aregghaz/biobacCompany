package com.biobac.company.utils;

import com.biobac.company.client.ProductClient;
import com.biobac.company.entity.PriceListItem;
import com.biobac.company.response.ProductResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ProductClientUtil {

    private ProductClientUtil() {
    }

    public static List<ProductResponse> enrichProductsWithPrices(List<PriceListItem> items, ProductClient productClient) {
        if (items == null || items.isEmpty()) return Collections.emptyList();

        return items.stream()
                .filter(li -> li.getProductId() != null)
                .map(li -> {
                    ProductResponse product = productClient.getProductById(li.getProductId()).getData();
                    if (product != null && li.getPrice() != null) {
                        product.setPrice(li.getPrice());
                    }
                    return product;
                })
                .filter(Objects::nonNull)
                .toList();
    }
}