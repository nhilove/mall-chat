package com.gyc.mallchat.consumer.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RequestInfo
 * Package: com.gyc.mallchat.consumer.common.domain.dto
 * Description: 请求上线文对象
 *
 * @Author gyc
 * @Create 2024/4/10 21:20
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfo {

    private Long uid;

    private String ip;
}
