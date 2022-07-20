package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.entity.User;
import com.reji.www.mapper.UserMapper;
import com.reji.www.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
