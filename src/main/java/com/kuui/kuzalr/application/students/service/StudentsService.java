package com.kuui.kuzalr.application.students.service;

import com.kuui.kuzalr.application.students.domain.Student;
import com.kuui.kuzalr.application.students.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentsService {

    public final StudentsRepository studentsRepository;

    public List<Student> findAllStudentList(){
       return studentsRepository.findAllStudentList();
    }

    @Transactional
    public void createUser(HashMap<String, String> studentName){

        Set<String> name = studentName.keySet();
        for(String n : name){
            Student student = new Student();
            student.setCnt(0);
            student.setName(n);
            student.setChristian_name(studentName.get(n));

            studentsRepository.save(student);
        }
    }

    @Transactional
    public int deleteAll(List<Long> allStudentIdList){
        return studentsRepository.deleteAll(allStudentIdList);
    }

    @Transactional
    public int update(List<Student> allStudentList){
        return  studentsRepository.update(allStudentList);
    }

    @Transactional
    public Student findById(Long id) {return studentsRepository.findById(id);}

    @Transactional
    public Long checkTheAnd(Long id, int cnt){return studentsRepository.checkTheAns(id, cnt);}

    @Transactional
    public Long resetStudentPoint(Long studentId) {return studentsRepository.resetStudentPoint(studentId);}

    public List<Student> top3Student (){return studentsRepository.top3Student();}

    @Transactional
    public Long updateImgUrl(Long id, String url) {return studentsRepository.updateImgUrl(id,url);}
}
