package com.example.myapplication.dagger;

import com.example.myapplication.dagger.expand.StudentMutilModule;

import dagger.Component;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: // Component 可以标注接口或抽象类，Component 桥梁可以完成依赖注入过程，
 */
@Component(modules = StudentMutilModule.class)
public interface StudentComponent {
    void injectDaggerActivity(DaggerActivity activity);
}
