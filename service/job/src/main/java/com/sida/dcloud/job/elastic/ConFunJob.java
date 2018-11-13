package com.sida.dcloud.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ConFunJob extends BiConsumer<ShardingContext, List<Map<String, String>>>, Function<ShardingContext, List<Map<String, String>>> {
}
