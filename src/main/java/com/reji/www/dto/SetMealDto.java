package com.reji.www.dto;

import com.reji.www.entity.SetMealDish;
import com.reji.www.entity.Setmeal;
import lombok.Data;

import java.util.List;

@Data
public class SetMealDto extends Setmeal {
    private List<SetMealDish> setmealDishes;
    private String categoryName;

}
