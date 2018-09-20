/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sida.xiruo.xframework.concurrent.core;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 专门被设计成用于线程池executor的一个任务队列。
 * 这个任务队列被优化成可以正确地（properly）利用在线程池executor中的线程。
 * 如果使用一个普通的队列，executor在有空闲线程的时候仍然会大量生产线程，这样你就不能将任务入队了。(这里好像不对。。。。)
 * As task queue specifically designed to run with a thread pool executor. The
 * task queue is optimised to properly utilize threads within a thread pool
 * executor. If you use a normal queue, the executor will spawn threads when
 * there are idle threads and you wont be able to force items onto the queue
 * itself.
 */
public class BaseTaskQueue extends LinkedBlockingQueue<Runnable> {

    private static final long serialVersionUID = 1L;

    private volatile BaseThreadPoolExecutor parent = null;

    // No need to be volatile. This is written and read in a single thread
    // (when stopping a context and firing the  listeners)
    private Integer forcedRemainingCapacity = null;

    public BaseTaskQueue() {
        super();
    }

    public BaseTaskQueue(int capacity) {
        super(capacity);
    }

    public BaseTaskQueue(Collection<? extends Runnable> c) {
        super(c);
    }

    public void setParent(BaseThreadPoolExecutor tp) {
        parent = tp;
    }

    public boolean force(Runnable o) {
        if (parent == null || parent.isShutdown()) {
            throw new RejectedExecutionException("Executor not running, can't force a command into the queue!");
        }
        // 注意这里调用的是父类的offer，不是调用override的offer
        return super.offer(o);
    }

    public boolean force(Runnable o, long timeout, TimeUnit unit) throws InterruptedException {
        if (parent == null || parent.isShutdown()) {
            throw new RejectedExecutionException("Executor not running, can't force a command into the queue!");
        }
        return super.offer(o, timeout, unit);
    }

    /**
     * 复写队列的offer方法，使得在当前线程数小于最大线程数的时候，创建线程。
     * - 当前线程数 == 最大线程数 》》 入队
     * - 有空闲线程 》》   入队
     * - 当前线程数 < 最大线程数 》》 false，使得创建线程
     * - 其他情况入队
     *
     * 这个方法会在StandardThreadExecutor的内部executor执行execute的时候被调用。
     * @param o
     * @return
     */
    @Override
    public boolean offer(Runnable o) {
        //we can't do any checks
        if (parent == null) {
            return super.offer(o);
        }
        // 达到最大线程数，入队。
        if (parent.getPoolSize() == parent.getMaximumPoolSize()) {
            return super.offer(o);
        }
        //有空闲线程，还是添加到队列中。但是只要一加到队列中就会被空闲线程领走
        if (parent.getSubmittedCount() < (parent.getPoolSize())) {
            return super.offer(o);
        }
        // 没有空闲线程了，并且未达到最大线程数，返回false，使executor调用addWorker来增加线程
        if (parent.getPoolSize() < parent.getMaximumPoolSize()) {
            return false;
        }
        // 其他情况入队
        return super.offer(o);
    }


    /**
     * 出队
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public Runnable poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        return super.poll(timeout, unit);
    }

    /**
     * 等待出队
     * @return
     * @throws InterruptedException
     */
    @Override
    public Runnable take() throws InterruptedException {
//        if (parent != null && parent.currentThreadShouldBeStopped()) {
        if (parent != null) {
            return poll(parent.getKeepAliveTime(TimeUnit.MILLISECONDS),
                    TimeUnit.MILLISECONDS);
            // yes, this may return null (in case of timeout) which normally
            // does not occur with take()
            // but the ThreadPoolExecutor implementation allows this
        }
        return super.take();
    }

    @Override
    public int remainingCapacity() {
        if (forcedRemainingCapacity != null) {
            // ThreadPoolExecutor.setCorePoolSize checks that
            // remainingCapacity==0 to allow to interrupt idle threads
            // I don't see why, but this hack allows to conform to this
            // "requirement"
            return forcedRemainingCapacity.intValue();
        }
        return super.remainingCapacity();
    }

    public void setForcedRemainingCapacity(Integer forcedRemainingCapacity) {
        this.forcedRemainingCapacity = forcedRemainingCapacity;
    }

}
