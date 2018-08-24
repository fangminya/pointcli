package com.foodchain.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Author: yuanZ
 * @Date: 2018/7/24 15:56
 * @Description: 处理全局异常
**/
@ControllerAdvice
public class MyExceptionResolver {

    @ExceptionHandler(value = UnauthorizedException.class)
    public String unauthorizedException (UnauthorizedException e) {
        return "error/403";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundException(NoHandlerFoundException e) {
        return "error/404";
    }
}
