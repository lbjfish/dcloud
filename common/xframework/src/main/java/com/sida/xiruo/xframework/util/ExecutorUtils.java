package com.sida.xiruo.xframework.util;


import com.sida.xiruo.xframework.concurrent.core.StandardThreadExecutorService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池简单工具类
 *
 * @author Tung
 * @version 1.0
 * @date 2018/2/24.
 * @update
 */
public class ExecutorUtils {
    private static StandardThreadExecutorService processorExecutorService = new StandardThreadExecutorService.Builder("Async_Processor").build();
    public static StandardThreadExecutorService getProcessorExecutorService(){
        return processorExecutorService;
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return getProcessorExecutorService().submit(task);
    }

    public static <T> Future<T> submit(Runnable task, T result) {
        return getProcessorExecutorService().submit(task, result);
    }

    public static Future<?> submit(Runnable task) {
        return getProcessorExecutorService().submit(task);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return getProcessorExecutorService().invokeAll(tasks);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                               long timeout, TimeUnit unit)
            throws InterruptedException {
        return getProcessorExecutorService().invokeAll(tasks, timeout, unit);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return getProcessorExecutorService().invokeAny(tasks);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return getProcessorExecutorService().invokeAny(tasks, timeout, unit);
    }

    public static void main(String[] args) throws InterruptedException {
        StandardThreadExecutorService batchProcessorExecutorService = ExecutorUtils.getProcessorExecutorService();
    }
}
