package com.gyc.mallchat.consumer.user.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * ClassName: WxMsgService
 * Package: com.gyc.mallchat.consumer.user.service
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 17:05
 * @Version 1.0
 */
public interface WxMsgService {

    /**
     * 扫码成功事件
     * @param wxMpXmlMessage
     * @return
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage);

    /**
     * 用户授权回填信息
     * @param userInfo
     */
    void authorize(WxOAuth2UserInfo userInfo);
}
