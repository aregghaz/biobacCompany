package com.biobac.company.utils.specifications;

import com.biobac.company.entity.*;
import com.biobac.company.request.FilterCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.biobac.company.utils.SpecificationUtil.*;

public class SaleSpecification {
    public static Specification<Sale> buildSpecification(Map<String, FilterCriteria> filters) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            Join<Sale, SaleStatus> companyTypeJoin = null;
            Join<Sale, Company> saleCompanyJoin = null;
            Join<Sale, OurCompany> saleOurCompanyJoin = null;

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

    public static Specification<Sale> filterFinalizedSales() {
        return (root, query, cb) -> root.get("status").get("id").in(1L);
    }

    public static Specification<Sale> filterPendingSales() {
        return (root, query, cb) -> cb.not(root.get("status").get("id").in(1L));
    }
}
