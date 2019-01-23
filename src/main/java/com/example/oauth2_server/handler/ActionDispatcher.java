package com.example.oauth2_server.handler;

import com.example.oauth2_server.config.Oauth2ServerProperties;
import com.example.oauth2_server.handler.core.Return;
import com.example.oauth2_server.handler.core.RouterContext;
import com.example.oauth2_server.handler.core.config.ActionWrapper;
import com.example.oauth2_server.handler.core.invocation.ActionProxy;
import com.example.oauth2_server.handler.core.util.HttpReaderUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  Action 分发器，收到 request 后不做实际业务处理，而是组装 action 并交给处理。
 */
//@Component
//@ChannelHandler.Sharable
public class ActionDispatcher extends ChannelInboundHandlerAdapter {

    private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    private static final String CONNECTION_CLOSE = "close";
    private static final String ACTION_SUFFIX = ".action";

    private HttpRequest request;
    private FullHttpResponse response;
    private Channel channel;
    @Autowired
    private Oauth2ServerProperties serverProperties;

    @Autowired
    private RouterContext routerContext;

    public ActionDispatcher(){
    }

    @PostConstruct
    public void init() throws Exception{
       routerContext.loadRouterContext(serverProperties.getConfigFilePath());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(msg instanceof HttpRequest){
            channel = ctx.channel();
            request = (HttpRequest) msg;

            try{
                String path = getRequestPath();
                ActionWrapper actionWrapper = routerContext.getActionWrapper(path);
                if(actionWrapper == null){
                    response = HttpReaderUtil.getNotFoundResponse();
                    writeResponse(true);
                    return;
                }

                DataHolder.setRequest(request);
                ActionProxy proxy = routerContext.getActionProxy(actionWrapper);
                Return result = proxy.execute();
                if(result != null){
                    response = result.process();
                }
                writeResponse(false);
            }catch(Exception e){
                response = HttpReaderUtil.getErroResponse();
                writeResponse(true);
            }finally{
                ReferenceCountUtil.release(msg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private String getRequestPath() throws Exception{
        String uri = request.uri();
        int startIndex = uri.indexOf(ACTION_SUFFIX);
        if(startIndex <= 0){
            throw new Exception("request path error");
        }
        return uri.substring(0,startIndex + ACTION_SUFFIX.length());
    }

    private void writeResponse(boolean forceClose){
        boolean close = isClose();
        if(!close && !forceClose){
            response.headers().add(HttpHeaders.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
        }
        ChannelFuture future = channel.write(response);
        if(close || forceClose){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private boolean isClose(){
        if(request.headers().contains(HttpHeaders.CONNECTION, CONNECTION_CLOSE, true) ||
                (request.protocolVersion().equals(HttpVersion.HTTP_1_0) &&
                        !request.headers().contains(HttpHeaders.CONNECTION, CONNECTION_KEEP_ALIVE, true)))
            return true;
        return false;
    }


}
