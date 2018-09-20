package com.sida.xiruo.xframework.cache.redis;

import com.sida.xiruo.common.util.Xiruo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class JedisClusterConfig {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(JedisClusterConfig.class);
	private Set<HostAndPort> nodes;
	private JedisPoolConfig poolConfig;

	@Value("${common.redis.addresses}")String addresses;
//	@Value("${common.redis.password}")String password;
	@Value("${common.redis.isAuth}")boolean isAuth;
	@Value("${common.redis.connectionTimeout}")int connectionTimeout;
	@Value("${common.redis.soTimeout}")int soTimeout;
	@Value("${common.redis.maxRedirections}")int maxRedirections;

	@Value("${common.redis.pool.lifo}")boolean lifo;
	@Value("${common.redis.pool.maxTotal}")int maxTotal;
	@Value("${common.redis.pool.maxIdle}")int maxIdle;
	@Value("${common.redis.pool.minIdle}")int minIdle;
	@Value("${common.redis.pool.maxWaitMillis}")long maxWaitMillis;
	@Value("${common.redis.pool.testOnBorrow}")boolean testOnBorrow;
	@Value("${common.redis.pool.testOnReturn}")boolean testOnReturn;
	@Value("${common.redis.pool.testOnCreate}")boolean testOnCreate;
	@Value("${common.redis.pool.testWhileIdle}")boolean testWhileIdle;

	@PostConstruct
	public void init() {
		poolConfig = new JedisPoolConfig();
		poolConfig.setLifo(lifo);
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setTestOnCreate(testOnCreate);
		poolConfig.setTestOnReturn(testOnReturn);
		poolConfig.setTestWhileIdle(testWhileIdle);

		nodes = new HashSet<HostAndPort>();
		HostAndPort hap = null;
		String[] array = null;
		for(String s : addresses.split(",")) {
			array = StringUtils.split(s, ":");
			hap = new HostAndPort(array[0], Xiruo.nullToInt(array[1]));
			nodes.add(hap);
		}
	}

	@Bean
	public JedisCluster createJedisCluster() {
		if(isAuth) {
			return new JedisCluster(nodes,
					connectionTimeout,
					soTimeout,
					maxRedirections,
//					password,
					poolConfig);
		} else {
//			nodes.stream().forEach((HostAndPort pair) -> {
//				LOG.warn(String.format("%s=%d\r\n", pair.getHost(), pair.getPort()));
//			});
			return new JedisCluster(nodes,
					connectionTimeout,
					soTimeout,
					maxRedirections,
//					password,
					poolConfig);
		}
	}

	@Bean
	public RedisClusterConfiguration createRedisClusterConfiguration() {
		List<String> hostAndPorts = Arrays.asList(addresses.split(","));
//		LOG.warn("****************" + hostAndPorts.toString());
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(hostAndPorts);
		return redisClusterConfiguration;
	}

	@Bean
	public JedisConnectionFactory createJedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(createRedisClusterConfiguration(), poolConfig);
		if(isAuth) {
//			jedisConnectionFactory.setPassword(password);
		}
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate createRedisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(createJedisConnectionFactory());
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jsonRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@PreDestroy
	public void destroy() {
		if(null != nodes) {
			nodes.clear();
			nodes = null;
		}
	}
}
