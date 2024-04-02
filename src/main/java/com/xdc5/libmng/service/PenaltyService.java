package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.Penalty;
import com.xdc5.libmng.mapper.PenaltyMapper;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class PenaltyService {
    @Autowired
    private PenaltyMapper penaltyMapper;
    //插入新的处分信息
    public void insertPenalty(Integer adminId, Integer userId, String reason, LocalDate endDate){
        Penalty penalty=new Penalty();
        penalty.setAdminId(adminId);
        penalty.setUserId(userId);
        penalty.setReason(reason);
        penalty.setEndDate(endDate);
        penaltyMapper.addPenalty(penalty);
    }
    //将adminId和userId确定下的Penalty的reason和endDate信息进行更新，同时要在动态SQl中更新处分时间。
    public void updatePenaltyById(Integer adminId,Integer userId,String reason,LocalDate endDate){
        Penalty penalty=new Penalty();
        penalty.setAdminId(adminId);
        penalty.setUserId(userId);
        penalty.setReason(reason);
        penalty.setEndDate(endDate);
        penaltyMapper.updatePenalty(penalty);
    }
}
