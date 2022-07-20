package com.reji.www.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reji.www.common.Result;
import com.reji.www.dto.DishDto;
import com.reji.www.entity.Dish;
import com.reji.www.entity.DishFlavor;
import com.reji.www.service.DishFlavorService;
import com.reji.www.service.DishService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/page")
    public Result page(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String name){
        Page<DishDto> dishDtoPage = dishService.pageDishDto(page, pageSize, name);

        return Result.success(dishDtoPage);
    }


    @PostMapping
    public Result save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return Result.success("新增成功！");
    }

    @GetMapping("/{id}")
    public Result queryDataById(@PathVariable Long id){
        DishDto dishDtoById = dishService.getDishDtoById(id);
        return Result.success(dishDtoById);
    }

    @PutMapping
    public Result update(@RequestBody DishDto dishDto){
        boolean update = dishService.update(dishDto);
        if (update) {
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    @PostMapping("/status/{status}")
    public Result statusHandle(@PathVariable Integer status,String ids){
        String[] split = ids.split(",");
        Long[] dishIds = new Long[split.length];
        for (int i = 0;i<split.length;i++) {
            dishIds[i] = Long.valueOf(split[i]);
        }
        LambdaUpdateWrapper<Dish> dishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        dishLambdaUpdateWrapper.in(Dish::getId,dishIds);
        dishLambdaUpdateWrapper.set(Dish::getStatus, status);

        boolean update = dishService.update(dishLambdaUpdateWrapper);
        if (update){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping
    public Result delete(String ids){
        String[] split = ids.split(",");
        ArrayList<Long> longs = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            longs.add(Long.valueOf(split[i]));
        }
        boolean b = dishService.removeByIds(longs);

        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.in(DishFlavor::getDishId,longs);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);

        if (b){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }


    @GetMapping("/list")
    public Result list(Long categoryId){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,categoryId);

        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        ArrayList<DishDto> dishDtos = new ArrayList<>();
        for (Dish dish : list) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
            List<DishFlavor> list1 = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(list1);
            dishDtos.add(dishDto);
        }

        return Result.success(dishDtos);
    }
}
