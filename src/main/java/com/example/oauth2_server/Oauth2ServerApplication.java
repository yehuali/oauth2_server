package com.example.oauth2_server;

import com.example.oauth2_server.config.Oauth2ServerProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 通过SpringBoot启动服务
 */
@SpringBootApplication(scanBasePackages = {"com.example.oauth2_server"})
@MapperScan("com.example.oauth2_server.mapper")
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Oauth2ServerApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public Oauth2ServerProperties brokerProperties() {
        return new Oauth2ServerProperties();
    }
}

