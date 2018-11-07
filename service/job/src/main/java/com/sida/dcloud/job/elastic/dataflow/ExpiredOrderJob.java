package com.sida.dcloud.job.elastic.dataflow;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.client.ActivityClient;
import com.sida.dcloud.job.elastic.ConFunJob;
import com.sida.dcloud.job.elastic.consumer.UpdateOrderStatusConsumer;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.util.JobUtil;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ExpiredOrderJob extends AbstractJobConFun {
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    @Autowired
    private ActivityClient activityClient;
    @Autowired
    private UpdateOrderStatusConsumer updateOrderStatusConsumer;
    @Autowired
    private JobUtil jobUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void accept(ShardingContext shardingContext, List<Map<String, String>> maps) {
        maps.stream().filter(map -> !jobUtil.exists(map.get("note_id"))).forEach(map -> {
            JobEntity jobEntity = new JobEntity();
            jobEntity.setId(map.get("note_id"));
            LocalDateTime datetime = LocalDateTime.ofInstant(Xiruo.stringToDate(map.get("create_time")).toInstant(), ZoneId.systemDefault());
            //全局支付过期时间（分钟）
            Integer payExpired =
                    Integer.parseInt(Optional.ofNullable(redisUtil.getGlobalVariableValueByCode("pay_expired")).orElse("60"));
            jobEntity.setJobCron(DateTimeFormatter.ofPattern(CRON_DATE_FORMAT).format(datetime.plusMinutes(payExpired)));
            jobEntity.setShardingTotalCount(1);
            jobEntity.setParamMap(new HashMap<String, String>() {{put("orderId", map.get("id"));put("orderStatus", "3");}});
            updateOrderStatusConsumer.initJob(jobEntity);
            LOG.info("启动定时任务 - 支付过期 - {}", jobEntity.getJobCron());
        });
    }

    @Override
    public List<Map<String, String>> apply(ShardingContext shardingContext) {
        return (List<Map<String, String>>)activityClient.selectUnpayOrderList();
    }
}
