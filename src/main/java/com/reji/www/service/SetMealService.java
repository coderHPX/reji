package com.reji.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.www.dto.SetMealDto;
import com.reji.www.entity.Dish;
import com.reji.www.entity.Setmeal;

public interface SetMealService extends IService<Setmeal> {
    boolean saveSetMealWithDish(SetMealDto setMealDto);
    Page<SetMealDto> page(int page, int pageSize, String name);

    SetMealDto querySetMealDtoById(Long id);
    boolean updateSetMeal(SetMealDto setMealDto);
    boolean deleteSetMealWithDish(String ids);
}
