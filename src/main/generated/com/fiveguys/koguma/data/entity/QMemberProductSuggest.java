package com.fiveguys.koguma.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberProductSuggest is a Querydsl query type for MemberProductSuggest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberProductSuggest extends EntityPathBase<MemberProductSuggest> {

    private static final long serialVersionUID = 218853751L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberProductSuggest memberProductSuggest = new QMemberProductSuggest("memberProductSuggest");

    public final QBaseTime _super = new QBaseTime(this);

    public final QMemberProductSuggestId id;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QMemberProductSuggest(String variable) {
        this(MemberProductSuggest.class, forVariable(variable), INITS);
    }

    public QMemberProductSuggest(Path<? extends MemberProductSuggest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberProductSuggest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberProductSuggest(PathMetadata metadata, PathInits inits) {
        this(MemberProductSuggest.class, metadata, inits);
    }

    public QMemberProductSuggest(Class<? extends MemberProductSuggest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QMemberProductSuggestId(forProperty("id"), inits.get("id")) : null;
    }

}

