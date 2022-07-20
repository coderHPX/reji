package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.common.CustomException;
import com.reji.www.entity.Category;
import com.reji.www.entity.Dish;
import com.reji.www.entity.Setmeal;
import com.reji.www.mapper.CategoryMapper;
import com.reji.www.service.CategoryService;
import com.reji.www.service.DishService;
import com.reji.www.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;


    @Autowired
    private SetMealService setMealService;


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public boolean remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("当下分类关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setMealService.count(setmealLambdaQueryWrapper);

        if (count1 >0){
            throw new CustomException("当下分类关联了套餐，不能删除");
        }



        return super.removeById(id);
    }
}
