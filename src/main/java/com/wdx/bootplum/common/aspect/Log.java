package com.wdx.bootplum.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author wangwei
 * @Description //TODO 自定义LOG注解
 * @Date 13:53 2019-04-03
 * @Param 
 * @return 
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	String value() default "";
}
