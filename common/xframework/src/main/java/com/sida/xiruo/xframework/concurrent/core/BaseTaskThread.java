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

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * 拓展Thread类，加入创建时间。
 */
public class BaseTaskThread extends Thread {

    private static final Log log = LogFactory.getLog(BaseTaskThread.class);
    private final long creationTime;

    public BaseTaskThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        this.creationTime = System.currentTimeMillis();
    }

    public BaseTaskThread(ThreadGroup group, Runnable target, String name,
                          long stackSize) {
        super(group, target, name, stackSize);
        this.creationTime = System.currentTimeMillis();
    }

    /**
     * 返回线程的创建时间
     */
    public final long getCreationTime() {
        return creationTime;
    }

}
