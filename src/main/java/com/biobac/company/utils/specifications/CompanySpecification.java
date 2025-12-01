package com.biobac.company.utils.specifications;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.entity.CompanyType;
import com.biobac.company.entity.Cooperation;
import com.biobac.company.request.FilterCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.biobac.company.utils.SpecificationUtil.buildBetween;
import static com.biobac.company.utils.SpecificationUtil.buildContains;
import static com.biobac.company.utils.SpecificationUtil.buildEquals;
import static com.biobac.company.utils.SpecificationUtil.buildGreaterThanOrEqualTo;
import static com.biobac.company.utils.SpecificationUtil.buildLessThanOrEqualTo;
import static com.biobac.company.utils.SpecificationUtil.buildNotEquals;

public class CompanySpecification {
    private static String isTypeField(String field) {
        Map<String, String> typeField = Map.of(
                "type", "id",
                "typeId", "id",
                "typeIds", "id",
                "typeName", "id"
        );
        return typeField.getOrDefault(field, null);
    }

    private static String isGroupField(String field) {
        Map<String, String> groupField = Map.of(
                "companyGroupId", "id"
        );
        return groupField.getOrDefault(field, null);
    }

    public static Specification<Company> buildSpecification(Map<String, FilterCriteria> filters) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            Join<Company, CompanyType> companyTypeJoin = null;
            Join<Company, CompanyGroup> companyGroupJoin = null;

            if (filters != null) {
                for (Map.Entry<String, FilterCriteria> entry : filters.entrySet()) {
                    String field = entry.getKey();
                    Path<?> path;
                    if (isTypeField(field) != null) {
                        if (companyTypeJoin == null) {
                            companyTypeJoin = root.join("types", JoinType.LEFT);
                        }
                        path = companyTypeJoin.get(isTypeField(field));
                    } else if (isGroupField(field) != null) {
                        if (companyGroupJoin == null) {
                            companyGroupJoin = root.join("companyGroup", JoinType.LEFT);
                        }
                        path = companyGroupJoin.get(isGroupField(field));
                    } else {
                        path = root.get(field);
                    }
                    FilterCriteria criteria = entry.getValue();
                    Predicate predicate = null;

                    switch (criteria.getOperator()) {
                        case "equals" -> predicate = buildEquals(cb, path, criteria.getValue());
                        case "notEquals" -> predicate = buildNotEquals(cb, path, criteria.getValue());
                        case "contains" -> predicate = buildContains(cb, path, criteria.getValue());
                        case "greaterThanOrEqualTo" ->
                                predicate = buildGreaterThanOrEqualTo(cb, path, criteria.getValue());
                        case "lessThanOrEqualTo" -> predicate = buildLessThanOrEqualTo(cb, path, criteria.getValue());
                        case "between" -> predicate = buildBetween(cb, path, criteria.getValue());
                    }

                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Company> isDeleted() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted")));
    }

    public static Specification<Company> belongsToGroups(List<Long> groupIds) {
        return (root, query, cb) -> {
            if (groupIds == null || groupIds.isEmpty()) {
                return cb.disjunction();
            }
            return root.get("companyGroup").get("id").in(groupIds);
        };
    }

    public static Specification<Company> filterBuyer() {
        return (root, query, cb) -> root.get("types").get("id").in(1);

    }

    public static Specification<Company> filterSeller() {
        return (root, query, cb) -> root.get("types").get("id").in(2);
    }

    public static Specification<Company> filterByCooperation() {
        return (root, query, cb) -> {
            Join<Company, Cooperation> cooperationJoin = root.join("cooperation", JoinType.INNER);
            return cb.equal(cooperationJoin.get("name"), "Да");
        };
    }
}
