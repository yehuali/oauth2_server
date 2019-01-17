package com.example.oauth2_server.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 1996763651958081846L;
    private String id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;

}
