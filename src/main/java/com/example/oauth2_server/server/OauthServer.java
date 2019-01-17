package com.example.oauth2_server.server;

import com.example.oauth2_server.config.Oauth2ServerProperties;
import com.example.oauth2_server.handler.ActionDispatcher;
import com.example.oauth2_server.handler.OauthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Netty启动Server
 */
@Component
public class OauthServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthServer.class);
    @Autowired
    private Oauth2ServerProperties serverProperties;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

//    private SslContext sslContext;

    private Channel channel;

    @PostConstruct
    public void start() throws Exception {
        LOGGER.info("Initializing OauthServer ...");
        bossGroup =  new NioEventLoopGroup();
        workerGroup =  new NioEventLoopGroup();
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("keystore/mqtt-broker.pfx");
//        keyStore.load(inputStream, serverProperties.getSslPassword().toCharArray());
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//        kmf.init(keyStore, serverProperties.getSslPassword().toCharArray());
//        sslContext = SslContextBuilder.forServer(kmf).build();
        ActionDispatcher dispatcher = new ActionDispatcher();
        dispatcher.init(null);
        oauthServer();
        LOGGER.info("OauthServer is up and running. Open SSLPort: {}",  serverProperties.getSslPort());
    }


    @PreDestroy
    public void stop() {
        LOGGER.info("Shutdown OauthServer ...");
        bossGroup.shutdownGracefully();
        bossGroup = null;
        workerGroup.shutdownGracefully();
        workerGroup = null;
        channel.closeFuture().syncUninterruptibly();
        channel = null;
        LOGGER.info("OauthServer shutdown finish.");
    }

    private void oauthServer() throws Exception {
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // handler在初始化时就会执行
                .handler(new LoggingHandler(LogLevel.INFO))
                // childHandler会在客户端成功connect后才执行
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        // Netty提供的心跳检测
                        channelPipeline.addFirst("idle", new IdleStateHandler(0, 0, serverProperties.getKeepAlive()));
                        // Netty提供的SSL处理
//                        SSLEngine sslEngine = sslContext.newEngine(socketChannel.alloc());
//                        sslEngine.setUseClientMode(false);        // 服务端模式
//                        sslEngine.setNeedClientAuth(false);        // 不需要验证客户端
//                        channelPipeline.addLast("ssl", new SslHandler(sslEngine));

                        channelPipeline.addLast("codec",new HttpServerCodec());
                        channelPipeline.addLast("aggregator",new HttpObjectAggregator(1024*1024));//在处理 POST消息体时需要加上
                        channelPipeline.addLast("dispatcher",new ActionDispatcher());//请求分发组件
                    }
                })
                .option(ChannelOption.SO_BACKLOG, serverProperties.getSoBacklog())
                .childOption(ChannelOption.SO_KEEPALIVE, serverProperties.isSoKeepAlive());
        channel = sb.bind(serverProperties.getSslPort()).sync().channel();
    }
}
