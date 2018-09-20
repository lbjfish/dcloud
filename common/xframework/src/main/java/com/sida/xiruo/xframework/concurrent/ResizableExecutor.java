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
package com.sida.xiruo.xframework.concurrent;

/**
 * 表示该线程池是可以resize的
 */
public interface ResizableExecutor extends java.util.concurrent.Executor {

    /**
     * 返回当前线程池中的线程数量
     */
    public int getPoolSize();

    public int getMaxThreads();

    /**
     * 返回正在执行任务的线程数（大概）
     */
    public int getActiveCount();

    public boolean resizePool(int corePoolSize, int maximumPoolSize);

    public boolean resizeQueue(int capacity);

}
