package cn.example.common_module;

import java.util.HashMap;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: 各个隔离的组件实现通信跳转
 */
public enum JumpServiceFactory {
    //单例实现
    INSTANCE;

    private final HashMap<String, Object> componentServices = new HashMap<>();

    // 组件通信初始化
    public void init(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            ((IAppComponent) instance).onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unInit() {
        clearAll();
    }

    public void addComponentService(String className, Object componentService) {
        componentServices.put(className, componentService);
    }

    public void removeComponentService(String className) {
        componentServices.remove(className);
    }

    public void clearAll(){
        componentServices.clear();
    }

    public <T> T getComponentService(String className) {
        Object res = componentServices.get(className);
        return ((T) res);
    }
}
