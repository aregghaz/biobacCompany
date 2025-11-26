package com.biobac.company.utils.specifications;

import com.biobac.company.entity.Company;
import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.entity.CompanyType;
import com.biobac.company.request.FilterCriteria;
import jakarta.persistence.criteria.Join;
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

public class CompanyGroupSpecification {
    public static Specification<CompanyGroup> buildSpecification(Map<String, FilterCriteria> filters) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            Join<Company, CompanyType> companyTypeJoin = null;

            if (filters != null) {
                for (Map.Entry<String, FilterCriteria> entry : filters.entrySet()) {
                    String field = entry.getKey();
                    Path<?> path;
                    path = root.get(field);

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

    public static Specification<CompanyGroup> belongsToGroups(List<Long> groupIds) {
        return (root, query, cb) -> {
            if (groupIds == null || groupIds.isEmpty()) {
                return cb.disjunction();
            }
            return root.get("id").in(groupIds);
        };
    }
}
