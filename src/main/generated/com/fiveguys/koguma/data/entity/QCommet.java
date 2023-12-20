package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommet is a Querydsl query type for Commet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommet extends EntityPathBase<Commet> {

    private static final long serialVersionUID = -1531445405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommet commet1 = new QCommet("commet1");

    public final StringPath commet = createString("commet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public QCommet(String variable) {
        this(Commet.class, forVariable(variable), INITS);
    }

    public QCommet(Path<? extends Commet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommet(PathMetadata metadata, PathInits inits) {
        this(Commet.class, metadata, inits);
    }

    public QCommet(Class<? extends Commet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

