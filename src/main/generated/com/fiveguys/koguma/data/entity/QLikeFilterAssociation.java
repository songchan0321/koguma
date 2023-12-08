package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeFilterAssociation is a Querydsl query type for LikeFilterAssociation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeFilterAssociation extends EntityPathBase<LikeFilterAssociation> {

    private static final long serialVersionUID = -239193238L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeFilterAssociation likeFilterAssociation = new QLikeFilterAssociation("likeFilterAssociation");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QPost post;

    public final QProduct product;

    public QLikeFilterAssociation(String variable) {
        this(LikeFilterAssociation.class, forVariable(variable), INITS);
    }

    public QLikeFilterAssociation(Path<? extends LikeFilterAssociation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeFilterAssociation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeFilterAssociation(PathMetadata metadata, PathInits inits) {
        this(LikeFilterAssociation.class, metadata, inits);
    }

    public QLikeFilterAssociation(Class<? extends LikeFilterAssociation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

