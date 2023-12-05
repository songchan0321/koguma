package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubPostCategory is a Querydsl query type for ClubPostCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubPostCategory extends EntityPathBase<ClubPostCategory> {

    private static final long serialVersionUID = -117156292L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubPostCategory clubPostCategory = new QClubPostCategory("clubPostCategory");

    public final QClub club;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QClubPostCategory(String variable) {
        this(ClubPostCategory.class, forVariable(variable), INITS);
    }

    public QClubPostCategory(Path<? extends ClubPostCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubPostCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubPostCategory(PathMetadata metadata, PathInits inits) {
        this(ClubPostCategory.class, metadata, inits);
    }

    public QClubPostCategory(Class<? extends ClubPostCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

