package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberProductSuggestId is a Querydsl query type for MemberProductSuggestId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberProductSuggestId extends BeanPath<MemberProductSuggestId> {

    private static final long serialVersionUID = -134940430L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberProductSuggestId memberProductSuggestId = new QMemberProductSuggestId("memberProductSuggestId");

    public final QMember member;

    public final QProduct product;

    public QMemberProductSuggestId(String variable) {
        this(MemberProductSuggestId.class, forVariable(variable), INITS);
    }

    public QMemberProductSuggestId(Path<? extends MemberProductSuggestId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberProductSuggestId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberProductSuggestId(PathMetadata metadata, PathInits inits) {
        this(MemberProductSuggestId.class, metadata, inits);
    }

    public QMemberProductSuggestId(Class<? extends MemberProductSuggestId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

