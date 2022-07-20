package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.common.CustomException;
import com.reji.www.dto.SetMealDto;
import com.reji.www.entity.SetMealDish;
import com.reji.www.entity.Setmeal;
import com.reji.www.mapper.SetmealMapper;
import com.reji.www.service.CategoryService;
import com.reji.www.service.SetMealDishService;
import com.reji.www.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetMealService {
    @Autowired
    private SetMealDishService setMealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public boolean saveSetMealWithDish(SetMealDto setMealDto) {
        boolean saveSetMeal = this.save(setMealDto);

        List<SetMealDish> setmealDishes = setMealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setMealDto.getId());
            return item;
        }).collect(Collectors.toList());

        boolean b = setMealDishService.saveBatch(setmealDishes);


        return saveSetMeal && b;
    }

    @Override
    public Page<SetMealDto> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetMealDto> setMealDtoPage = new Page<>();


        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.isNotBlank(name),Setmeal::getName,name);

        this.page(setmealPage,setmealLambdaQueryWrapper);

        BeanUtils.copyProperties(setmealPage,setMealDtoPage,"records");

        List<SetMealDto> setMealDtos = new ArrayList<>();
        setMealDtos = setmealPage.getRecords().stream().map((item)->{
            SetMealDto setMealDto = new SetMealDto();

            BeanUtils.copyProperties(item,setMealDto);
            setMealDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());

            return setMealDto;
        }).collect(Collectors.toList());

        setMealDtoPage.setRecords(setMealDtos);
        return setMealDtoPage;
    }

    @Override
    public SetMealDto querySetMealDtoById(Long id) {
        Setmeal byId = this.getById(id);

        LambdaQueryWrapper<SetMealDish> setMealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealDishLambdaQueryWrapper.eq(SetMealDish::getSetmealId,id);
        List<SetMealDish> list = setMealDishService.list(setMealDishLambdaQueryWrapper);

        SetMealDto setMealDto = new SetMealDto();

        BeanUtils.copyProperties(byId,setMealDto);

        setMealDto.setSetmealDishes(list);

        return setMealDto;
    }

    @Override
    @Transactional
    public boolean updateSetMeal(SetMealDto setMealDto) {
        boolean b = this.updateById(setMealDto);

        List<SetMealDish> setmealDishes = setMealDto.getSetmealDishes();

        LambdaQueryWrapper<SetMealDish> setMealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealDishLambdaQueryWrapper.eq(SetMealDish::getSetmealId,setMealDto.getId());
        setMealDishService.remove(setMealDishLambdaQueryWrapper);

        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setMealDto.getId());
            return item;
        }).collect(Collectors.toList());
        boolean b1 = setMealDishService.saveBatch(setmealDishes);

        return b&&b1;
    }

    @Override
    @Transactional
    public boolean deleteSetMealWithDish(String ids) {
        String[] split = ids.split(",");
        ArrayList<Long> longs = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            longs.add(Long.valueOf(split[i]));
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,longs);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(setmealLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("套餐正在销售，请停售后，再删除！！");
        }

        boolean b = this.removeByIds(longs);

        LambdaQueryWrapper<SetMealDish> setMealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealDishLambdaQueryWrapper.in(SetMealDish::getSetmealId,longs);
        boolean remove = setMealDishService.remove(setMealDishLambdaQueryWrapper);
        return b&&remove;
    }
}
