package com.gyc.mallchat.consumer.websocket.domain.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: WebSocketBaseResp
 * Package: com.gyc.mallchat.consumer.websocket.domain
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/3 11:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketBaseResp<T> {


    private Integer type;

    private T data;

}
