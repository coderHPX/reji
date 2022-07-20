package com.reji.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.www.entity.Category;

public interface CategoryService extends IService<Category> {
    boolean remove(Long id);
}
