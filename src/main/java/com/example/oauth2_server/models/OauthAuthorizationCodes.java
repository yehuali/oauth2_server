package com.example.oauth2_server.models;

import lombok.Data;

/**
 * 授权令牌(授权码)
 */
@Data
public class OauthAuthorizationCodes extends BaseModel {
    private String clientId;
    private String userId;
    private String code;
    private String redirectUri;
    private String expires;
    private String scope;
}
