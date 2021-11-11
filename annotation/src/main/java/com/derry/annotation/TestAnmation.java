package com.derry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: 注解 说明
 */
//  标记这些注解是否包含在用户文档中 JAVAdoc
@Documented

// 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)
@Inherited

// 标记这个注解应该是哪种 Java 成员 作用范围
@Target({ElementType.FIELD/*字段声明（包括枚举常量）  */,
        ElementType.TYPE /*类、接口（包括注释类型）或枚举声明 */,
        ElementType.METHOD /* 方法声明*/,
        ElementType.PARAMETER /*参数声明 */,
        ElementType.CONSTRUCTOR /*构造方法申明*/,
        ElementType.LOCAL_VARIABLE /* 局部变量声明 */,
        ElementType.ANNOTATION_TYPE/*注释类型声明*/,
        ElementType.PACKAGE/*包声明*/})

// 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问
@Retention(RetentionPolicy.SOURCE /* Annotation信息仅存在于编译器处理期间，编译器处理完之后就没有该Annotation信息了  */)
//@Retention(RetentionPolicy.CLASS/* 编译器将Annotation存储于类对应的.class文件中。默认行为  */)
//@Retention(RetentionPolicy.RUNTIME /* 编译器将Annotation存储于class文件中，并且可由JVM读入 */)
public @interface TestAnmation {
    int value();
}
