package com.sida.dcloud.job.util;

import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.elastic.AbstractJob;
import com.sida.dcloud.job.elastic.dataflow.ExpiredOrderJob;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.elastic.consumer.ScanAndChangeOrderStatusConsumer;
import com.sida.dcloud.job.elastic.consumer.ThirdPartCodeConsumer;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class Bootstrap implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private ThirdPartCodeConsumer thirdPartCodeConsumer;
    @Autowired
    private ScanAndChangeOrderStatusConsumer scanAndChangeOrderStatusConsumer;
    @Autowired
    private ExpiredOrderJob expiredOrderJob;

    /**
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        //扫描订单改变订单状态
        createChangeOrderStatusJob();
        //发送验证码到第三方
        createThirdPartCodeJob();
        //延迟5秒，避免多任务同时启动造成瞬时高并发/高吞吐
        delayWithSecond(5);
        //获取未支付未超时订单创建超时任务
        createExpiredOrderJob();

    }

    private void createExpiredOrderJob() {
        //延迟3分钟执行
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(ExpiredOrderJob.class.getCanonicalName());
        jobEntity.setJobName("ExpiredOrderJob");
        jobEntity.setShardingTotalCount(1);
        LocalDateTime datetime = LocalDateTime.now();
        jobEntity.setJobCron(DateTimeFormatter.ofPattern(AbstractJob.CRON_DATE_FORMAT).format(datetime.plusMinutes(2)));
        expiredOrderJob.initJob(jobEntity);
    }

    private void createThirdPartCodeJob() {
        //实时性要求不高，每30分钟执行一次
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(ThirdPartCodeConsumer.class.getCanonicalName());
        jobEntity.setJobName("ThirdPartCodeJob");
        jobEntity.setLoop(true);
        jobEntity.setShardingTotalCount(1);
        jobEntity.setJobCron("* 0/30 * * * ?");
        thirdPartCodeConsumer.initJob(jobEntity);
    }

    private void createChangeOrderStatusJob() {
        //延迟2分钟执行
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(ScanAndChangeOrderStatusConsumer.class.getCanonicalName());
        jobEntity.setJobName("ChangeOrderStatusJob");
        jobEntity.setShardingTotalCount(1);
        LocalDateTime datetime = LocalDateTime.now();
        jobEntity.setJobCron(DateTimeFormatter.ofPattern(AbstractJob.CRON_DATE_FORMAT).format(datetime.plusMinutes(1)));
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
