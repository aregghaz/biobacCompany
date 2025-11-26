package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.response.PaymentCategoryResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    default PaymentCategoryResponse toCategoryResponse(PaymentCategory entity, @Context CycleAvoidingMappingContext context) {
        if (entity == null) {
            return null;
        }
        PaymentCategoryResponse existing = context.getMappedInstance(entity, PaymentCategoryResponse.class);
        if (existing != null) {
            return existing;
        }
        PaymentCategoryResponse dto = new PaymentCategoryResponse();
        context.storeMappedInstance(entity, dto);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            List<PaymentCategoryResponse> children = new ArrayList<>(entity.getChildren().size());
            for (PaymentCategory child : entity.getChildren()) {
                children.add(toCategoryResponse(child, context));
            }
            dto.setChildren(children);
        } else {
            dto.setChildren(Collections.emptyList());
        }
        return dto;
    }

    class CycleAvoidingMappingContext {
        private Map<Object, Object> knownInstances = new IdentityHashMap<>();
        private Set<Object> visiting = Collections.newSetFromMap(new IdentityHashMap<>());

        @BeforeMapping
        public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
            return (T) knownInstances.get(source);
        }

        @AfterMapping
        public void storeMappedInstance(Object source, @MappingTarget Object target) {
            knownInstances.put(source, target);
        }

        public void startVisiting(Object source) {
            visiting.add(source);
        }

        public boolean isVisiting(Object source) {
            return visiting.contains(source);
        }

        public void finishVisiting(Object source) {
            visiting.remove(source);
        }
    }
}
