package com.derry.annotation;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: 自定义注解测试
 */
@TestZhuJie(value = "注解的值哈哈哈",isTestParam = true)
public class Student {

    public String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }
}
