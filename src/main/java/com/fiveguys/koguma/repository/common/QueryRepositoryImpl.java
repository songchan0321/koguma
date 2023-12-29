package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.*;
import com.querydsl.core.Tuple;
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
import java.util.stream.Collectors;

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

        QImage image = QImage.image;

        JPAQuery<?> jpaQuery = jpaQueryFactory
                .select(entity, image)
                .from(entity)
                .leftJoin(image).on(getJoinCondition(entity, image))
                .where(Expressions.numberTemplate(Double.class,
                        "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                        locationDTO.getLongitude(), locationDTO.getLatitude(),
                        longitudePath, latitudePath).loe(locationDTO.getSearchRange() * 1000));

        if (keyword != null) {
            jpaQuery.where(titlePath.containsIgnoreCase(keyword));
        }
        switch (target) {
            case PRODUCT:
                jpaQuery.orderBy(QProduct.product.regDate.desc()); // QProduct.product.regDate.desc() 대신 entity.regDate.desc() 사용


                List<Tuple> filterList = (List<Tuple>) jpaQuery.fetch();

                List<Product> products = filterList.stream()
                        .map(tuple -> tuple.get(0, Product.class)) // 첫 번째 엔터티는 Product
                        .collect(Collectors.toList());
                for (Product product : products) {
                    System.out.println(product.toString());
                }
                return products;
            case CLUB:
                entity = QClub.club;
                break;
            case POST:
                entity = QPost.post;
                break;
            default:
                throw new Exception("잘못된 엔티티 입력");
        }
        return null;
    }
    public List<Product> findAllByDistanceProduct(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception {
        QProduct product = QProduct.product;
        QImage image = QImage.image;

        JPAQuery<Product> jpaQuery = jpaQueryFactory
                .selectDistinct(product)
                .from(product)
                .leftJoin(image).on(getJoinCondition(product, image))
                .where(
                        Expressions.numberTemplate(Double.class,
                                        "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                                        locationDTO.getLongitude(), locationDTO.getLatitude(),
                                        product.longitude, product.latitude)
                                .loe(locationDTO.getSearchRange() * 1000),
                        keyword != null ? product.title.containsIgnoreCase(keyword)
                                .or(product.seller.nickname.containsIgnoreCase(keyword))
                                : null,
                        categoryId != null ? product.categoryId.eq(categoryId) : null,
                        product.activeFlag.isTrue()

                )
                .orderBy(product.regDate.desc());

        return jpaQuery.fetch();
    }

    @Override
    public List<Post> findAllByDistancePost(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception {
        QPost post = QPost.post;
        QImage image = QImage.image;
        JPAQuery<Post> jpaQuery = jpaQueryFactory
                .selectDistinct(post)
                .from(post)
                .leftJoin(image).on(getJoinCondition(post, image))
                .where(
                        Expressions.numberTemplate(Double.class,
                                        "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                                        locationDTO.getLongitude(), locationDTO.getLatitude(),
                                        post.longitude, post.latitude)
                                .loe(locationDTO.getSearchRange() * 1000),
                        keyword != null ? post.title.containsIgnoreCase(keyword) : null,
                        categoryId != null ? post.category.id.eq(categoryId) : null,
                        post.activeFlag.isTrue()
                )
                .orderBy(post.regDate.desc());

        return jpaQuery.fetch();
    }

    @Override
    public List<Club> findAllByDistanceClub(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception {
        QClub club = QClub.club;

        JPAQuery<Club> jpaQuery = jpaQueryFactory
                .selectFrom(club)
                .where(
                        Expressions.numberTemplate(Double.class,
                                        "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                                        locationDTO.getLongitude(), locationDTO.getLatitude(),
                                        club.longitude, club.latitude)
                                .loe(locationDTO.getSearchRange() * 1000),
                        keyword != null ? club.title.containsIgnoreCase(keyword) : null,
                        categoryId != null ? club.category.id.eq(categoryId) : null,
                        club.activeFlag.isTrue()
                )
                .orderBy(club.regDate.desc());

        return jpaQuery.fetch();
    }
    @Override
    public List<Member> findAllByDistanceMember(LocationDTO locationDTO, String keyword) throws Exception {


        QLocation location = QLocation.location;   // location으로 5키로 반경 조회하고 memberId만 뽑아내면 해당 memberId로 findById로 조회
        QMember member = QMember.member;
        JPAQuery<Member> jpaQuery = jpaQueryFactory
                .selectFrom(member)
                .leftJoin(location).on(location.member.eq(member))
                        .where(
                                Expressions.numberTemplate(Double.class,
                                                "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                                                locationDTO.getLongitude(), locationDTO.getLatitude(),
                                                location.longitude, location.latitude)
                                        .loe(5000),
                                keyword != null ? member.nickname.containsIgnoreCase(keyword) : null,
                                member.activeFlag.isTrue(),
                                location.repAuthLocationFlag.isTrue()

                        );


        return jpaQuery.fetch();
    }


//    private com.querydsl.core.types.dsl.BooleanExpression getJoinCondition(QProduct product, QImage image) {
//        return product.eq(image.product);
//    }

    private com.querydsl.core.types.dsl.BooleanExpression getJoinCondition(EntityPath<?> entity, QImage image) {
        switch (entity.getType().getSimpleName()) {
            case "Product":
                return QProduct.product.eq(image.product);
            case "Club":
                return QClub.club.eq(image.club);
            case "Post":
                return QPost.post.eq(image.post);
            default:
                throw new IllegalArgumentException("잘못된 엔티티 입력");
        }
    }

}