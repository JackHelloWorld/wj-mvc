package com.wj.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 忽略登录验证
 * @author Jack
 *
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface ValidateIgnoreLogin {

}
