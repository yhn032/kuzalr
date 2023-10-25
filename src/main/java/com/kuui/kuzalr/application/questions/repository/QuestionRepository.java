package com.kuui.kuzalr.application.questions.repository;

import com.kuui.kuzalr.application.questions.domain.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kuui.kuzalr.application.questions.domain.QQuestion.question1;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public int questionCntByType(String type){
        return em
                .createQuery("select q from Question q where q.type = :type and q.useYn = :useYn")
                .setParameter("type", type)
                .setParameter("useYn", "N")
                .getResultList()
                .size();
    }

    public Question findById(Long id){
        return em.find(Question.class, id);
    }

    public List<Long> questIdListByType(String type){
        return em
                .createQuery("select q.id from Question q where q.useYn = :useYn and q.type = :type")
                .setParameter("useYn", "N")
                .setParameter("type", type)
                .getResultList();
    }

    public Long updateCheckYn(Long id) {
        return queryFactory
                .update(question1)
                .set(question1.checkYn, "Y")
                .where(question1.id.eq(id))
                .execute();
    }

    public Long shuffleQuest(Long id, String type){
        return queryFactory
                .update(question1)
                .set(question1.type, type)
                .set(question1.useYn, "N")
                .set(question1.checkYn, "N")
                .where(question1.id.eq(id))
                .execute();
    }
}
