package com.reji.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reji.www.common.Result;
import com.reji.www.dto.SetMealDto;
import com.reji.www.entity.SetMealDish;
import com.reji.www.entity.Setmeal;
import com.reji.www.service.SetMealDishService;
import com.reji.www.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;
    @Autowired
    private SetMealDishService setMealDishService;

    @PostMapping
    public Result save(@RequestBody SetMealDto setMealDto){
        boolean b = setMealService.saveSetMealWithDish(setMealDto);
        if (b){
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    @GetMapping("/page")
    public Result page(@RequestParam int page,@RequestParam int pageSize, @RequestParam(required = false) String name){
        Page<SetMealDto> page1 = setMealService.page(page, pageSize, name);
        return Result.success(page1);
    }

    @GetMapping("/{id}")
    public Result querySetMealDto(@PathVariable Long id){
        return Result.success(setMealService.querySetMealDtoById(id));
    }

    @PutMapping
    public Result editSetMeal(@RequestBody SetMealDto setMealDto){
        boolean b = setMealService.updateSetMeal(setMealDto);
        if (b){
            return Result.success("修改成功！");
        }
        return Result.error("修改失败!");
    }

    @PostMapping("/status/{status}")
    public Result statusHandle(@PathVariable Integer status,String ids){
        String[] split = ids.split(",");
        ArrayList<Long> longs = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            longs.add(Long.valueOf(split[i]));
        }
        LambdaUpdateWrapper<Setmeal> setmealLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealLambdaUpdateWrapper.in(Setmeal::getId,longs);
        setmealLambdaUpdateWrapper.set(Setmeal::getStatus,status);

        boolean update = setMealService.update(setmealLambdaUpdateWrapper);

        if (update){
            return Result.success("修改成功！");
        }
        return Result.error("修改失败");

    }

    @DeleteMapping
    public Result delete(String ids){
        boolean b = setMealService.deleteSetMealWithDish(ids);
        if (b){
            return Result.success("删除成功!");
        }
        return Result.error("删除失败！");
    }

    @GetMapping("/list")
    public Result list(Long categoryId){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,categoryId)
                                 .eq(Setmeal::getStatus,1);
        List<Setmeal> list = setMealService.list(setmealLambdaQueryWrapper);
        return Result.success(list);
    }
}
