package com.biobac.company.service;

import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.request.DeliveryMethodRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.DeliveryMethodResponse;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface DeliveryMethodService {

    List<DeliveryMethodResponse> getAllDeliveryMethods();

    Pair<List<DeliveryMethodResponse>, PaginationMetadata> getPagination(
            Map<String, FilterCriteria> filters,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir
    );

    DeliveryMethodResponse getDeliveryMethodById(Long id);

    DeliveryMethodResponse createDeliveryMethod(DeliveryMethodRequest request);

    DeliveryMethodResponse update(Long id, DeliveryMethodRequest request);

    void delete(Long id);
}
