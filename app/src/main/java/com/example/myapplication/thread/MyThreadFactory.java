package com.example.myapplication.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/18
 * <p>
 * Summary:
 */
public class MyThreadFactory implements ThreadFactory {

    private final ThreadFactory threadFactory;
    private final String threadBaseName;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public MyThreadFactory(String threadName) {
        this.threadBaseName = threadName;
        this.threadFactory = Executors.defaultThreadFactory();
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread thread = threadFactory.newThread(r);
        thread.setName(threadBaseName + "_@_" + atomicInteger.getAndIncrement());
        return thread;
    }
}
