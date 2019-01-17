package com.example.oauth2_server.models;

import lombok.Data;

/**
 * 存储作用域相关信息
 */
@Data
public class OauthScopes extends BaseModel {
    private String scope;
    private String description;
    private Integer isDefault;

}
