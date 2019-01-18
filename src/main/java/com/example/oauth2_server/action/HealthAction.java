package com.example.oauth2_server.action;

import com.example.oauth2_server.config.Oauth2ServerProperties;
import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.ret.Render;
import com.example.oauth2_server.handler.core.ret.RenderType;
import com.example.oauth2_server.mapper.OauthAccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthAction extends BaseAction {

    @Autowired
    private Oauth2ServerProperties oauth2ServerProperties;

    private OauthAccessTokenMapper accessTokenMapper;

    //测试基本参数类型
    public Render healthCheck(){
        accessTokenMapper.getAll();
        System.out.println(oauth2ServerProperties.getPort());
        return new Render(RenderType.JSON, "Received your primTypeTest request.[from primTypeTest]");
    }
}
