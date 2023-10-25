package com.kuui.kuzalr.application.questions.service;

import com.kuui.kuzalr.application.questions.domain.Question;
import com.kuui.kuzalr.application.questions.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;
    public int questionCntByType(String type){
        return questionRepository.questionCntByType(type);
    }
    public Question findById(Long id) {return questionRepository.findById(id);}
    @Transactional
    public Long questIdListByType(String type){

        //type에 맞는 id리스트 가져오기 (useYn = n인것만)
        List<Long> idList = questionRepository.questIdListByType(type);

        //랜덤 문자 뽑기
        Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
        int i = random.nextInt(idList.size());

        //뽑힌 인덱스 데이터 useYn Y처리
        Question question = questionRepository.findById(idList.get(i));
        question.setUseYn("Y");

        return idList.get(i);
    }

    @Transactional
    public Long updateCheckYn(Long id) {return  questionRepository.updateCheckYn(id);}

    @Transactional
    public Long shuffleQuest(Long id, String type){return questionRepository.shuffleQuest(id, type);}
}
