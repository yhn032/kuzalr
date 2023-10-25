package com.kuui.kuzalr.application.questions.controller;

import com.kuui.kuzalr.application.common.service.CommonService;
import com.kuui.kuzalr.application.questions.domain.Question;
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
public class QuestionController {
    private final QuestionService questionService;
    private final StudentsService studentsService;
    private final CommonService commonService;

    @RequestMapping("/question/{type}/show")
    public String gamePage(@PathVariable("type") String type, Model model){
    //랜덤문자 꺼내오기(꺼내온 문자는 service 단에서 useYn = Y 처리)
        Long id = questionService.questIdListByType(type);
        Question quest = questionService.findById(id);
        model.addAttribute("question", quest);
        return "question/question";

//        //db값을 가져와
//        TYPECHECK TYPE = commonService.findQNumByType(type);
//        String typeDb = TYPE.getQNum();
//        if(typeDb.equals("")){//else -> 없다면 꺼내오기
//            //랜덤문자 꺼내오기(꺼내온 문자는 service 단에서 useYn = Y 처리)
//            Long id = questionService.questIdListByType(type);
//            Question quest = questionService.findById(id);
//            model.addAttribute("question", quest);
//
//            //타입에 대한 qnum추가하기
//            commonService.updateQNumList(type, String.valueOf(quest.getId()));
//        }else {//if -> type에 대한 db값이 있다면 랜덤 문자 꺼내기 x 문자열의 마지막 인덱스값 가져오기
//            String[] split = typeDb.split(",");
//            Question quest = questionService.findById(Long.parseLong(split[split.length - 1]));
//            model.addAttribute("question", quest);
//
//        }

    }

    @GetMapping("/question/shuffleAndReplay")
    public String shuffle(Model model){
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

        //===================================================================

        //학생들의 점수를 update
        List<Student> allStudentList = studentsService.findAllStudentList();
        allStudentList.forEach(student -> studentsService.resetStudentPoint(student.getId()));

        //===================================================================

        //type값 받아와서 model에 담기
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

    @GetMapping("/question/isAnswer")
    @ResponseBody
    public String isAnswer(@RequestParam("q_id")String id){
        Question quest = questionService.findById(Long.parseLong(id));

        if(quest.getCheckYn().equals("Y")) return "fail";
        else return "success";
    }

    //정답체크 팝업
    @GetMapping("/question/checkScore")
    public String checkScore(Model model, @RequestParam("q_id")String id){
        //현재 문제 번호를 model에 넣어줘야함
        model.addAttribute("q_id", id);
        List<Student> allStudentList = studentsService.findAllStudentList();

        model.addAttribute("studentList", allStudentList);
        return "question/checkScore";
    }

    //우승자 선정 팝업
    @GetMapping("/question/winner")
    public String winner(Model model){
        List<Student> students = studentsService.top3Student();
        List<Student> topList = new ArrayList<>(3);

        if(students.size() > 3){
            for(int i=0; i<3; i++){topList.add(students.get(i));}
            model.addAttribute("top3", topList);
        }else {
            model.addAttribute("top3", students);
        }

        return "question/final";
    }

    //중간 점검 팝업
    @GetMapping("/question/checkMedium")
    public String checkMedium(Model model){
        List<Student> allStudentList = studentsService.top3Student();

        model.addAttribute("studentList", allStudentList);
        return "question/checkMedium";
    }
}
