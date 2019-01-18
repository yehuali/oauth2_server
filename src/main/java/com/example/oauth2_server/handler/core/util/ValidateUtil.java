package com.example.oauth2_server.handler.core.util;

import com.example.oauth2_server.handler.core.exception.ValidationException;

/**
 * 断言工具类
 */
public class ValidateUtil {

    /**
     * 断言非空
     * @param dataName
     * @param values
     */
    public static void checkNull(String dataName, Object... values){
        if(values == null){
            throw new ValidationException(dataName +" cannot be null");
        }
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if(value == null){
                throw new ValidationException(dataName +" cannot be null at " + dataName + "[" + i + "]");
            }
        }
    }
}
