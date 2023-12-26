package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubPost is a Querydsl query type for ClubPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubPost extends EntityPathBase<ClubPost> {

    private static final long serialVersionUID = -989514978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubPost clubPost = new QClubPost("clubPost");

    public final QBaseTime _super = new QBaseTime(this);

    public final StringPath categoryName = createString("categoryName");

    public final QClub club;

    public final StringPath clubMemberNickname = createString("clubMemberNickname");

    public final StringPath clubName = createString("clubName");

    public final QClubPostCategory clubPostCategory;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath images = createString("images");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath title = createString("title");

    public QClubPost(String variable) {
        this(ClubPost.class, forVariable(variable), INITS);
    }

    public QClubPost(Path<? extends ClubPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubPost(PathMetadata metadata, PathInits inits) {
        this(ClubPost.class, metadata, inits);
    }

    public QClubPost(Class<? extends ClubPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.clubPostCategory = inits.isInitialized("clubPostCategory") ? new QClubPostCategory(forProperty("clubPostCategory"), inits.get("clubPostCategory")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

