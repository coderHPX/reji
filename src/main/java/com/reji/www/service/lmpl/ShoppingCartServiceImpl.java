package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.common.BaseContext;
import com.reji.www.entity.Dish;
import com.reji.www.entity.Setmeal;
import com.reji.www.entity.ShoppingCart;
import com.reji.www.mapper.ShoppingCartMapper;
import com.reji.www.service.DishService;
import com.reji.www.service.SetMealService;
import com.reji.www.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private DishService dishService;

    private SetMealService setMealService;

    @Override
    @Transactional
    public boolean add(ShoppingCart shoppingCart) {
        // 获取userId
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        //判断是套餐还是菜品
        if (shoppingCart.getDishId()==null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
;
        }
        else {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        }

        ShoppingCart one = this.getOne(shoppingCartLambdaQueryWrapper);
        if (one==null){
            shoppingCart.setUserId(currentId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            boolean save = this.save(shoppingCart);
            return save;
        }
        one.setNumber(one.getNumber()+1);



        return this.updateById(one);
    }

    @Override
    public boolean reduce(ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        //判断是套餐还是菜品
        if (shoppingCart.getDishId()==null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            ;
        }
        else {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        }
        ShoppingCart one = this.getOne(shoppingCartLambdaQueryWrapper);

        if (one.getNumber() == 1){
            boolean remove = this.remove(shoppingCartLambdaQueryWrapper);
            return remove;
        }

        one.setNumber(one.getNumber()-1);
        boolean b = this.updateById(one);


        return b;
    }
}
