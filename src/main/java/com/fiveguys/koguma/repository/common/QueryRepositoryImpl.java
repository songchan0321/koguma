package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.*;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryRepositoryImpl implements QueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<?> findAllByDistance(CategoryType target, LocationDTO locationDTO, Pageable pageable, String keyword) throws Exception {
        EntityPath<?> entity = null;

        switch (target) {
            case PRODUCT:
                entity = QProduct.product;
                break;
            case CLUB:
                entity = QClub.club;
                break;
            case POST:
                entity = QPost.post;
                break;
            default:
                throw new Exception("잘못된 엔티티 입력");
        }

        PathBuilder<Object> pathBuilder = new PathBuilder<>(Object.class, entity.getMetadata());

        NumberPath<Double> longitudePath = pathBuilder.getNumber("longitude", Double.class);
        NumberPath<Double> latitudePath = pathBuilder.getNumber("latitude", Double.class);
        StringPath titlePath = pathBuilder.getString("title");
        JPAQuery<?> jpaQuery = jpaQueryFactory
                .select(entity)
                .from(entity)
                .where(Expressions.numberTemplate(Double.class,
                        "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                        locationDTO.getLongitude(), locationDTO.getLatitude(),
                        longitudePath, latitudePath).loe(locationDTO.getSearchRange() * 1000));

        if (keyword != null) {
            jpaQuery.where(titlePath.containsIgnoreCase(keyword));
        }

        List<?> filterList = jpaQuery
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();

        return filterList;
    }

}