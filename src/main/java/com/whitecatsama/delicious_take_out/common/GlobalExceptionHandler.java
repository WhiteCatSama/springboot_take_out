package com.whitecatsama.delicious_take_out.common;

import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


/*
* 全局异常处理
* */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //    异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        String[] split = ex.getMessage().split(" ");
        if((split[0]+" "+split[1]).contains("Duplicate entry")){
          return new Result(Code.SAVE_FAILED,split[2]+"已存在,新增失败");
        }
        return new Result(Code.SAVE_FAILED,"未知错误");
    }
}
