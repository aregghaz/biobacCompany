package com.biobac.company.mapper;

import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.request.PaymentCategoryRequest;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.EmployeeResponse;
import com.biobac.company.response.PaymentCategoryResponse;
import com.biobac.company.response.PaymentResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
        if (existing != null) return existing;

        PaymentCategoryResponse response = PaymentCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .category(entity.getCategory())
                .children(new ArrayList<>())
                .build();

        context.put(entity, response);

        if (entity.getParent() != null) {
            response.setParent(toParentResponseWithChildren(entity.getParent()));
        }

        for (PaymentCategory child : entity.getChildren()) {
            response.getChildren().add(toCategoryResponse(child, context));
        }

        if (entity.getCategory() != null) {
            switch (entity.getCategory()) {
                case EMPLOYEE -> {
                    List<EmployeeResponse> employees = safeGetEmployees();
                    for (EmployeeResponse e : employees) {

                        final String name = (e.getLastname() != null ? e.getLastname() : "")
                                + (e.getFirstname() != null ? (" " + e.getFirstname()) : "");

                        PaymentCategoryResponse child = PaymentCategoryResponse.builder()
                                .id(e.getId())
                                .parentId(response.getParentId())
                                .parent(toShallowParentFromResponse(response))
                                .name(name.trim())
                                .children(Collections.emptyList())
                                .build();
                        response.getChildren().add(child);
                    }
                }
                case SELLER -> {
                    List<CompanyResponse> sellers = safeGetSellers();
                    for (CompanyResponse seller : sellers) {
                        PaymentCategoryResponse child = PaymentCategoryResponse.builder()
                                .id(seller.getId())
                                .parentId(response.getId())
                                .parent(toShallowParentFromResponse(response))
                                .name(seller.getName())
                                .children(Collections.emptyList())
                                .build();
                        response.getChildren().add(child);
                    }
                }
                case BUYER -> {
                    List<CompanyResponse> buyers = safeGetBuyer();
                    for (CompanyResponse buyer : buyers) {
                        PaymentCategoryResponse child = PaymentCategoryResponse.builder()
                                .id(buyer.getId())
                                .parentId(response.getId())
                                .parent(toShallowParentFromResponse(response))
                                .name(buyer.getName())
                                .children(Collections.emptyList())
                                .build();
                        response.getChildren().add(child);
                    }
                }
            }
        }

        return response;
    }

    private PaymentCategoryResponse toParentResponseWithChildren(PaymentCategory entity) {
        if (entity == null) return null;

        // We do NOT use the context cache here because we want a specialized "Parent View"
        // that behaves differently (doesn't link back to children's parents).

        PaymentCategoryResponse response = PaymentCategoryResponse.builder()
                .id(entity.getId())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .parent(null)
                .name(entity.getName())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .children(new ArrayList<>())
                .build();

        if (entity.getChildren() != null) {
            for (PaymentCategory child : entity.getChildren()) {
                // For siblings, we map them BUT we ensure they don't link back to this parent
                // to avoid the cycle (Child -> Parent -> Child -> Parent)
                response.getChildren().add(toShallowChildResponse(child));
            }
        }
        return response;
    }

    private PaymentCategoryResponse toShallowChildResponse(PaymentCategory entity) {
        if (entity == null) return null;

        return PaymentCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .parent(null)    // No parent link
                .children(null)   // No deeper children
                .build();
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
            return enricher != null ? enricher.getSellersSafe() : Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            throw  e;
        }
    }

    private PaymentCategoryResponse toShallowParentFromResponse(PaymentCategoryResponse parent) {
        if (parent == null) return null;

        return PaymentCategoryResponse.builder()
                .id(parent.getId())
                .name(parent.getName())
                .category(parent.getCategory())
                .createdAt(parent.getCreatedAt())
                .updatedAt(parent.getUpdatedAt())
                .parentId(parent.getParentId())
                .children(new ArrayList<>())
                .build();
    }

    public static class CycleAvoidingContext {
        private final Map<Object, Object> map = new IdentityHashMap<>();

        public PaymentCategoryResponse get(PaymentCategory source) {
            return (PaymentCategoryResponse) map.get(source);
        }

        public void put(Object source, Object target) {
            map.put(source, target);
        }
    }
}
