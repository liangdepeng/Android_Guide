package com.example.myapplication;

import com.example.module_play.PlayServiceImpl;
import com.example.moudle_study.StudyServiceImpl;

import cn.example.common_module.IAppComponent;
import cn.example.common_module.IPlayService;
import cn.example.common_module.IStudyService;
import cn.example.common_module.JumpServiceFactory;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/3
 * <p>
 * Summary: 初始化组件添加各个组件通信的实现逻辑类
 *
 *  组件化基本原理：
 *  各个业务module模块 相互独立 不耦合 不产生相互依赖 开发时可以 单独运行一个 module 更加快，提高效率
 *
 *   组件通信实现 ：
 *   各个组件需要通过中间的 common 组件来实现通信，由common模块 定义各个module 的通信接口 然后 各个module 去实现，
 *   在common模块定义通信类工厂获取各个模块通信实现类的对象来进行通信，各个模块的通信类对象由app壳工程在application
 *   进行初始化,各个 moudle 通过 通信类工厂 JumpServiceFactory 即可调用
 */
public class AppComponent implements IAppComponent {
    @Override
    public void onCreate() {
        JumpServiceFactory.INSTANCE.addComponentService(IPlayService.class.getName(),new PlayServiceImpl());
        JumpServiceFactory.INSTANCE.addComponentService(IStudyService.class.getName(),new StudyServiceImpl());
    }

    @Override
    public void onDelete() {
        JumpServiceFactory.INSTANCE.clearAll();
    }
}
