package com.example.oauth2_server.models;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 访问令牌
 */
@Data
public class OauthAccessTokens extends BaseModel {
    private String client_id;
    private String user_id;
    private String token;
    private Timestamp expires_at;
    private String scope;

}
