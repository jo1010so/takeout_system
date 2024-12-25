package com.jojo.exception.handler;

import com.jojo.constant.StatusEnum;
import com.jojo.exception.BusinessException;
import com.jojo.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(BusinessException.class)
    public Result<?> businessException(BusinessException e){
        log.error(e.getMessage());
        return Result.fail(StatusEnum.OPERATION_FAIL.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeException(RuntimeException e){
        log.error(e.getMessage());
        return Result.fail(StatusEnum.SERVER_INNER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> accessDeniedException(AccessDeniedException e){
        log.error(e.getMessage());
        throw e;
    }

}
