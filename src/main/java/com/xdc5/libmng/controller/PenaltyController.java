package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Penalty;
import com.xdc5.libmng.entity.PenaltyDetail;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.PenaltyService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
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
        BigDecimal money = new BigDecimal(requestBody.get("money").toString());
        Integer adminId = (Integer) request.getAttribute("userId");
        //首先如果没有这个Id报错
        if (!Objects.equals(userService.getUserInfo(adminId).getUserRole(), "admin")){
            return Result.error("Fail: no permission");
        }
        if(userService.getUserInfo(userId)==null){
            return Result.error("Fail: no such user");
        }

        User user = userService.getUserInfo(userId);
        BigDecimal currentMoney = new BigDecimal(user.getMoney().toString());
        BigDecimal updatedMoney = currentMoney.subtract(money);

        if(updatedMoney.compareTo(BigDecimal.ZERO) < 0){
            return Result.error("Fail: not enough money");
        }

        penaltyService.addPenalty(adminId, userId, reason, money);
        user.setMoney(updatedMoney);
        userService.updateUserInfo(user);
        return Result.success("Success: put /admin/penalty/{userId}");
    }

    @GetMapping("/user/penalty")
    public Result getPenalty(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<PenaltyDetail> penalties = penaltyService.getPenaltyByUserId(userId);
        return Result.success(penalties,"Success: get /user/penalty");
    }


}
