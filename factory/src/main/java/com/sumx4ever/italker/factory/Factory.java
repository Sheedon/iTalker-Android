package com.sumx4ever.italker.factory;

import com.sumx4ever.common.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xudongsun on 2018/3/16.
 */

public class Factory {
    // 单例模式
    private static final Factory instance;
    private final ExecutorService execute;

    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
//    int KEEP_ALIVE_TIME = 0;
//    TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
//    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

    static {
        instance = new Factory();
    }

    private Factory() {
        execute = Executors.newFixedThreadPool(NUMBER_OF_CORES);
//      execute = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue);
    }

    public Application app() {
        return Application.getInstance();
    }

    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        instance.execute.execute(runnable);
    }
}
