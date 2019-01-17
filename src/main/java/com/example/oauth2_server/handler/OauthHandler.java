package com.example.oauth2_server.handler;

import com.example.oauth2_server.protocol.ProtocolProcess;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 消息处理
 */
public class OauthHandler extends SimpleChannelInboundHandler<String> {
    private ProtocolProcess protocolProcess;

    public OauthHandler(ProtocolProcess protocolProcess) {
        this.protocolProcess = protocolProcess;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
}
