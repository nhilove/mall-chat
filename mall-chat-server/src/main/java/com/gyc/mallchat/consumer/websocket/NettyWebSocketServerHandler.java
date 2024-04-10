package com.gyc.mallchat.consumer.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.gyc.mallchat.consumer.websocket.domain.enums.WSReqTypeEnum;
import com.gyc.mallchat.consumer.websocket.domain.vo.req.WebSocketBaseReq;
import com.gyc.mallchat.consumer.websocket.service.WebSocketService;
import com.gyc.mallchat.consumer.websocket.utils.NettyUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: NettyWebSocketServerHandler
 * Package: com.gyc.mallchat.consumer.websocket
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/2 17:20
 * @Version 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //初次连接时会将websocketService初始化好放进容器中
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        //保存用户连接时的channel
        webSocketService.connect(ctx.channel());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //主动断开连接，用户下线
        userOffline(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StrUtil.isNotBlank(token)) {
                //登陆过，会生成token保存到本地，每次websocket请求会带上token,如果有直接进行认证
                webSocketService.authorize(ctx.channel(), token);
            }
            System.out.println("握手完成事件");
        } else if ((evt instanceof IdleStateEvent)) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("读空闲");
                // 用户被动下线
                userOffline(ctx.channel());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private void userOffline(Channel channel) {
        webSocketService.userOffline(channel);
        channel.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        //反序列化请求，根据请求类型处理不同的事情
        WebSocketBaseReq req = JSONUtil.toBean(text, WebSocketBaseReq.class);
        switch (WSReqTypeEnum.of(req.getType())) {
            case LOGIN://1是登录
                // 微信登录，生成二维码，返回给前端
                webSocketService.handlerLogin(ctx.channel());
                break;
            case AUTHORIZE://3是认证
                webSocketService.authorize(ctx.channel(), req.getData());
                break;
            case HEARTBEAT://2是心跳
                break;
        }

    }
}
