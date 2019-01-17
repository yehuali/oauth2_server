package com.example.oauth2_server.handler;

import com.example.oauth2_server.handler.core.RouterContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

/**
 *  Action 分发器，收到 request 后不做实际业务处理，而是组装 action 并交给处理。
 */
public class ActionDispatcher extends ChannelInboundHandlerAdapter {

    private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    private static final String CONNECTION_CLOSE = "close";
    private static final String ACTION_SUFFIX = ".action";

    protected static RouterContext routerContext;
    private HttpRequest request;
    private FullHttpResponse response;
    private Channel channel;

    public ActionDispatcher(){

    }

    public void init(String configFilePath) throws Exception{
        if(configFilePath == null){
            configFilePath = "router.xml";
        }
        routerContext = new RouterContext(configFilePath);
    }


}
