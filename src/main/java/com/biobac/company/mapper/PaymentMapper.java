package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.response.PaymentCategoryResponse;
import org.mapstruct.*;

import java.util.IdentityHashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentCategoryResponse toCategoryResponse(PaymentCategory entity, @Context CycleAvoidingMappingContext context);

    class CycleAvoidingMappingContext {
        private Map<Object, Object> knownInstances = new IdentityHashMap<>();

        @BeforeMapping
        public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
            return (T) knownInstances.get(source);
        }

        @AfterMapping
        public void storeMappedInstance(Object source, @MappingTarget Object target) {
            knownInstances.put(source, target);
        }
    }
}
