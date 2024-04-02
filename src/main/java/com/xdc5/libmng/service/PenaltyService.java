package com.xdc5.libmng.service;


import com.xdc5.libmng.mapper.PenaltyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PenaltyService {
    @Autowired
    private PenaltyMapper penaltyMapper;
    //插入新的处分信息
    public void insertNewPenalty(Integer adminId,Integer userId,String reason,String endDate){
        penaltyMapper.insertNewPenalty(adminId,userId,reason,endDate);
    }
    //将adminid和useid确定下的Penalty的reason和endDate信息进行更新，同时要在动态SQl中更新处分时间。
    public void updatePenaltyById(Integer adminId,Integer userId,String reason,String endDate){
        penaltyMapper.updatePenaltyById(adminId,userId,reason,endDate);
    }
}
