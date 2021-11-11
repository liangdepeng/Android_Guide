package com.example.myapplication.dagger;

import javax.inject.Inject;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary:
 */
public class Student {

    public int age = 0;
    public String name = "default_name";
    public String phone = "default_phone";

    // 用@Inject标注构造函数时，Dagger 2 会完成实例的创建。
    // 仅限于 能够标记构造函数的情况 其他群请移步 StudentMutilModule
   // @Inject
    public Student() {
    }
}
