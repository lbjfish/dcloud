package com.sida.xiruo.xframework.concurrent.core;

import com.sida.xiruo.xframework.concurrent.DelegatingSecurityContextCallable;
import com.sida.xiruo.xframework.concurrent.DelegatingSecurityContextRunnable;
import com.sida.xiruo.xframework.concurrent.ResizableExecutor;
import com.sida.xiruo.xframework.concurrent.ThreadsConstants;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * BaseExecutor的实现，类是abstract的，不让直接使用。
 *
 * @author Tung
 * @version 1.0
 * @date 2018/1/31.
 * @update
 * @see StandardThreadExecutorService
 */

public abstract class StandardThreadExecutor implements BaseExecutor, ResizableExecutor {

    // ---------------------------------------------- Properties
    /**
     * 当前组件使用的executor
     */
    protected BaseThreadPoolExecutor executor = null;

    /**
     * 使用的任务队列
     */
    private BaseTaskQueue taskqueue = null;

    private StandardThreadExecutorService.Builder builder = null;

    // ---------------------------------------------- Constructors
    protected StandardThreadExecutor(StandardThreadExecutorService.Builder builder) {
        Assert.notNull(builder, "builder can not be null");
        this.builder = builder;
        init(builder.getName());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
    }

    /**
     * 初始化这个component。
     */
    public void init(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("The name of the executor to init is empty, Please setting the name before.");
        }
        taskqueue = new BaseTaskQueue(getMaxQueueSize());
        BaseTaskThreadFactory tf = new BaseTaskThreadFactory(getNamePrefix(), isDaemon(), getThreadPriority());
        /*构造一个内部线程池*/
        executor = new BaseThreadPoolExecutor(getCoreThreads(), getMaxThreads(), getMaxIdleTime(), TimeUnit.MILLISECONDS, taskqueue, tf);
        if (executor != null) {
            executor.setKeepAliveTime(this.builder.getMaxIdleTime(), TimeUnit.MILLISECONDS);
            executor.setMaximumPoolSize(this.builder.getMaxThreads());
            executor.setCorePoolSize(this.builder.getCoreThreads());
        }
        if (isPrestartCoreThreads()) {
            executor.prestartAllCoreThreads();
        }
        taskqueue.setParent(executor);
    }

    /**
     * 停止组件
     */
    public void stop() {
        if (executor != null) {
            //直接关闭，停止运行中的任务，返回未开始的任务。
//            executor.shutdownNow();

            //平缓关闭，队列中的任务就会被忽略，
            executor.shutdown();
            try {
                executor.awaitTermination(ThreadsConstants.DEFAULT_EXECUTOR_STOP_AWAIT, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            //清除Builder的缓存
            StandardThreadExecutorService.Builder.removeCachedExecutor(getName());

        }
        executor = null;
        taskqueue = null;
    }

    /**
     * 把传入任务包装一层，使得当前的SecurityContext绑定到Runnable中。
     *
     * @param task
     * @return
     */
    protected Runnable wrap(Runnable task) {
        // never null
        SecurityContext sc = SecurityContextHolder.getContext();
        // 绑定context
        return DelegatingSecurityContextRunnable.create(task, sc);
    }

    /**
     * 将当前的SecurityContext绑定到Runnable中
     *
     * @param task
     * @return
     */
    protected <V> Callable<V> wrap(Callable<V> task) {
        // never null
        SecurityContext sc = SecurityContextHolder.getContext();
        // 绑定context
        return DelegatingSecurityContextCallable.create(task, sc);
    }


    @Override
    public void execute(Runnable task, long timeout, TimeUnit unit) {
        if (executor != null) {
            Runnable delegate = wrap(task);
            executor.execute(delegate, timeout, unit);
        } else {
            throw new IllegalStateException("StandardThreadExecutor not started.");
        }
    }

    @Override
    public void execute(Runnable task) {
        if (executor != null) {
            Runnable delegate = wrap(task);
            try {
                executor.execute(delegate);
            } catch (RejectedExecutionException rx) {
                // 如果任务被拒绝，则强制入队。如果还失败，则说明队列已满。
                if (!((BaseTaskQueue) executor.getQueue()).force(delegate)) {
                    throw new RejectedExecutionException("Queue capacity is full.");
                }
            }
        } else {
            throw new IllegalStateException("StandardThreadPool not started.");
        }
    }

    @Override
    public String getName() {
        return this.builder.getName();
    }

    public int getThreadPriority() {
        return this.builder.getThreadPriority();
    }

    public boolean isDaemon() {
        return this.builder.isDaemon();
    }

    public String getNamePrefix() {
        return this.builder.getNamePrefix();
    }

    public int getMaxIdleTime() {
        return this.builder.getMaxIdleTime();
    }

    @Override
    public int getMaxThreads() {
        return this.builder.getMaxThreads();
    }

    public int getCoreThreads() {
        return this.builder.getCoreThreads();
    }

    public boolean isPrestartCoreThreads() {
        return this.builder.isPrestartCoreThreads();
    }

    public int getMaxQueueSize() {
        return this.builder.getMaxQueueSize();
    }

    // Statistics from the thread pool
    @Override
    public int getActiveCount() {
        return (executor != null) ? executor.getActiveCount() : 0;
    }

    public long getCompletedTaskCount() {
        return (executor != null) ? executor.getCompletedTaskCount() : 0;
    }

    public int getCorePoolSize() {
        return (executor != null) ? executor.getCorePoolSize() : 0;
    }

    public int getLargestPoolSize() {
        return (executor != null) ? executor.getLargestPoolSize() : 0;
    }

    @Override
    public int getPoolSize() {
        return (executor != null) ? executor.getPoolSize() : 0;
    }

    public int getQueueSize() {
        return (executor != null) ? executor.getQueue().size() : -1;
    }

    public int getSubmittedCount() {
        return (executor != null) ? executor.getSubmittedCount() : -1;
    }

    protected BaseThreadPoolExecutor getExecutor() {
        return executor;
    }

    /**
     * 有任务执行的情况下，resize将无效
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @return
     * @see ThreadPoolExecutor#setCorePoolSize
     */
    @Override
    public boolean resizePool(int corePoolSize, int maximumPoolSize) {
        if (executor == null) {
            return false;
        }
        executor.setCorePoolSize(corePoolSize);
        executor.setMaximumPoolSize(maximumPoolSize);
        return true;
    }

    @Override
    public boolean resizeQueue(int capacity) {
        return false;
    }

}
