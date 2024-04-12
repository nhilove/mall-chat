package com.gyc.mallchat.consumer.websocket.service;

import com.gyc.mallchat.consumer.websocket.domain.vo.resp.WSBaseResp;
import io.netty.channel.Channel;

/**
 * ClassName: WebSocketService
 * Package: com.gyc.mallchat.consumer.websocket.service
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 14:36
 * @Version 1.0
 */
public interface WebSocketService {

    /**
     * 建立连接
     * @param channel
     */
    void connect(Channel channel);

    /**
     * 处理登录
     * @param channel
     */
    void handlerLogin(Channel channel);

    /**
     * 用户下线
     * @param channel
     */
    void userOffline(Channel channel);

    /**
     * 用户扫码登录成功
     * @param code
     * @param id
     */
    void scanLoginSuccess(Integer code, Long id);

    /**
     * 等待授权
     * @param code
     */
    void waitAuthorize(Integer code);

    /**
     * 授权验证
     * @param channel
     * @param data
     */
    void authorize(Channel channel, String data);

    /**
     * 给所有用户发消息
     * @param msg
     */
    void senMsgToAll(WSBaseResp<?> msg);
}
