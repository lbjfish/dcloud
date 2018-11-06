package com.sida.dcloud.job.util;

import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.simple.consumer.ScanAndChangeOrderStatusConsumer;
import com.sida.dcloud.job.simple.consumer.ThirdPartCodeConsumer;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class Bootstrap implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private ThirdPartCodeConsumer thirdPartCodeConsumer;
    @Autowired
    private ScanAndChangeOrderStatusConsumer scanAndChangeOrderStatusConsumer;

    /**
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        //发送验证码到第三方
//        createThirdPartCodeJob();
        //延迟5秒，避免多任务同时启动造成瞬时高并发/高吞吐
        delayWithSecond(5);
        //扫描订单改变订单状态
//        createChangeOrderStatusJob();
    }

    private void createThirdPartCodeJob() {
        //实时性要求不高，每5分钟执行一次
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(UUIDGenerate.getNextId());
        jobEntity.setJobName("SimpleJob - SendCodeToThirdPart");
        jobEntity.setShardingTotalCount(1);
        jobEntity.setJobCron("* 0/5 * * * ?");
        thirdPartCodeConsumer.initJob(jobEntity);
    }

    private void createChangeOrderStatusJob() {
        //实时性要求不高，每5分钟执行一次
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(UUIDGenerate.getNextId());
        jobEntity.setJobName("SimpleJob - ChangeOrderStatus");
        jobEntity.setShardingTotalCount(1);
        jobEntity.setJobCron("* 0/5 * * * ?");
        scanAndChangeOrderStatusConsumer.initJob(jobEntity);
    }

    private void delayWithSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch(InterruptedException e) {
            LOG.error("延迟时发生异常: ", e);
            throw new JobException(e);
        }
    }
}
