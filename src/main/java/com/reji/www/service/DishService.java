package com.reji.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.www.dto.DishDto;
import com.reji.www.entity.Dish;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    Page<DishDto> pageDishDto(int page,int pageSize, String name);
    DishDto getDishDtoById(Long id);

    boolean update(DishDto dishDto);
}
