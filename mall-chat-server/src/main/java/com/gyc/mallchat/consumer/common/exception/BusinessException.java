package com.gyc.mallchat.consumer.common.exception;

import com.gyc.mallchat.consumer.common.exception.enums.CommonErrorEnum;
import lombok.Data;

/**
 * ClassName: BusinessException
 * Package: com.gyc.mallchat.consumer.common.exception
 * Description: 自定义业务异常
 *
 * @Author gyc
 * @Create 2024/4/11 10:44
 * @Version 1.0
 */
@Data
public class BusinessException extends RuntimeException {

    public Integer errorCode;

    public String errorMsg;


    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(CommonErrorEnum error) {
        super(error.getErrorMsg());
        this.errorCode = error.getErrorCode();
        this.errorMsg = error.getErrorMsg();
    }

}
