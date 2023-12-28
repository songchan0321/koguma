package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubComment is a Querydsl query type for ClubComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubComment extends EntityPathBase<ClubComment> {

    private static final long serialVersionUID = -938452191L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubComment clubComment = new QClubComment("clubComment");

    public final QBaseTime _super = new QBaseTime(this);

    public final QClub club;

    public final QClubMember clubMember;

    public final QClubPost clubPost;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QClubComment(String variable) {
        this(ClubComment.class, forVariable(variable), INITS);
    }

    public QClubComment(Path<? extends ClubComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubComment(PathMetadata metadata, PathInits inits) {
        this(ClubComment.class, metadata, inits);
    }

    public QClubComment(Class<? extends ClubComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.clubMember = inits.isInitialized("clubMember") ? new QClubMember(forProperty("clubMember"), inits.get("clubMember")) : null;
        this.clubPost = inits.isInitialized("clubPost") ? new QClubPost(forProperty("clubPost"), inits.get("clubPost")) : null;
    }

}

