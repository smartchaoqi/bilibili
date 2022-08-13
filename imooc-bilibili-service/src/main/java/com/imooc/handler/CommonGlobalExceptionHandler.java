package com.imooc.handler;

import com.imooc.domain.JsonResponse;
import com.imooc.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
//优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request, Exception exception){
        String msg = exception.getMessage();
        if (exception instanceof ConditionException){
            ConditionException conditionException=(ConditionException) exception;
            String code = conditionException.getCode();
            return new JsonResponse<>(code,msg);
        }else{
            return new JsonResponse<>("500",msg);
        }
    }

}
