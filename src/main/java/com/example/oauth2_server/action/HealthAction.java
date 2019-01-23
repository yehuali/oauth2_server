package com.example.oauth2_server.action;

import com.example.oauth2_server.config.Oauth2ServerProperties;
import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.ret.Render;
import com.example.oauth2_server.handler.core.ret.RenderType;
import com.example.oauth2_server.mapper.OauthAccessTokenMapper;
import com.example.oauth2_server.models.OauthAccessTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthAction extends BaseAction {

    @Autowired
    private Oauth2ServerProperties oauth2ServerProperties;

    @Autowired
    private OauthAccessTokenMapper accessTokenMapper;

    //测试基本参数类型
    public Render healthCheck(){
        List<OauthAccessTokens> list = accessTokenMapper.getAll();
        System.out.println("clientId:"+list.get(0).getClient_id()+",port:" +oauth2ServerProperties.getPort());
        return new Render(RenderType.JSON, "Received your primTypeTest request.[from primTypeTest]");
    }
}
