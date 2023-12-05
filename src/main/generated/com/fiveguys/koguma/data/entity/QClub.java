package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -1615002274L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClub club = new QClub("club");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final QCategory category;

    public final StringPath categoryName = createString("categoryName");

    public final StringPath content = createString("content");

    public final StringPath dong = createString("dong");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath joinActiveFlag = createBoolean("joinActiveFlag");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Integer> maxCapacity = createNumber("maxCapacity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath title = createString("title");

    public QClub(String variable) {
        this(Club.class, forVariable(variable), INITS);
    }

    public QClub(Path<? extends Club> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClub(PathMetadata metadata, PathInits inits) {
        this(Club.class, metadata, inits);
    }

    public QClub(Class<? extends Club> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

