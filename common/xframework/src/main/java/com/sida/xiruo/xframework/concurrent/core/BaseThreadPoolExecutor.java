package com.sida.xiruo.xframework.concurrent.core;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 只是比juc的ThreadPoolExecutor实现多了一个更有效的getSubmittedCount()方法。
 * 这个submittedCount将在workqueue中用于判断入队情况。{@link BaseTaskQueue#offer}
 * Same as a java.util.concurrent.ThreadPoolExecutor but implements a much more efficient
 * {@link #getSubmittedCount()} method, to be used to properly handle the work queue.
 * If a RejectedExecutionHandler is not specified a default one will be configured
 * and that one will always throw a RejectedExecutionException
 *
 */
public class BaseThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 提交了的，但是还没有完成的任务数量。包括了在队列中的任务，和正在被执行的任务。
     * 这个数量总是大于等于getActiveCount(){@link #getActiveCount()}.
     */
    private final AtomicInteger submittedCount = new AtomicInteger(0);

    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new RejectHandler());
    }

    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new RejectHandler());
    }


    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        submittedCount.decrementAndGet();

//        if (t == null) {
//            stopCurrentThreadIfNeeded();
//        }
    }


    public int getSubmittedCount() {
        return submittedCount.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable command) {
        execute(command,0,TimeUnit.MILLISECONDS);
    }

    /**
     * 如果没有可用线程，将入队。如果队列满了，阻塞一定时间内仍然满队，则抛出RejectedExecutionException
     *
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution - the queue is full
     * @throws NullPointerException if command or unit is null
     */
    public void execute(Runnable command, long timeout, TimeUnit unit) {
        submittedCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException rx) {
            if (super.getQueue() instanceof BaseTaskQueue) {
                final BaseTaskQueue queue = (BaseTaskQueue)super.getQueue();
                try {
                    // Rejected就强制入队，再失败就说明队列满了
                    if (!queue. force(command, timeout, unit)) {
                        submittedCount.decrementAndGet();
                        throw new RejectedExecutionException("Queue capacity is full.");
                    }
                } catch (InterruptedException x) {
                    submittedCount.decrementAndGet();
                    throw new RejectedExecutionException(x);
                }
            } else {
                submittedCount.decrementAndGet();
                throw rx;
            }

        }
    }

    private static class RejectHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r,
                ThreadPoolExecutor executor) {
            //处理任务拒绝时的情况，这里先抛出一个异常
            throw new RejectedExecutionException();
        }

    }

}
