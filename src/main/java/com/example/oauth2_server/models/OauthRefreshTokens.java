package com.example.oauth2_server.models;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 刷新令牌
 */
@Data
public class OauthRefreshTokens extends BaseModel {
    private String clientId;
    private String userId;
    private String token;
    private Timestamp expires;
    private String scope;

}
