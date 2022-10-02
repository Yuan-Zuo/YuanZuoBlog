package com.xdoj.demo.exception;

import com.xdoj.demo.result.CodeMsg;
import com.xdoj.demo.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

//Controller增强器，作用是给Controller控制器添加统一的操作或处理
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    //拦截所有异常
    //HttpServletRequest 对象专门用于封装 HTTP 请求消息
    @ExceptionHandler(value = Exception.class)
    public Result<String> exception(HttpServletRequest request, Exception e){
        e.printStackTrace();
//      instanceof是Java中的二元运算符，左边是对象，右边是类；当对象是右边类或子类所创建对象时，返回true；否则，返回false。
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            System.out.println("GlobalException");
            return Result.error(ex.getCm());
        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            List<ObjectError> errors =  ex.getAllErrors();
            ObjectError error = errors.get(0);

            String msg = error.getDefaultMessage();

            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
