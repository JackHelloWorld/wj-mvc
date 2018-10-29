package com.wj.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于校验属性值
 * @author Jack
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateEdit {

	/**
	 * 校验类型
	 * @return
	 */
	ValidateType type() default ValidateType.NULL;
	
	/**
	 * 校验结果信息
	 * @return
	 */
	String message();
	
	public enum ValidateType{
		
		/**非空校验*/
		NULL,
		/**int数字校验*/
		INTEGER,
		
		/**long数字校验*/
		LONG,
		
		/**double数字校验*/
		DOUBLE,
		
		/**BIGDECIMAL 数字校验*/
		BIGDECIMAL;
	}
	
	
}
