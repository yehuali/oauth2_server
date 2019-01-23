package com.example.oauth2_server.mapper;

import com.example.oauth2_server.models.OauthUsers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * https://www.cnblogs.com/ityouknow/p/6037431.html
 */
public interface OauthUserMapper {

    @Select("SELECT * FROM oauth_users where username = #{username}")
    public OauthUsers getUserByUserName(@Param("username") String username);
}
