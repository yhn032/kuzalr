package com.kuui.kuzalr.application.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTYPECHECK is a Querydsl query type for TYPECHECK
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTYPECHECK extends EntityPathBase<TYPECHECK> {

    private static final long serialVersionUID = 1256698165L;

    public static final QTYPECHECK tYPECHECK = new QTYPECHECK("tYPECHECK");

    public final StringPath qNum = createString("qNum");

    public final StringPath type = createString("type");

    public QTYPECHECK(String variable) {
        super(TYPECHECK.class, forVariable(variable));
    }

    public QTYPECHECK(Path<? extends TYPECHECK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTYPECHECK(PathMetadata metadata) {
        super(TYPECHECK.class, metadata);
    }

}

