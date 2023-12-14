package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentHistorySpecifications{
    public static Specification<PaymentHistory> hasType(MemberDTO memberDTO, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = criteriaBuilder.disjunction();
            Arrays.stream(PaymentHistoryType.values()).forEach((paymentHistoryType) -> {
                if(paymentHistoryType.name().contains(type)) {
                    typePredicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), paymentHistoryType));
                }
            });
            if (PaymentHistoryType.contains(type))
                predicates.add(typePredicate);
            predicates.add(criteriaBuilder.equal(root.get("member"), memberDTO.toEntity()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
