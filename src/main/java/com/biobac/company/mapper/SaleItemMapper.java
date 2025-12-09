package com.biobac.company.mapper;

import com.biobac.company.client.ProductClient;
import com.biobac.company.entity.SaleItem;
import com.biobac.company.response.ApiResponse;
import com.biobac.company.response.ProductResponse;
import com.biobac.company.response.SaleItemResponse;
import com.biobac.company.response.SimpleNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleItemMapper {

    private final ProductClient productClient;

    public SaleItemResponse toResponse(SaleItem saleItem) {
        if (saleItem == null) return null;

        SaleItemResponse response = new SaleItemResponse();
        response.setCreatedAt(saleItem.getCreatedAt());
        response.setUpdatedAt(saleItem.getUpdatedAt());
        response.setId(saleItem.getId());
        response.setQuantity(saleItem.getQuantity());
        response.setUnitPrice(saleItem.getUnitPrice());
        response.setTotalPrice(saleItem.getTotalPrice());

        Long productId = saleItem.getProductId();
        if (productId != null) {
            try {
                ApiResponse<ProductResponse> api = productClient.getProductById(productId);
                ProductResponse product = (api != null) ? api.getData() : null;
                if (product != null) {
                    response.setProduct(new SimpleNameResponse(product.getId(), product.getName()));
                } else {
                    response.setProduct(new SimpleNameResponse(productId, null));
                }
            } catch (Exception ex) {
                response.setProduct(new SimpleNameResponse(productId, null));
            }
        }

        return response;
    }
}
