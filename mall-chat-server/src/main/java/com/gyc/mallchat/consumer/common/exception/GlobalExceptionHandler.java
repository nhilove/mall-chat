package com.gyc.mallchat.consumer.common.exception;

import com.gyc.mallchat.consumer.common.domain.vo.res.ApiResult;
import com.gyc.mallchat.consumer.common.exception.enums.CommonErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.gyc.mallchat.consumer.common.exception
 * Description: 全局异常处理器
 *
 * @Author gyc
 * @Create 2024/4/11 10:29
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler( value = MethodArgumentNotValidException.class )
    public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(ex -> sb.append(ex.getField()).append(ex.getDefaultMessage()).append(","));
        return ApiResult.fail(CommonErrorEnum.PARAMS_INVALID.getErrorCode(), sb.toString().substring(0, sb.length() - 1));
    }

    @ExceptionHandler( value = BusinessException.class )
    public ApiResult<?> businessException(BusinessException e) {
        log.info("Business Exception, the reason is : {}", e.getMessage());
        return ApiResult.fail(e.errorCode, e.getErrorMsg());
    }


    @ExceptionHandler( value = Exception.class )
    public ApiResult<?> exception(Exception e) {
        log.error("System error,the reason is : {},", e.getMessage());
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

}
