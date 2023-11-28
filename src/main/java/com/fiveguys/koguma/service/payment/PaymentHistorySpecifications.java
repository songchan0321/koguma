package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentHistorySpecifications{
    public static Specification<PaymentHistory> hasType(PaymentHistoryType type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Arrays.stream(PaymentHistoryType.values()).forEach((paymentHistoryType) -> {
                if(paymentHistoryType.equals(type)) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
