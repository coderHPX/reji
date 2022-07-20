package com.reji.www.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandle {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandle(SQLIntegrityConstraintViolationException ex){
        if (ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
            return Result.error("用户名"+s[2]+"已存在！");
        }
        return Result.error("操作失败");
    }


    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandle(CustomException ex){

        return Result.error(ex.getMessage());
    }

}