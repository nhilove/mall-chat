package com.gyc.mallchat.consumer.websocket.service.adapter;

import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.websocket.domain.enums.WSRespTypeEnum;
import com.gyc.mallchat.consumer.websocket.domain.vo.resp.WebSocketBaseResp;
import com.gyc.mallchat.consumer.websocket.ws.WSLoginSuccess;
import com.gyc.mallchat.consumer.websocket.ws.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * ClassName: WebSocketAdapter
 * Package: com.gyc.mallchat.consumer.websocket.service.adapter
 * Description: websocket发送消息的桥梁，适配器
 *
 * @Author gyc
 * @Create 2024/4/9 15:10
 * @Version 1.0
 */
public class WebSocketAdapter {

    public static WebSocketBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WebSocketBaseResp<WSLoginUrl> res = new WebSocketBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_URL.getType());
        res.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return res;
    }

    public static WebSocketBaseResp<?> buildResp(String token, User user) {
        WebSocketBaseResp<WSLoginSuccess> res = new WebSocketBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess wsLoginSuccess = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .uid(user.getId())
                .token(token)
                .build();
        res.setData(wsLoginSuccess);
        return res;
    }

    public static WebSocketBaseResp<?> buildWaitAuthorizeResp() {
        WebSocketBaseResp<WSLoginUrl> res = new WebSocketBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return res;
    }

    public static WebSocketBaseResp<?> buildInvalidTokenResp() {
        WebSocketBaseResp<WSLoginUrl> res = new WebSocketBaseResp<>();
        res.setType(WSRespTypeEnum.INVALIDATE_TOKEN.getType());
        return res;
    }
}
