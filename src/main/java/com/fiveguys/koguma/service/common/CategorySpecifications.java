package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.CategoryType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategorySpecifications {

    public static Specification<Category> hasType(CategoryType type){

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Arrays.stream(CategoryType.values()).forEach((categoryType) ->{
                if(categoryType.equals(categoryType)){
                    predicates.add(criteriaBuilder.equal(root.get("categoryType"), type));
                }
            });
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


