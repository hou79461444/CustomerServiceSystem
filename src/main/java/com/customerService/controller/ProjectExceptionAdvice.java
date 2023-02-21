package com.customerService.controller;

import com.customerService.exception.BusinessException;
import com.customerService.exception.SystemException;
import com.customerService.util.Code;
import com.customerService.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 全局异常处理器类（集中统一处理项目中出现的异常）
// 异常处理器方法（拦截并处理异常，一种异常处理方法只能处理一种类型的异常）
@RestControllerAdvice
@Slf4j
public class ProjectExceptionAdvice {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
        log.error("BusinessException! code:{}, msg:{}, data:{}", ex.getCode(), ex.getMessage(), ex.getData());
        return new Result(ex.getCode(), ex.getMessage(), ex.getData());
    }

    // 处理系统异常
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
        log.error("SystemException! msg: {}", ex.getMessage());
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    // 处理其它异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        log.error("UnKnowException! msg: {}", ex.getMessage());
        return new Result(Code.SYSTEM_UNKNOW_ERR, "系统繁忙，请稍后再试", null);
    }
}


