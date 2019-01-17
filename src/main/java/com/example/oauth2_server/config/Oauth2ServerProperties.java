package com.example.oauth2_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 服务配置
 */
@ConfigurationProperties(prefix = "spring.oauth2.server")
public class Oauth2ServerProperties {
    /**
     * SSL端口号, 默认8883端口
     */
    private int sslPort = 8885;
    /**
     * SSL密钥文件密码
     */
    private String sslPassword;

    /**
     * 心跳时间(秒), 默认60秒, 该值可被客户端连接时相应配置覆盖
     */
    private int keepAlive = 60;

    /**
     * Sokcet参数, 存放已完成三次握手请求的队列最大长度, 默认511长度
     */
    private int soBacklog = 511;

    /**
     * Socket参数, 是否开启心跳保活机制, 默认开启
     */
    private boolean soKeepAlive = true;


    public int getSslPort() {
        return sslPort;
    }

    public void setSslPort(int sslPort) {
        this.sslPort = sslPort;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getSoBacklog() {
        return soBacklog;
    }

    public void setSoBacklog(int soBacklog) {
        this.soBacklog = soBacklog;
    }

    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }
}
