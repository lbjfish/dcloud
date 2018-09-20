package com.sida.dcloud.schedule.job;

import org.springframework.stereotype.Component;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */
@Component("testJob")
public class TestJob {

    int count = 0;

    /**
     * 检查支付超时
     */
    public void execute() {
     /*   int temp = count++;
        System.out.println("count : "+temp);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("temp : "+temp);*/
       // List<UserEntity> listU = userClient.getUsers();
      //  System.out.println(listU.size());
    }

    /**
     * 测试任务一
     */
    public void testOne() {
        System.out.println("Test One");
    }


    /**
     * 测试任务二
     */
    public void testTwo() {
        System.out.println("Test Two");
    }
}
