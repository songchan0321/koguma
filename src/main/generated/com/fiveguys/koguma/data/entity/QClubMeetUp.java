package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMeetUp is a Querydsl query type for ClubMeetUp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMeetUp extends EntityPathBase<ClubMeetUp> {

    private static final long serialVersionUID = -1831658432L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMeetUp clubMeetUp = new QClubMeetUp("clubMeetUp");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final QClub club;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxCapacity = createNumber("maxCapacity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> meetDate = createDateTime("meetDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath roadAddr = createString("roadAddr");

    public final StringPath title = createString("title");

    public QClubMeetUp(String variable) {
        this(ClubMeetUp.class, forVariable(variable), INITS);
    }

    public QClubMeetUp(Path<? extends ClubMeetUp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMeetUp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMeetUp(PathMetadata metadata, PathInits inits) {
        this(ClubMeetUp.class, metadata, inits);
    }

    public QClubMeetUp(Class<? extends ClubMeetUp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

