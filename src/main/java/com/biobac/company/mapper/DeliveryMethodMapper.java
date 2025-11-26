package com.biobac.company.mapper;

import com.biobac.company.entity.DeliveryMethod;
import com.biobac.company.request.DeliveryMethodRequest;
import com.biobac.company.response.DeliveryMethodResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMethodMapper {

    DeliveryMethod toDeliveryMethodEntity(DeliveryMethodRequest deliveryMethod);

    DeliveryMethodResponse toDeliveryResponse(DeliveryMethod deliveryMethod);

}
