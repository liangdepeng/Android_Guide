package com.example.myapplication.thread;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/18
 * <p>
 * Summary:
 */
public class ExecutorUtil {

    private ExecutorUtil() {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(new MyThreadFactory("test_cache"));
        // CachedThreadPool.它是一种线程数量不定的线程池，只有非核心线程，可以简单理解为最大线程数是无限大的。
        // CachedThreadPool的任务队列相当于一个空集合，这导致任何任务都会被立即执行，比较适合做一些大量的耗时较少的任务。
        ThreadPoolExecutor cachedThreadPool2 = new ThreadPoolExecutor(// 线程池
                0, // 要保留在池中的线程数，即使它们处于空闲状态，除非设置了 {@code allowCoreThreadTimeOut}
                Integer.MAX_VALUE, //线程池中允许的最大线程数
                60L,//当线程数大于核心数时，这是多余的空闲线程在终止前等待新任务的最长时间
                TimeUnit.SECONDS,// {@code keepAliveTime} 参数的时间单位
                new SynchronousQueue<Runnable>(),//用于在执行任务之前保留任务的队列。该队列将仅保存由 {@code execute} 方法提交的 {@code Runnable} 任务。
                new MyThreadFactory("test_cache")//执行程序创建新线程时使用的工厂
        );

        // 如果为 false（默认），核心线程即使在空闲时也保持活动状态。
        // 如果为 true，则核心线程使用 keepAliveTime 超时等待工作。
        cachedThreadPool2.allowCoreThreadTimeOut(false);

        // 执行任务
        cachedThreadPool2.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        requestList.add("request_1");
        requestList.add("request_2");
        requestList.add("request_3");

        cachedThreadPool2.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    resultCallback("request_1", "result_1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cachedThreadPool2.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    resultCallback("request_2", "result_2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cachedThreadPool2.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    resultCallback("request_3", "result_3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        //创建一个线程池，该线程池重用固定数量的线程在共享的无界队列中运行。在任何时候，最多 {@code nThreads} 个线程将是活动的处理任务。
        // 如果在所有线程都处于活动状态时提交了其他任务，它们将在队列中等待，直到有线程可用。如果任何线程在关闭前的执行过程中因失败而终止，
        // 则在需要执行后续任务时，将有一个新线程取而代之。池中的线程将一直存在，直到明确{@link ExecutorServiceshutdown shutdown}。
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        //FixedTreadPool. fixed有稳固的含义，通过工厂方法类Executors的newFixedThreadPool方法创建。
        // 它是线程数量固定的线程池，仅有核心线程且没有超时策略，所以线程不会被回收。这意味着它能够快速的响应外界请求。
        ThreadPoolExecutor fixHtreadPoolExecutor = new ThreadPoolExecutor(2,// 核心线程数量
                2,// 最大线程数量
                0L, // 线程池满负荷运行时 之后的任务等待时间
                TimeUnit.MILLISECONDS,// 时间单位
                new LinkedBlockingQueue<Runnable>(),// 任务队列
                new MyThreadFactory("test_fix")// 线程工厂
        );

        //fixHtreadPoolExecutor.execute();


        // 一个 {@link ThreadPoolExecutor} 可以额外安排命令在给定延迟后运行，或定期执行。当需要多个工作线程时，
        // 或者需要 {@link ThreadPoolExecutor}（此类扩展）的额外灵活性或功能时，此类比 {@link java.util.Timer} 更可取。
        // <p>延迟任务在启用后立即执行，但没有任何关于启用后何时开始的实时保证。计划执行时间完全相同的任务按提交的先进先出 (FIFO) 顺序启用。
        // <p>当提交的任务在运行之前被取消时，执行会被抑制。默认情况下，此类取消的任务不会自动从工作队列中删除，直到其延迟结束。
        // 虽然这可以实现进一步的检查和监控，但它也可能导致取消任务的无限保留。
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(6, new MyThreadFactory("schedule_"));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // doSomeThing
            }
        };
        // 定时任务 100秒之后执行
        scheduledThreadPool.schedule(runnable, 100, MILLISECONDS);
        // 60秒 之后开始执行任务  之后每隔 60*60秒 = 1 小时 循环执行 定时任务
        scheduledThreadPool.scheduleAtFixedRate(runnable, 60, 60 * 60, MILLISECONDS);

        scheduledThreadPool.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(1000);

                return "10_alive_return";
            }
        }, 10, MILLISECONDS);


        // 创建一个 Executor，它使用单个工作线程在无界队列中运行，并在需要时使用提供的 ThreadFactory 创建一个新线程。
        // 与其他等效的 {@code newFixedThreadPool(1, threadFactory)} 不同，返回的执行程序保证不可重新配置以使用其他线程。
        // SingleThreadExecutor.只有一个核心线程，所以确保所有的任务都是在一个线程里顺序执行。
        // 把所有的任务都放到一个线程，这样有一个好处是不需要处理线程同步问题。
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(new MyThreadFactory("newSingleThreadExecutor"));

        Future<Object> future = singleThreadExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(10000);
                return "finish";
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ExecutorService newWorkStealingPool = Executors.newWorkStealingPool(2);
           // newWorkStealingPool.execute();

        }

        Executors.newSingleThreadScheduledExecutor(new MyThreadFactory(""));

//        try {
//            future.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private List<String> requestList = new ArrayList<>(2);

    private synchronized void resultCallback(String request, String result) {

        if (requestList.size() != 0) {
            requestList.remove(request);
        } else {
            Log.e("thread_thread","finish");
        }

    }
}
