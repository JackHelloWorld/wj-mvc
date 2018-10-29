package com.wj.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 查询属性处理,只对String类型属性生效
 * @author Jack
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryConfig {

	/**
	 * 查询类型
	 * @return
	 */
	QueryType value() default QueryType.TRIM;
	
	/**
	 * 数据库查询,属性赋值处理
	 * @author Jack
	 *
	 */
	public enum QueryType {

		/**模糊查询,忽略左边 %?*/
		LIKELEFT,
		
		/**模糊查询,忽略右边  ?%*/
		LIKERIGHT,
		
		/**模糊查询,忽略两端 %?% */
		LIKEBOTH,
		
		/**去除空格 */
		TRIM,
		
		/**时间查询,自动拼接  ? 00:00:00*/
		DATETIMEMIN,
		
		/**时间查询,自动拼接  ? 23:59:59*/
		DATETIMEMAX,
		
		/**不做任何处理*/
		NONE;
		
	}
}
