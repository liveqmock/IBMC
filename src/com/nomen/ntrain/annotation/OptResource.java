package com.nomen.ntrain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 操作方法窃取
 * @author 连金亮
 * @date   2014-11-20
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptResource {
	/**操作的内容，如组卷设置***/
	String optRemark() default "操作 内容 不能为空！";
	/**操作的类型，如新增***/
	OptEnums optType();
}
