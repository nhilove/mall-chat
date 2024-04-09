package com.gyc.mallchat.consumer.websocket.domain.vo.req;

import lombok.Data;

/**
 * ClassName: WebSocketBaseReq
 * Package: com.gyc.mallchat.consumer.websocket.domain
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/3 11:01
 * @Version 1.0
 */
@Data
public class WebSocketBaseReq {


    private Integer type;

    private String data;
}
