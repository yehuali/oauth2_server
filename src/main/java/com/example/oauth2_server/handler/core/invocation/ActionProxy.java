package com.example.oauth2_server.handler.core.invocation;

import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.Return;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * Action代理，本身不做action调用的工作，只是调用Invocation本身。
 */
@Data
public class ActionProxy extends BaseAction {
    private BaseAction actionObject;
    private ActionInvocation invocation;
    private String methodName;
    private Method method;

    public Return execute() throws Exception{
        return invocation.invoke();
    }
}
