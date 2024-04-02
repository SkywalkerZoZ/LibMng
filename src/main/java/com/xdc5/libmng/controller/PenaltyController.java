package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.PenaltyService;
import com.xdc5.libmng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
public class PenaltyController {
    @Autowired
    UserService userService;
    //处分读者
    @Autowired
    PenaltyService penaltyService;

    @Transactional
    @PutMapping("/admin/penalty/{userId}")
    public Result punishReader(HttpServletRequest request, @PathVariable Integer userId, @RequestBody HashMap<String, Object> requestBody){
        String reason=(String)requestBody.get("reason");
        String endDate=(String)requestBody.get("endDate");
        Integer adminId = (Integer) request.getAttribute("userId");
        //首先查看用户的borrowPerms，如果已经是0了，那么找到对他的处分，更新处分结束日期
        //如果查看到用户的borrowPerms不是0，也就是说还没有被处分，则立刻处分，并且创建新的处分元素
        //首先如果没有这个Id报错
        if(userService.getUserInfo(userId)==null){
            return Result.error("Fail: bad request");
        }
        if(userService.checkPermsByID(userId)){
            //说明是1，1表示正常，立即处分，创建新的处分元素
            //处分
            userService.changeReasderPerms(userId);
            //插入新的处分元素
            penaltyService.insertNewPenalty(adminId,userId,reason,endDate);
        }else{
            //说明是0,0表示更新处分信息。
            penaltyService.updatePenaltyById(adminId,userId,reason,endDate);
        }
        return Result.success("Success: put /admin/penalty/{userId}");
    }

}
