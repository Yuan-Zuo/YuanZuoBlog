package com.Myblog.demo.exception;

import com.Myblog.demo.domain.ResultDTO;
import com.Myblog.demo.result.CodeMsg;
import com.Myblog.demo.result.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//Controller增强器，作用是给Controller控制器添加统一的操作或处理
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    //拦截所有异常
    //HttpServletRequest 对象专门用于封装 HTTP 请求消息
    @ExceptionHandler(value = Exception.class)
    public Object exception(HttpServletRequest request,
                            Exception e, Model model,
                            HttpServletResponse response){
        e.printStackTrace();
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回json
            Result result = Result.error(CodeMsg.SERVER_ERROR);
            if(e instanceof GlobalException){
                GlobalException ex = (GlobalException) e;
                result = Result.error(ex.getCm());
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(result));
                writer.close();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
//      页面跳转
//      instanceof是Java中的二元运算符，左边是对象，右边是类；当对象是右边类或子类所创建对象时，返回true；否则，返回false。
        HttpStatus status = getStatus(request);
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            model.addAttribute("message",ex.getCm().getMsg());
        }else {
            model.addAttribute("message",CodeMsg.SERVER_ERROR);
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("java.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
