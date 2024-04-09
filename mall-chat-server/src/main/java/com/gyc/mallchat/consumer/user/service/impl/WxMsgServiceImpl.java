package com.gyc.mallchat.consumer.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.UserService;
import com.gyc.mallchat.consumer.user.service.WxMsgService;
import com.gyc.mallchat.consumer.user.service.adapter.TextBuilder;
import com.gyc.mallchat.consumer.user.service.adapter.UserAdapter;
import com.gyc.mallchat.consumer.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName: WxMsgServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 17:06
 * @Version 1.0
 */
@Service
@Slf4j
public class WxMsgServiceImpl implements WxMsgService {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Value( "${wx.mp.callback}" )
    private String callback;

    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     * openId和code关系映射表
     */
    private static final ConcurrentMap<String, Integer> WAITE_AUTHORIZE_MAP = new ConcurrentHashMap<>();

    /**
     * 扫码成功事件
     *
     * @param wxMpXmlMessage
     * @return
     */
    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        Integer code = getEventKey(wxMpXmlMessage);
        String openId = wxMpXmlMessage.getFromUser();

        //进行校验
        if (Objects.isNull(code)) {
            return null;
        }
        User user = userService.getUserByOpenId(openId);
        boolean registered = Objects.nonNull(user);
        boolean authorized = registered && StrUtil.isNotBlank(user.getAvatar());
        //注册过并且回填了用户信息
        if (registered && authorized) {
            //登陆成功逻辑，通过code找到channle，给用户推送消息
            webSocketService.scanLoginSuccess(code, user.getId());
            return null;
        }
        //没注册走注册逻辑
        if (!registered) {
            User insert = UserAdapter.buildUserSava(openId);
            userService.register(insert);
        }

        //没授权走授权逻辑，推送链接让用户授权
        WAITE_AUTHORIZE_MAP.put(openId, code);
        webSocketService.waitAuthorize(code);
        String authorizeUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        return TextBuilder.build("请点击登录: <a href=\"" + authorizeUrl + "\">登录</a>", wxMpXmlMessage);

    }

    /**
     * 用户授权回填信息
     *
     * @param userInfo
     */
    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User oldUser = userService.getUserByOpenId(openid);
        if (StrUtil.isBlank(oldUser.getAvatar())) {
            fillUserInfo(oldUser.getId(), userInfo);
        }
        //用户回填完信息，移除临时关系表中key
        Integer code = WAITE_AUTHORIZE_MAP.remove(openid);
        webSocketService.scanLoginSuccess(code, oldUser.getId());

    }

    /**
     * 填充用户信息
     *
     * @param uid
     * @param userInfo
     */
    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildUserAuthorized(uid, userInfo);
        //用户名不能重复
        userDao.updateById(user);
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try {
            String eventKey = wxMpXmlMessage.getEventKey();
            String key = eventKey.replace("qrscene_", "");
            return Integer.parseInt(key);
        } catch (Exception e) {
            log.error("getEventKey failed : {}", e.getMessage());
            return null;
        }
    }
}
