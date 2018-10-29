package com.wj.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 权限验证配置
 * @author Jack
 *
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface ValidateAuth {

	/**验证值*/
	String value() default "";
	
	/**是否验证,默认为验证*/
	boolean validate() default true;
	
}
