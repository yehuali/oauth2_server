package com.example.oauth2_server.services;

import com.example.oauth2_server.exception.AuthenticationException;
import com.example.oauth2_server.mapper.OauthUserMapper;
import com.example.oauth2_server.models.OauthUsers;
import com.example.oauth2_server.services.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsService {

    @Autowired
    private OauthUserMapper userMapper;


    public OauthUsers loadUserByUsername(String username,String password){
        //从数据库查询用户信息
        OauthUsers user = userMapper.getUserByUserName(username);
        if(user !=null){
            //查看用户权限(待完成)
            return user;
        }else{
            throw new AuthenticationException("用户名不存在！");
        }
    }

}
