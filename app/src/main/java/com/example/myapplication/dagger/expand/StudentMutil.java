package com.example.myapplication.dagger.expand;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: 需要实例化的对象
 */
public class StudentMutil {

    public int age = 1;
    public String name = "default_name222";
    public String phone = "default_phone222";

    public StudentMutil(int age, String name, String phone) {
        this.age = age;
        this.name = name;
        this.phone = phone;
    }
}
