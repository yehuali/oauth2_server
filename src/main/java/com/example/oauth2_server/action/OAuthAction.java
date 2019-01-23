package com.example.oauth2_server.action;

import com.example.oauth2_server.exception.AuthenticationException;
import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.ret.Render;
import com.example.oauth2_server.handler.core.ret.RenderType;
import com.example.oauth2_server.models.OauthUsers;
import com.example.oauth2_server.services.UserDetailsService;
import com.example.oauth2_server.services.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 授权
 */
@Component
@Slf4j
public class OAuthAction extends BaseAction {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    /**
     * http://niocoder.com/2018/01/05/Spring-Security%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E4%BA%8C-Spring-Security%E6%8E%88%E6%9D%83%E8%BF%87%E7%A8%8B/#usernamepasswordauthenticationfilter
     * 验证逻辑
     *  1.根据username查缓存
     *    1.1 没有去注册用户
     *    1.2 有则校验
     * @param username
     * @param password
     * @return
     */
    public Render usernamePasswordAuthentication(String username,String password){
        if (username == null) {
            username = "";
        }

        if (password == null) {//明文
            password = "";
        }
        //根据用户名查看用户
        OauthUsers user = userDetailsService.loadUserByUsername(username.trim(),password.trim());

        //判断用户属性锁定，过期等(待完成)

        //校验密码
        if (!passwordEncoder.matches(user.getPassword(), password)) {
            log.error("Authentication failed: password does not match stored value");
            throw new AuthenticationException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }

        //认证之后的逻辑

        return new Render(RenderType.JSON, "Received your primTypeTestWithNamespace request.[from primTypeTestWithNamespace]");
    }

}
