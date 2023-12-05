package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatroom is a Querydsl query type for Chatroom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatroom extends EntityPathBase<Chatroom> {

    private static final long serialVersionUID = -799509285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatroom chatroom = new QChatroom("chatroom");

    public final QBaseTime _super = new QBaseTime(this);

    public final BooleanPath activeFlag = createBoolean("activeFlag");

    public final QMember buyer;

    public final DateTimePath<java.time.LocalDateTime> buyerEnterDate = createDateTime("buyerEnterDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final DateTimePath<java.time.LocalDateTime> sellerEnterDate = createDateTime("sellerEnterDate", java.time.LocalDateTime.class);

    public QChatroom(String variable) {
        this(Chatroom.class, forVariable(variable), INITS);
    }

    public QChatroom(Path<? extends Chatroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatroom(PathMetadata metadata, PathInits inits) {
        this(Chatroom.class, metadata, inits);
    }

    public QChatroom(Class<? extends Chatroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new QMember(forProperty("buyer")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

