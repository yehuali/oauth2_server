package com.example.oauth2_server.dao;

import com.example.oauth2_server.mapper.OauthAccessTokenMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 数据持久层相关测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    private OauthAccessTokenMapper accessTokenMapper;

    @Test
    public void findByName() throws Exception {
        accessTokenMapper.getAll();
    }
}
