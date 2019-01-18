package com.example.oauth2_server.handler.core.invocation;

import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.Return;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 封装了action拦截器和调用的过程
 */
@Component
public class ActionInvocation {
    private ActionProxy proxy;

    public void init(ActionProxy proxy){
        this.proxy = proxy;
    }


    public Return invoke() throws Exception{
        Return result = null;
        result = invokeAction();
        return result;
    }

    protected Return invokeAction() throws Exception{
        BaseAction action = proxy.getActionObject();
        Method method = proxy.getMethod();
        String methodName = proxy.getMethodName();
        return action.processRequest(method, methodName);
    }
}
