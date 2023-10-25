package com.kuui.kuzalr.application.common.controller;

import com.kuui.kuzalr.application.questions.service.QuestionService;
import com.kuui.kuzalr.application.students.domain.Student;
import com.kuui.kuzalr.application.students.service.StudentsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Getter
public class CommonController {

    private final StudentsService studentsService;
    private final QuestionService questionService;

    @GetMapping("/common/home")
    public String home() {
        return "index";
    }



    @GetMapping("/common/gameStart")
    @ResponseBody
    public String gameStart(){
        List<Student> findAllStudentList = studentsService.findAllStudentList();
        System.out.println("findAllStudentList.size() = " + findAllStudentList.size());
        String answer = "";
        if(findAllStudentList.size() > 0) answer = "success";
        else answer = "fail";
        return answer;
    }

    @GetMapping("/common/gameStarts")
    public String game(Model model){
        int A = questionService.questionCntByType("A");
        int B = questionService.questionCntByType("B");
        int C = questionService.questionCntByType("C");
        int D = questionService.questionCntByType("D");
        int E = questionService.questionCntByType("E");
        int F = questionService.questionCntByType("F");

        model.addAttribute("ACnt", A);
        model.addAttribute("BCnt", B);
        model.addAttribute("CCnt", C);
        model.addAttribute("DCnt", D);
        model.addAttribute("ECnt", E);
        model.addAttribute("FCnt", F);

        return "common/gameMain";
    }
    @GetMapping("/student/resetAll")
    public String resetStudent(){

        List<Student> allStudentList = studentsService.findAllStudentList();
        List<Long> studentIdList = new ArrayList<>();
        allStudentList.forEach(student -> studentIdList.add(student.getId()));

        //학생 삭제
        int im = studentsService.deleteAll(studentIdList);

        //shuffle and replay
        //question table의 index 섞어서 type 데이터 update(type, useYn, checkYn)
        List<Integer> qIdx = new ArrayList<>();
        int cnt = 1;
        for(int i=0; i<36; i++){qIdx.add(cnt++);}
        int qIdxSize = qIdx.size();

        //A
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "A", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());

        //B
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "B", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());

        //C
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "C", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());

        //D
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "D", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());

        //E
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "E", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());

        //F
        qIdxSize = qIdx.size();
        qIdx = shuffle(qIdx, "F", qIdxSize);
        System.out.println("qIdx.size() = " + qIdx.size());


        return "index";
    }
    public List<Integer> shuffle(List<Integer> qIdx, String type, int qIdxSize){
        Collections.shuffle(qIdx);
        for(int i=qIdxSize-1; i>=qIdxSize-6; i--) {
            int t = qIdx.get(i);
            Long id = Long.parseLong(String.valueOf(t));
            questionService.shuffleQuest(id, type);
            qIdx.remove(i);
        }

        return qIdx;
    }

}
