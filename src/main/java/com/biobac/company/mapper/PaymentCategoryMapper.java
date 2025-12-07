package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.EmployeeResponse;
import com.biobac.company.response.PaymentCategoryResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class PaymentCategoryMapper {

    @Autowired
    protected PaymentCategoryEnricher enricher;

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    public abstract PaymentCategory toEntity(PaymentCategoryRequest response);

    public PaymentCategoryResponse toCategoryResponse(PaymentCategory entity, @Context CycleAvoidingContext context) {
        if (entity == null) return null;

        PaymentCategoryResponse existing = context.get(entity);
//        PaymentCategoryResponse existing = context.getMappedInstance(entity, PaymentCategoryResponse.class);
        if (existing != null) return existing;

        PaymentCategoryResponse dto = PaymentCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .parent(toCategoryResponse(entity.getParent(), context))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .category(entity.getCategory())
                .children(new ArrayList<>())
                .build();

        if (entity.getParent() != null) {
            dto.setParent(toParentResponseWithChildren(entity.getParent()));
        }

        for(PaymentCategory child : entity.getChildren()) {
            dto.getChildren().add(toCategoryResponse(child, context));
        }

        if (entity.getCategory() != null) {
            switch (entity.getCategory()) {
                case EMPLOYEE -> {
                    List<EmployeeResponse> employees = safeGetEmployees();
                    for (EmployeeResponse e : employees) {
                        PaymentCategoryResponse child = new PaymentCategoryResponse();
                        child.setId(e.getId());
                        child.setParentId(dto.getId());
                        child.setParent(toShallowParentFromResponse(dto));
                        String name = (e.getLastname() != null ? e.getLastname() : "")
                                + (e.getFirstname() != null ? (" " + e.getFirstname()) : "");
                        child.setName(name.trim());
                        child.setChildren(Collections.emptyList());
                        dto.getChildren().add(child);
                    }
                }
                case SELLER -> {
                    List<CompanyResponse> sellers = safeGetSellers();
                    for (CompanyResponse c : sellers) {
                        PaymentCategoryResponse child = new PaymentCategoryResponse();
                        child.setId(null);
                        child.setParentId(dto.getId());
                        child.setParent(toShallowParentFromResponse(dto));
                        child.setName(c.getName());
                        child.setChildren(Collections.emptyList());
                        dto.getChildren().add(child);
                    }
                }
                case BUYER -> {
                    List<CompanyResponse> buyers = safeGetBuyer();
                    for (CompanyResponse c : buyers) {
                        PaymentCategoryResponse child = new PaymentCategoryResponse();
                        child.setId(null);
                        child.setParentId(dto.getId());
                        child.setParent(toShallowParentFromResponse(dto));
                        child.setName(c.getName());
                        child.setChildren(Collections.emptyList());
                        dto.getChildren().add(child);
                    }
                }
            }
        }

        return dto;
    }

    private PaymentCategoryResponse toParentResponseWithChildren(PaymentCategory entity) {
        if (entity == null) return null;

        // We do NOT use the context cache here because we want a specialized "Parent View"
        // that behaves differently (doesn't link back to children's parents).

        PaymentCategoryResponse dto = new PaymentCategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);

        // Important: Stop going up the tree. The parent of the parent is just an ID or null here.
        dto.setParent(null);

        dto.setChildren(new ArrayList<>());

        if (entity.getChildren() != null) {
            for (PaymentCategory child : entity.getChildren()) {
                // For siblings, we map them BUT we ensure they don't link back to this parent
                // to avoid the cycle (Child -> Parent -> Child -> Parent)
                dto.getChildren().add(toShallowChildResponse(child));
            }
        }
        return dto;
    }

    private PaymentCategoryResponse toShallowChildResponse(PaymentCategory entity) {
        if (entity == null) return null;
        PaymentCategoryResponse dto = new PaymentCategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);
        dto.setParent(null); // No parent link
        dto.setChildren(null); // No deeper children
        return dto;
    }

    protected List<PaymentCategoryResponse> mapChildren(List<PaymentCategory> children, @Context CycleAvoidingContext context) {
        if (children == null || children.isEmpty()) {
            return new ArrayList<>();
        }
        List<PaymentCategoryResponse> result = new ArrayList<>(children.size());
        for (PaymentCategory child : children) {
            result.add(toCategoryResponse(child, context));
        }
        return result;
    }

    private List<CompanyResponse> safeGetBuyer() {
        try {
            return enricher != null ? enricher.getBuyersSafe() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<EmployeeResponse> safeGetEmployees() {
        try {
            return enricher != null ? enricher.getEmployeesSafe() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<CompanyResponse> safeGetSellers() {
        try {
            return enricher != null ? enricher.getSafeSeller() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private PaymentCategoryResponse toShallowParentResponse(PaymentCategory entity) {
        if (entity == null) {
            return null;
        }
        PaymentCategoryResponse dto = new PaymentCategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);
        dto.setChildren(new ArrayList<>());
        return dto;
    }

    private PaymentCategoryResponse toShallowParentFromResponse(PaymentCategoryResponse parent) {
        if (parent == null) {
            return null;
        }
        PaymentCategoryResponse dto = new PaymentCategoryResponse();
        dto.setId(parent.getId());
        dto.setName(parent.getName());
        dto.setCategory(parent.getCategory());
        dto.setCreatedAt(parent.getCreatedAt());
        dto.setUpdatedAt(parent.getUpdatedAt());
        dto.setParentId(parent.getParentId());
        dto.setChildren(new ArrayList<>());
        return dto;
    }

    public static class CycleAvoidingContext {
        private final Map<Object, Object> map= new IdentityHashMap<>();

        public PaymentCategoryResponse get(PaymentCategory source) {
            return (PaymentCategoryResponse) map.get(source);
        }

        public void put(Object source, Object target) {
            map.put(source, target);
        }
    }

//    public static class CycleAvoidingMappingContext {
//        private Map<Object, Object> knownInstances = new IdentityHashMap<>();
//        private Set<Object> visiting = Collections.newSetFromMap(new IdentityHashMap<>());
//
//        @BeforeMapping
//        public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
//            return (T) knownInstances.get(source);
//        }
//
//        @AfterMapping
//        public void storeMappedInstance(Object source, @MappingTarget Object target) {
//            knownInstances.put(source, target);
//        }
//
//        public void startVisiting(Object source) {
//            visiting.add(source);
//        }
//
//        public boolean isVisiting(Object source) {
//            return visiting.contains(source);
//        }
//
//        public void finishVisiting(Object source) {
//            visiting.remove(source);
//        }
//    }
}
