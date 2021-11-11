package com.derry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary:
 */
@Target({ElementType.TYPE,ElementType.FIELD})// 在变量上进行注解
@Retention(RetentionPolicy.CLASS)//
public @interface BindMyView {
    String value();
}
