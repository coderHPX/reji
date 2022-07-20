package com.reji.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.www.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    boolean add(ShoppingCart shoppingCart);
    boolean reduce(ShoppingCart shoppingCart);
}
