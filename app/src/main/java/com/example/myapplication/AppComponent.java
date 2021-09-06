package com.example.myapplication;

import com.example.commom_module.IAppComponent;
import com.example.commom_module.IPlayService;
import com.example.commom_module.IStudyService;
import com.example.commom_module.JumpServiceFactory;
import com.example.module_play.PlayServiceImpl;
import com.example.moudle_study.StudyServiceImpl;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/3
 * <p>
 * Summary: 初始化组件添加各个组件通信的实现逻辑类
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
