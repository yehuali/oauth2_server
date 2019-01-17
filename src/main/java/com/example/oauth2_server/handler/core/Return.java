package com.example.oauth2_server.handler.core;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 结果抽象接口
 */
public interface Return {
    public abstract FullHttpResponse process() throws Exception;
}
