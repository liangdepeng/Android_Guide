package com.derry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary:自定义注解
 */
@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestZhuJie {

    String value() default "default_value";

    boolean isTestParam();
}
