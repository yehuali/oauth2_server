package com.example.oauth2_server.handler.core;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Action基础类，所有Action必须继承自此类。BaseAction封装了参数填充、转发、方法调用等功能，并向子类提供render等渲染方法。
 */
@Slf4j
public class BaseAction {
    public Return processRequest(Method method, String methodName) throws Exception {
        if (method == null) {
            throw new NoSuchMethodException("Can not find specified method: " + methodName);
        }
        return exec(method);
    }
    /**
     * 供内部使用的调用所指定方法的方法
     * 	 * @param method
     * 	 *            所指定的需要被调用的方法
     * 	 * @return 调用方法后所返回的结果
     * 	 * @throws Exception
     */
    private Return exec(Method method) throws Exception {
        return null;
    }
}
