package com.gyc.mallchat.consumer.websocket.service.adapter;

import com.gyc.mallchat.consumer.common.domain.enums.YesOrNoEnum;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.websocket.domain.enums.WSRespTypeEnum;
import com.gyc.mallchat.consumer.websocket.domain.vo.resp.WSBaseResp;
import com.gyc.mallchat.consumer.websocket.ws.WSBlack;
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

    public static WSBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBaseResp<WSLoginUrl> res = new WSBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_URL.getType());
        res.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return res;
    }

    public static WSBaseResp<?> buildResp(String token, User user) {
        WSBaseResp<WSLoginSuccess> res = new WSBaseResp<>();
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

    public static WSBaseResp<?> buildResp(String token, User user, boolean power) {
        WSBaseResp<WSLoginSuccess> res = new WSBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess wsLoginSuccess = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .uid(user.getId())
                .power(power ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus())
                .token(token)
                .build();
        res.setData(wsLoginSuccess);
        return res;
    }

    public static WSBaseResp<?> buildWaitAuthorizeResp() {
        WSBaseResp<WSLoginUrl> res = new WSBaseResp<>();
        res.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return res;
    }

    public static WSBaseResp<?> buildInvalidTokenResp() {
        WSBaseResp<WSLoginUrl> res = new WSBaseResp<>();
        res.setType(WSRespTypeEnum.INVALIDATE_TOKEN.getType());
        return res;
    }

    public static WSBaseResp<?> buildBlackResp(User user) {
        WSBaseResp<WSBlack> res = new WSBaseResp<>();
        res.setType(WSRespTypeEnum.BLACK.getType());
        WSBlack wsBlack = WSBlack.builder()
                .uid(user.getId())
                .build();
        res.setData(wsBlack);
        return res;
    }
}
