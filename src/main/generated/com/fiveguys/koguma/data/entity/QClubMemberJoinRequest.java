package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMemberJoinRequest is a Querydsl query type for ClubMemberJoinRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMemberJoinRequest extends EntityPathBase<ClubMemberJoinRequest> {

    private static final long serialVersionUID = -940464819L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMemberJoinRequest clubMemberJoinRequest = new QClubMemberJoinRequest("clubMemberJoinRequest");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final QClub club;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath nickname = createString("nickname");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QClubMemberJoinRequest(String variable) {
        this(ClubMemberJoinRequest.class, forVariable(variable), INITS);
    }

    public QClubMemberJoinRequest(Path<? extends ClubMemberJoinRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMemberJoinRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMemberJoinRequest(PathMetadata metadata, PathInits inits) {
        this(ClubMemberJoinRequest.class, metadata, inits);
    }

    public QClubMemberJoinRequest(Class<? extends ClubMemberJoinRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

