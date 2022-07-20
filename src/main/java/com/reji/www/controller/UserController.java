package com.reji.www.controller;


import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reji.www.common.Result;
import com.reji.www.entity.User;
import com.reji.www.service.UserService;
import com.reji.www.utils.SMSUtils;
import com.reji.www.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody User user) throws ClientException {
        String phone = user.getPhone();
        String s = String.valueOf(ValidateCodeUtils.generateValidateCode(6));

//        SMSUtils.sendMessage(phone,s);

//        request.getSession().setAttribute("code",s);
        redisTemplate.opsForValue().set("code",s,5, TimeUnit.MINUTES);

        System.out.println("短信验证码==============="+s);

    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request,@RequestBody Map map){
        String code = (String) map.get("code");
//        String codeSession = (String) request.getSession().getAttribute("code");
        String codeSession = (String) redisTemplate.opsForValue().get("code");

        if (!code.equals(codeSession)){
            return Result.error("验证码错误");
        }
        if (map.get("phone")==null){
            return Result.error("请输入手机号");
        }
        User user = new User();
        user.setPhone((String) map.get("phone"));
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());

        User one = userService.getOne(userLambdaQueryWrapper);
        if (one == null){
            userService.save(user);
        }

        request.getSession().setAttribute("userFrontId",one.getId());


        return Result.success("登录成功！");

    }

}
