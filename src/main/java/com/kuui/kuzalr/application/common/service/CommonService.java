package com.kuui.kuzalr.application.common.service;

import com.kuui.kuzalr.application.common.domain.TYPECHECK;
import com.kuui.kuzalr.application.common.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonService {
    private final CommonRepository commonRepository;

    public TYPECHECK findQNumByType(String type){
        return commonRepository.findQNumByType(type);
    }

    public void updateQNumList(String type, String qNum){
        TYPECHECK typecheck = commonRepository.findQNumByType(type);

        String qNumList = typecheck.getQNum();
        if(qNumList.equals("")){
            commonRepository.updateQNumList(type, qNum);
        }else {
            commonRepository.updateQNumList(type, qNumList+","+qNum);
        }
    }
}
