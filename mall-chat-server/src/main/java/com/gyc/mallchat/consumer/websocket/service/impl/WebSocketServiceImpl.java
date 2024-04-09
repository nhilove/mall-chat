package com.gyc.mallchat.consumer.websocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.LoginService;
import com.gyc.mallchat.consumer.websocket.domain.dto.WebSocketExtraDto;
import com.gyc.mallchat.consumer.websocket.domain.vo.resp.WebSocketBaseResp;
import com.gyc.mallchat.consumer.websocket.service.WebSocketService;
import com.gyc.mallchat.consumer.websocket.service.adapter.WebSocketAdapter;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: WebSocketServiceImpl
 * Package: com.gyc.mallchat.consumer.websocket.service.impl
 * Description: 专门管理websocket连接地业务
 *
 * @Author gyc
 * @Create 2024/4/9 14:36
 * @Version 1.0
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    @Lazy
    private WxMpService wxMpService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginService loginService;

    /**
     * 保存所有连接用户的状态（登录、游客）
     */
    private static final ConcurrentHashMap<Channel, WebSocketExtraDto> ONLINE_WS_MAP = new ConcurrentHashMap<>();


    public static final int MAXIMUM_SIZE = 1000;
    public static final Duration DURATION = Duration.ofHours(1);
    /**
     * code和连接的临时关系映射表
     */
    private static final Cache<Integer, Channel> WAITE_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WebSocketExtraDto());
    }


    @Override
    public void userOffline(Channel channel) {
        //从channel管理集合中移除，避免OOM
        ONLINE_WS_MAP.remove(channel);
        // todo 用户下线的通知消息

    }

    /**
     * 用户扫码登录成功
     *
     * @param code
     * @param id
     */
    @Override
    public void scanLoginSuccess(Integer code, Long id) {
        Channel channel = WAITE_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            return;
        }
        User user = userDao.getById(id);
        WAITE_LOGIN_MAP.invalidate(code);
        //返回token信息
        String token = loginService.login(id);
        //用户登录
        sendMessage(channel, WebSocketAdapter.buildResp(token, user));

    }

    /**
     * 等待授权
     *
     * @param code
     */
    @Override
    public void waitAuthorize(Integer code) {
        Channel channel = WAITE_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            return;
        }
        sendMessage(channel, WebSocketAdapter.buildWaitAuthorizeResp());
    }

    @SneakyThrows
    @Override
    public void handlerLogin(Channel channel) {
        //生成随即code,保存code和channel的临时关系
        Integer code = generatorCode(channel);
        //找微信生成带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        //发消息给前端
        sendMessage(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    /**
     * 发送任意类型的消息给前端
     *
     * @param channel
     * @param res
     */
    private void sendMessage(Channel channel, WebSocketBaseResp<?> res) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(res)));
    }

    /**
     * 生成code,保存code和channel的临时关系
     *
     * @param channel
     * @return
     */
    private Integer generatorCode(Channel channel) {
        Integer code;
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (Objects.nonNull(WAITE_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }


}
