package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRelationship is a Querydsl query type for MemberRelationship
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRelationship extends EntityPathBase<MemberRelationship> {

    private static final long serialVersionUID = -769042278L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRelationship memberRelationship = new QMemberRelationship("memberRelationship");

    public final QBaseTime _super = new QBaseTime(this);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MemberRelationshipType> memberRelationshipType = createEnum("memberRelationshipType", MemberRelationshipType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QMember sourceMember;

    public final QMember targetMember;

    public QMemberRelationship(String variable) {
        this(MemberRelationship.class, forVariable(variable), INITS);
    }

    public QMemberRelationship(Path<? extends MemberRelationship> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRelationship(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRelationship(PathMetadata metadata, PathInits inits) {
        this(MemberRelationship.class, metadata, inits);
    }

    public QMemberRelationship(Class<? extends MemberRelationship> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sourceMember = inits.isInitialized("sourceMember") ? new QMember(forProperty("sourceMember")) : null;
        this.targetMember = inits.isInitialized("targetMember") ? new QMember(forProperty("targetMember")) : null;
    }

}

