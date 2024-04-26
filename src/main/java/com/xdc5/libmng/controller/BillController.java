package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Bill;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class BillController {
    @Autowired
    BillService billService;
    @GetMapping("/bill")
    public Result getUserBill(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<Bill> userBillList = billService.getBillByUserId(userId);
        return Result.success(userBillList,"Success: get /bill");
    }
}
