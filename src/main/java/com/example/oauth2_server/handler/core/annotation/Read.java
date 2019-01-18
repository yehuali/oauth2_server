package com.example.oauth2_server.handler.core.annotation;

import java.lang.annotation.*;

/**
 * Read注解
 */

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Read {
	
	/**
	 * 将使用什么样的键值读取对象，对于field，就是他名字 对于method的parameter，需要指明
	 * @return the key itself
	 */
	public String key() default "";
	
	/**
	 * 提供设置缺省值
	 * @return 提供设置缺省值
	 */
	public String defaultValue() default "CORE_ANNOTATION_READ_DEFAULTVALUE_DEFAULT";
	
	/**
     * 是否校验参数为空
     * @return
     */
    public boolean checkNull() default false;

}
