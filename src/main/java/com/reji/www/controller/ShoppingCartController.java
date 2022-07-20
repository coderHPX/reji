package com.reji.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reji.www.common.BaseContext;
import com.reji.www.common.Result;
import com.reji.www.entity.ShoppingCart;
import com.reji.www.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;



    @GetMapping("/list")
    public Result list(){
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);

    }

    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCart shoppingCart){
        boolean add = shoppingCartService.add(shoppingCart);
        if (add){
            return Result.success("添加成功！");
        }
        return Result.error("添加失败！");


    }

    @PostMapping("/sub")
    public Result reduce(@RequestBody ShoppingCart shoppingCart){
        if (shoppingCartService.reduce(shoppingCart)){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/clean")
    public Result delete(){
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        boolean remove = shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        if (remove){
            return Result.success("清空成功");
        }
        return Result.error("清空失败");

    }
}
