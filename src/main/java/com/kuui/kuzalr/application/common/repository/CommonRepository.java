package com.kuui.kuzalr.application.common.repository;

import com.kuui.kuzalr.application.common.domain.TYPECHECK;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.kuui.kuzalr.application.common.domain.QTYPECHECK.tYPECHECK;

@Repository
@RequiredArgsConstructor
public class CommonRepository {
    public final EntityManager em;
    public final JPAQueryFactory queryFactory;
    public TYPECHECK findQNumByType(String type) {
        return em.find(TYPECHECK.class, type);
    }

    public Long updateQNumList(String type, String qNum){
        return queryFactory
                .update(tYPECHECK)
                .set(tYPECHECK.qNum, qNum)
                .where(tYPECHECK.type.eq(type))
                .execute();
    }
}
