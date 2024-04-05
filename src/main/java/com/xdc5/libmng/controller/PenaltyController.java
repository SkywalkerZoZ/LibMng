package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Penalty;
import com.xdc5.libmng.entity.PenaltyDetail;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.PenaltyService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

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
        String endDateStr=(String)requestBody.get("endDate");
        Integer adminId = (Integer) request.getAttribute("userId");
        LocalDate endDate= DateTimeUtils.strToDate(endDateStr,"yyyy-MM-dd");
        if (endDate ==null)
        {
            return Result.error("Fail: invalid date");
        }
        //首先查看用户的borrowPerms，如果已经是0了，那么找到对他的处分，更新处分结束日期
        //如果查看到用户的borrowPerms不是0，也就是说还没有被处分，则立刻处分，并且创建新的处分元素
        //首先如果没有这个Id报错
        if(userService.getUserInfo(userId)==null){
            return Result.error("Fail: no such user");
        }
        if(userService.checkPermsByID(userId)){
            //说明是1，1表示正常，立即处分，创建新的处分元素
            //处分
            userService.changeReasderPerms(userId);
            //插入新的处分元素
            penaltyService.insertPenalty(adminId,userId,reason,endDate);
        }else{
            //说明是0,0表示更新处分信息。
            penaltyService.updatePenaltyById(adminId,userId,reason,endDate);
        }
        return Result.success("Success: put /admin/penalty/{userId}");
    }

    @GetMapping("/user/penalty")
    public Result getPenalty(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<PenaltyDetail> penalties = penaltyService.getPenaltyByUserId(userId);
        return Result.success(penalties,"Success: get /user/penalty");
    }


}
