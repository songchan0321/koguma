package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMemberMeetUpJoin is a Querydsl query type for ClubMemberMeetUpJoin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMemberMeetUpJoin extends EntityPathBase<ClubMemberMeetUpJoin> {

    private static final long serialVersionUID = 386024772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMemberMeetUpJoin clubMemberMeetUpJoin = new QClubMemberMeetUpJoin("clubMemberMeetUpJoin");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final QClubMeetUp clubMeetUp;

    public final QClubMember clubMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QClubMemberMeetUpJoin(String variable) {
        this(ClubMemberMeetUpJoin.class, forVariable(variable), INITS);
    }

    public QClubMemberMeetUpJoin(Path<? extends ClubMemberMeetUpJoin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMemberMeetUpJoin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMemberMeetUpJoin(PathMetadata metadata, PathInits inits) {
        this(ClubMemberMeetUpJoin.class, metadata, inits);
    }

    public QClubMemberMeetUpJoin(Class<? extends ClubMemberMeetUpJoin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubMeetUp = inits.isInitialized("clubMeetUp") ? new QClubMeetUp(forProperty("clubMeetUp"), inits.get("clubMeetUp")) : null;
        this.clubMember = inits.isInitialized("clubMember") ? new QClubMember(forProperty("clubMember"), inits.get("clubMember")) : null;
    }

}

