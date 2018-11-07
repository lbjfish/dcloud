package com.sida.dcloud.job.elastic.consumer;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.elastic.AbstractJob;

import java.util.function.Consumer;

public abstract class AbstractJobConsumer extends AbstractJob implements Consumer<ShardingContext> {

}
