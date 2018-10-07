package com.sida.dcloud.activity.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread implements Runnable {
        static volatile int i = 0;
//    static AtomicInteger ai=new AtomicInteger(0);
    static final byte[] lock = new byte[0];
    ReentrantLock rLock = new ReentrantLock();

    public void run() {
//        rLock.lock();
            for (int m = 0; m < 100000; m++) {
//            ai.getAndIncrement();

                i++;
            }
//        rLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread mt = new MyThread();

        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        Thread.sleep(500);
//        System.out.println(MyThread.ai.get());
        System.out.println(i);
    }
}