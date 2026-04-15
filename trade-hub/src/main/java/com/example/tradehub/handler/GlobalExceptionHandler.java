package com.example.tradehub.handler;


import com.example.tradehub.exception.BaseException;
import com.example.tradehub.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//告诉 Spring 这是一个全局异常处理器，自动拦截所有 Controller 抛出的异常，回结果会自动序列化为 JSON
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
