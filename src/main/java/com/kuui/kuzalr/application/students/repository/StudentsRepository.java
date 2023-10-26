package com.kuui.kuzalr.application.students.repository;

import com.kuui.kuzalr.application.students.domain.Student;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kuui.kuzalr.application.students.domain.QStudent.student;

@Repository
@RequiredArgsConstructor
public class StudentsRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Student student){
        em.persist(student);
    }

    public List<Student> findAllStudentList() {
        return em.createQuery("select s from Student s").getResultList();
    }
//    public Long createUser(HashMap<String, String> studentName){
//        return
//                em.createNativeQuery("INSERT INTO students (name, christian_name, cnt) VALUES()")
//    }

    public Student findById(Long id) {return em.find(Student.class, id);}

    //학생 정보 새로 생성시 기존에 있던 정보 삭제
    public int deleteAll(List<Long> studentIdList){
        studentIdList.forEach(studentId -> queryFactory
                .delete(student)
                .where(student.id.eq(studentId))
                .execute());

        return 0;
    }

    //학생 정보 수정
    public int update(List<Student> studentList){
        studentList.forEach(student1 ->
                queryFactory
                        .update(student)
                        .set(student.name, student1.getName())
                        .set(student.christian_name, student1.getChristian_name())
                        .where(student.id.eq(student1.getId()))
                        .execute());

        return 0;
    }

    //정답체크 + 1
    public Long checkTheAns(Long studentId, int Cnt){

        return  queryFactory
                .update(student)
                .set(student.cnt, Cnt)
                .where(student.id.eq(studentId))
                .execute();
    }

    //reset하는 경우 학색들의 점수 초기화
    public Long resetStudentPoint(Long studentId){
        return queryFactory
                .update(student)
                .set(student.cnt, 0)
                .where(student.id.eq(studentId))
                .execute();
    }

    //점수 상위 3명 학생 조회
    public List<Student> top3Student(){
        return queryFactory
                .selectFrom(student)
                .orderBy(student.cnt.desc())
                .fetch();
    }

    //학생 이미지 업데이트
    public Long updateImgUrl(Long id, String url){
        return queryFactory
                .update(student)
                .set(student.imgUrl, url)
                .where(student.id.eq(id))
                .execute();
    }
}
