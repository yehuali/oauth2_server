package com.example.oauth2_server.models;

import lombok.Data;

/**
 * 用户或资源拥有者
 */
@Data
public class OauthUsers extends BaseModel {
    private String roleId;
    private String userName;
    private String password;
}
