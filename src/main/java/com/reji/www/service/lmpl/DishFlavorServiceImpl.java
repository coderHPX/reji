package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.entity.DishFlavor;
import com.reji.www.mapper.DishFlavorMapper;
import com.reji.www.service.DishFlavorService;
import org.springframework.stereotype.Service;


@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
