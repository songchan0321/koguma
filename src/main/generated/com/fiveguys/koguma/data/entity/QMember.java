package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1254399678L;

    public static final QMember member = new QMember("member1");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final EnumPath<MemberRoleType> memberRoleType = createEnum("memberRoleType", MemberRoleType.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath paymentAccount = createString("paymentAccount");

    public final NumberPath<Integer> paymentBalance = createNumber("paymentBalance", Integer.class);

    public final StringPath paymentBank = createString("paymentBank");

    public final StringPath paymentPw = createString("paymentPw");

    public final StringPath phone = createString("phone");

    public final StringPath pw = createString("pw");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final BooleanPath roleFlag = createBoolean("roleFlag");

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final BooleanPath socialFlag = createBoolean("socialFlag");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

