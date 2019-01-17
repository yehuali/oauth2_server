package com.example.oauth2_server.handler.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Namespace注解
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
	public String value() default "/";
}
