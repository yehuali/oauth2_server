package com.example.oauth2_server.handler.core.config;

import com.example.oauth2_server.handler.core.BaseAction;

import java.lang.reflect.Method;

/**
 * 包装Action
 */
public class ActionWrapper {
    public BaseAction actionObject;
    public Method method;
    public String actionPath;
    public Method callBackMethod;

    public ActionWrapper(BaseAction actionObject, Method method, String actionPath) {
        this(actionObject, method, actionPath, null);
    }

    public ActionWrapper(BaseAction actionObject, Method method ,String actionPath, Method callBackMethod) {
        this.actionObject = actionObject;
        this.method = method;
        this.actionPath = actionPath;
        this.callBackMethod = callBackMethod;
    }
}
