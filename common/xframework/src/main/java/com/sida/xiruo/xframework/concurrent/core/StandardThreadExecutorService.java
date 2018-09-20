package com.sida.xiruo.xframework.concurrent.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 包装的ExecutorService标准实例
 *
 * @author Tung
 * @version 1.0
 * @date 2018/1/31.
 * @update
 */

public class StandardThreadExecutorService extends StandardThreadExecutor implements ExecutorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardThreadExecutorService.class);

    private StandardThreadExecutorService(Builder builder) {
        super(builder);
    }

    @Override
    public final void shutdown() {
        getExecutor().shutdown();
    }

    @Override
    public final List<Runnable> shutdownNow() {
        return getExecutor().shutdownNow();
    }

    @Override
    public final boolean isShutdown() {
        return getExecutor().isShutdown();
    }

    @Override
    public final boolean isTerminated() {
        return getExecutor().isTerminated();
    }

    @Override
    public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return getExecutor().awaitTermination(timeout, unit);
    }

    @Override
    public final <T> Future<T> submit(Callable<T> task) {
        task = wrap(task);
        return getExecutor().submit(task);
    }

    @Override
    public final <T> Future<T> submit(Runnable task, T result) {
        task = wrap(task);
        return getExecutor().submit(task, result);
    }

    @Override
    public final Future<?> submit(Runnable task) {
        task = wrap(task);
        return getExecutor().submit(task);
    }

    private final <T> Collection<Callable<T>> createTasks(Collection<? extends Callable<T>> tasks) {
        if (tasks == null) {
            return null;
        }
        List<Callable<T>> results = new ArrayList<>(tasks.size());
        for (Callable<T> task : tasks) {
            results.add(wrap(task));
        }
        return results;
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        tasks = createTasks(tasks);
        return getExecutor().invokeAll(tasks);
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        tasks = createTasks(tasks);
        return getExecutor().invokeAll(tasks, timeout, unit);
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        tasks = createTasks(tasks);
        return getExecutor().invokeAny(tasks);
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        tasks = createTasks(tasks);
        return getExecutor().invokeAny(tasks, timeout, unit);
    }

    public static class Builder {
        private String name = "DEFAULT_EXECUTOR";
        /**
         * 线程名称的默认前缀
         */
        private String namePrefix = "default-exec-";

        /**
         * 最大线程数
         */
        private int maxThreads = 20;

        /**
         * 最小线程数,同核心线程数
         */
        private int coreThreads = 4;

        /**
         * 线程空闲时间(idle time) 毫秒
         */
        private int maxIdleTime = 6000;

        /**
         * 是否预先启动核心线程线程
         */
        private boolean prestartCoreThreads = true;

        /**
         * 最大入队数量，（超过后会我们会reject掉任务）
         */
        private int maxQueueSize = Integer.MAX_VALUE;

        /**
         * 默认的线程优先级 - 普通5
         */
        private int threadPriority = Thread.NORM_PRIORITY;

        /**
         * 是否以daemon形式运行
         */
        private boolean daemon = true;

        private static final Map<String, ExecutorService> cachedMap = new ConcurrentHashMap<>();

        public Builder(String executorName) {
            this.name = executorName;
        }

        private synchronized boolean checkExisted(String executorName) {
            ExecutorService es = cachedMap.get(executorName);
            if (es == null || es.isShutdown()) {
                return false;
            }
            return true;
        }

        public StandardThreadExecutorService build() {
            // 不严格要求不重复，所以简单做一下同步就好了。
            if (checkExisted(this.getName())) {
                // throw new RuntimeException("已有相同名称的线程池，不能继续新建。");
                LOGGER.error("已有相同名称的线程池:" + this.getName(), new RuntimeException("已有相同名称的线程池，不能继续新建。"));
                return null;
            }
            StandardThreadExecutorService es = new StandardThreadExecutorService(this);
            cachedMap.put(this.getName(), es);
            return es;
        }

        public static void removeCachedExecutor(String executorName) {
            cachedMap.remove(executorName);
        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public String getNamePrefix() {
            return namePrefix;
        }

        public Builder setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
            return this;
        }

        public int getMaxThreads() {
            return maxThreads;
        }

        public Builder setMaxThreads(int maxThreads) {
            this.maxThreads = maxThreads;
            return this;
        }

        public int getCoreThreads() {
            return coreThreads;
        }

        public Builder setCoreThreads(int coreThreads) {
            this.coreThreads = coreThreads;
            return this;
        }

        public int getMaxIdleTime() {
            return maxIdleTime;
        }

        public Builder setMaxIdleTime(int maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
            return this;
        }

        public boolean isPrestartCoreThreads() {
            return prestartCoreThreads;
        }

        public Builder setPrestartCoreThreads(boolean prestartCoreThreads) {
            this.prestartCoreThreads = prestartCoreThreads;
            return this;
        }

        public int getMaxQueueSize() {
            return maxQueueSize;
        }

        public Builder setMaxQueueSize(int maxQueueSize) {
            this.maxQueueSize = maxQueueSize;
            return this;
        }

        public int getThreadPriority() {
            return threadPriority;
        }

        public Builder setThreadPriority(int threadPriority) {
            this.threadPriority = threadPriority;
            return this;
        }

        public boolean isDaemon() {
            return daemon;
        }

        public Builder setDaemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }
    }


}
