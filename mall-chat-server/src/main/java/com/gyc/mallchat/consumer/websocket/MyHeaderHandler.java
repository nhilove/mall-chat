package com.gyc.mallchat.consumer.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import com.gyc.mallchat.consumer.websocket.utils.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Optional;

/**
 * Description:
 * Date: 2024-04-10
 */
public class MyHeaderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final HttpObject httpObject = (HttpObject) msg;

        if (httpObject instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.getUri());
            urlBuilder.getQuery().get("token");
            Optional<String> optionalToken = Optional.of(urlBuilder)
                    .map(UrlBuilder::getQuery)
                    .map(k -> k.get("token"))
                    .map(CharSequence::toString);
            optionalToken.ifPresent(s -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, s));
            //重新设置不带token参数的websocket请求路径
            request.setUri(urlBuilder.getPath().toString());
        }
        ctx.fireChannelRead(msg);
    }
}
