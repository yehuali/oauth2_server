package com.example.oauth2_server.mapper;

import com.example.oauth2_server.models.OauthAccessTokens;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OauthAccessTokenMapper {

    @Select("SELECT * FROM oauth_access_tokens")
    List<OauthAccessTokens> getAll();
}
