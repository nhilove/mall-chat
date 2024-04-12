package com.gyc.mallchat.consumer.common.exception.enums;

import cn.hutool.http.ContentType;
import com.gyc.mallchat.consumer.common.domain.vo.res.ApiResult;
import com.gyc.mallchat.consumer.common.utils.JsonUtils;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: HttpErrorEnum
 * Package: com.gyc.mallchat.consumer.common.exception.enums
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 20:50
 * @Version 1.0
 */
@AllArgsConstructor
public enum HttpErrorEnum {
    ACCESS_DENIED(401, "登录失效，请重新登录"),
    ;

    private Integer httpCode;
    private String desc;

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(httpCode);
        response.setContentType(ContentType.JSON.toString(StandardCharsets.UTF_8));
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode, desc)));
    }
}
