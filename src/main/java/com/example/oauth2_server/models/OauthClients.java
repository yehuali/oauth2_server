package com.example.oauth2_server.models;

import lombok.Data;

/**
 * 客户应用
 */
@Data
public class OauthClients extends BaseModel {
    private String clientKey;
    private String clientSecret;
    private String redirectUri;
}
