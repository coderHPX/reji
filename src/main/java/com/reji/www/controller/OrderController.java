package com.reji.www.controller;

import com.google.gson.internal.$Gson$Preconditions;
import com.reji.www.common.Result;
import com.reji.www.entity.Orders;
import com.reji.www.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return Result.success("支付成功");
    }
}
