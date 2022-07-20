package com.reji.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reji.www.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
