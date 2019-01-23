package com.example.oauth2_server.action;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/user",produces = "text/json;charset=utf-8")
public class UserController{
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username,String pwd){
        JSONObject resultJson = new JSONObject();
        Map<String, String> loginResult = new HashMap<String, String>();
        loginResult.put("username", username);
        loginResult.put("age", "20");
        loginResult.put("sex", "boy");

        resultJson.put("code", 200);
        resultJson.put("msg", "登录成功");
        resultJson.put("result", loginResult);

        return JSONObject.toJSONString(resultJson);
    }
}
