package com.kuui.kuzalr.application.students.controller;

import com.kuui.kuzalr.application.questions.service.QuestionService;
import com.kuui.kuzalr.application.students.domain.Student;
import com.kuui.kuzalr.application.students.service.StudentsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Getter
public class StudentsController {

    private final StudentsService studentsService;
    private final QuestionService questionService;

    @PostMapping("/student/createUser")
    public String createUser(@RequestParam("name")String[] name, @RequestParam("christian_name") String[] c_name, Model model){
        HashMap<String, String> studentName = new HashMap<>();
        for(int i=0; i<name.length;i++){
            if(name[i].equals("")) continue;
            studentName.put(name[i], c_name[i]);
        }

        // 학생 정보 insert
        studentsService.createUser(studentName);
        List<Student> allStudentList = studentsService.findAllStudentList();
        //imgUrl update
        allStudentList.forEach(student -> studentsService.updateImgUrl(student.getId(), "/img/" + student.getId().intValue() % 15 + ".jpg"));

        //img가 없데이트 된 학생으로 조회
        List<Student> allStudentList1 = studentsService.findAllStudentList();

        model.addAttribute("studentList",allStudentList1);

        //validation 필요함 (insert가 안 된 경우 에러페이지로)
        return "student/showStudents";
    }

    @GetMapping("/student/new")
    public String popUp(Model model){
        List<Student> allStudentList = studentsService.findAllStudentList();
        if(allStudentList.size() > 0 ){//이미 설정 완료
            model.addAttribute("studentList", allStudentList);
            return "student/showStudents";
        }else {
            return "student/insertStudents";
        }


    }

    @GetMapping("/student/update")
    public String updateStudent(Model model){
        List<Student> allStudentList = studentsService.findAllStudentList();
        model.addAttribute("studentList", allStudentList);

        return "student/updateStudents";
    }

    @PostMapping("/student/update")
    public String update(@RequestParam("id")Long[] id,@RequestParam("name")String[] name, @RequestParam("christian_name") String[] c_name, Model model){
        List<Student> allStudentList = new ArrayList<>();
        System.out.println("id.length = " + id.length);
        System.out.println("name.length = " + name.length);
        System.out.println("c_name.length = " + c_name.length);

        //id개수 만큼 update
        for(int i=0; i<id.length; i++){
            Student student = new Student();
            student.setId(id[i]);
            student.setName(name[i]);
            student.setChristian_name(c_name[i]);

            allStudentList.add(student);
        }
        studentsService.update(allStudentList);


        HashMap<String, String> studentName = new HashMap<>();
        for(int i=id.length; i<name.length;i++){
            if(name[i].equals("")) continue;
            studentName.put(name[i], c_name[i]);
        }
        if(id.length < name.length){ //새로운 학생이 들어온 경우 해당 학생은 신규 추가 save
            studentsService.createUser(studentName);

            //img url이 없는 친구들만 조회하여 업데이트하기
            List<Student> allImgNull = studentsService.findAllImgNull();

            allImgNull.forEach(student -> studentsService.updateImgUrl(student.getId(), "/img/" + student.getId().intValue() % 15 + ".jpg"));
        }

        List<Student> allStudentList2 = studentsService.findAllStudentList();
        model.addAttribute("studentList", allStudentList2);
        return "student/showStudents";
    }

    @GetMapping("/student/reset")
    public String resetStudent(){

        List<Student> allStudentList = studentsService.findAllStudentList();
        List<Long> studentIdList = new ArrayList<>();
        allStudentList.forEach(student -> studentIdList.add(student.getId()));

        //학생 삭제
        int im = studentsService.deleteAll(studentIdList);
        return "student/insertStudents";
    }

    @GetMapping("/student/plusCnt")
    @ResponseBody
    public String plusCnt(@RequestParam("studentId")String id, @RequestParam("q_id")String q_id){

        Long studentId = Long.parseLong(id);
        Student student = studentsService.findById(studentId);
        Long executeCnt = studentsService.checkTheAnd(studentId, student.getCnt()+1);

        //특정 답안에 대한 정답여부 디비 update
        questionService.updateCheckYn(Long.parseLong(q_id));

        if(executeCnt > 0) return "success";
        else return "false";
    }

}
