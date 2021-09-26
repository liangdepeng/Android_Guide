package com.example.myapplication.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/26
 * <p>
 * Summary:
 */
public class ExecutorHelper {

    private final static ThreadPoolExecutor EXEC = new ThreadPoolExecutor(// 线程池
            0, // 要保留在池中的线程数，即使它们处于空闲状态，除非设置了 {@code allowCoreThreadTimeOut}
            Integer.MAX_VALUE, //线程池中允许的最大线程数
            60L,//当线程数大于核心数时，这是多余的空闲线程在终止前等待新任务的最长时间
            TimeUnit.SECONDS,// {@code keepAliveTime} 参数的时间单位
            new SynchronousQueue<Runnable>(),//用于在执行任务之前保留任务的队列。该队列将仅保存由 {@code execute} 方法提交的 {@code Runnable} 任务。
            new MyThreadFactory("test_cache")//执行程序创建新线程时使用的工厂
    );

    public static ThreadPoolExecutor getExec() {
        return EXEC;
    }
}
