package com.reji.www.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reji.www.common.Result;
import com.reji.www.entity.Category;
import com.reji.www.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result page(int page,int pageSize){
        Page<Category> categoryPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryService.page(categoryPage,categoryLambdaQueryWrapper);
        return Result.success(page1);

    }

    @PostMapping
    public Result saveResult(@RequestBody Category category){
        boolean save = categoryService.save(category);

        if (save){
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }


    @DeleteMapping
    public Result delete(String ids){
        Long aLong = Long.valueOf(ids);
        boolean b = categoryService.remove(aLong);
        if (b){
            return Result.success(null);
        }
        return Result.error("删除失败");
    }

    @PutMapping
    public Result update(@RequestBody Category category){
        boolean b = categoryService.updateById(category);
        if (b){
            return Result.success("修改成功！");
        }
        return Result.error("修改失败！");
    }

    @GetMapping("/list")
    public Result listCategory(@RequestParam(required = false) Integer type){
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(StringUtils.checkValNotNull(type),Category::getType,type);
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return Result.success(list);
    }






}
