package com.sida.xiruo.xframework.cache.redis;

import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 * Created by huangbaidong
 * 2017/10/24 21:02
 */
@Component
public class RedisUtil {
    private final static LocalTime DEFAULT_TIMEOUT_SECOND_POINT = LocalTime.of(2,0,0);
    private RedisTemplate<Serializable, Object> redisTemplate;

    public String getGlobalVariableValueByCode(String code) {
        Map<String, String> valueMap;
        if((valueMap = getEntriesFromMap(RedisKey.GLOBAL_VARIABLE)) != null) {
            return valueMap.get(code);
        }
        return null;
    }

    /**
     * 通过分组编码得到分组下所有数据字典键值对（01:男，02:女）
     * 主要用于数据导出时，数据字典键转成excel需要的值
     * @param groupCode  数据分组编码，例：sex
     * @return
     */
    public Map<String, String> getDicCodeNameMapByGroupCode(String groupCode) {
        if(BlankUtil.isNotEmpty(groupCode)) {
            return (Map<String, String>)getOneByMapKey(RedisKey.DICT_DATA, groupCode);
        }
        return new HashMap<>();
    }

    /**
     * 通过分组编码得到分组下所有数据字典“值键”对,即键值倒转(男:01，女:02)
     * 主要用于导入数据时，excel值转换成数据字典键
     * @param groupCode  数据分组编码，例：sex
     * @return
     */
    public Map<String, String> getDicNameCodeMapByGroupCode(String groupCode) {
        Map<String, String> keyValueMap = getDicCodeNameMapByGroupCode(groupCode);
        if(BlankUtil.isNotEmpty(keyValueMap)) {
            Map<String, String> valueKeyMap = new HashMap();
            for (String key : keyValueMap.keySet()) {
                valueKeyMap.put(keyValueMap.get(key), key);
            }
            return valueKeyMap;
        }
        return new HashMap<>();
    }


    /**
     * 通过分组编码得到分组下的所有数据字典名称
     * 主要用于数据导入时，字典选项合法性校验
    *  @param groupCode  数据分组编码，例：sex
     * @return ["男","女"]
     */
    public Collection<String> getDicNamesByParentCode(String groupCode) {
        Map<String, String> map = getDicCodeNameMapByGroupCode(groupCode);
        if(BlankUtil.isNotEmpty(map)) {
            return map.values();
        }
        return new ArrayList<>();
    }

    /**
     * 通过分组编码得到分组下的所有数据字典编码
     * 主要用于表单提交时，字典选项合法性校验
     *  @param groupCode  数据分组编码，例：sex
     * @return  ["01","02"]
     */
    public Collection<String> getDicCodesByParentCode(String groupCode) {
        Map<String, String> map = getDicCodeNameMapByGroupCode(groupCode);
        if(BlankUtil.isNotEmpty(map)) {
            return map.keySet();
        }
        return new ArrayList<>();
    }

    /**
     * 获取组织机构缓存数据
     * @return
     */
    public Map<String,String> getOrgMap() {
        return getEntriesFromMap(RedisKey.ORG_MAP);
    }

    /**
     * 组织机构“值键”对,即键值倒转(宝安店:01，南山片区:02)
     * 主要用于导入数据时，excel值转换成数据字典键
     * @return
     */
    public Map<String,String> getOrgMapNameIdMap() {
        Map<String, String> keyValueMap = getOrgMap();
        if(BlankUtil.isNotEmpty(keyValueMap)) {
            Map<String, String> valueKeyMap = new HashMap();
            for (String key : keyValueMap.keySet()) {
                valueKeyMap.put(keyValueMap.get(key), key);
            }
            return valueKeyMap;
        }
        return null;
    }

    /**getDicGroupMap
     * 获取所有数据字典分组
     * @return
     */
    public Map<String, String> getDicGroupMap() {
        return getEntriesFromMap(RedisKey.DICT_GROUP);
    }

    public Map<String,String> getCacheVersion() {
        return getEntriesFromMap(RedisKey.CACHE_VERSION);
    }







    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 设置新值，同时返回旧值
     * @param lockKey
     * @param stringOfLockExpireTime
     * @return
     */
    public String getSet(final String lockKey, final String stringOfLockExpireTime) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] bytes = redisConnection.getSet(lockKey.getBytes(), stringOfLockExpireTime.getBytes());
                if(bytes != null) {
                    return new String(bytes);
                }
                return null;
            }
        });
        return result;
    }

    /**
     * 如果不存在key则插入
     * @param lockKey
     * @param stringOfLockExpireTime
     * @return true 插入成功， false 插入失败
     */
    public boolean setnx(final String lockKey, final String stringOfLockExpireTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.setNX(lockKey.getBytes(), stringOfLockExpireTime.getBytes());
            }
        });
    }


    /**
     * setnx 和 getSet方式插入的数据，调用此方法获取
     * @param key
     * @return
     */
    public String getInExecute(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] bytes = redisConnection.get(key.getBytes());
                if (bytes == null) {
                    return null;
                } else {
                    return new String(bytes);
                }
            }
        });
        return result;
    }


    /**
     * 将缓存保存在map集合中
     * @param redisKey
     * @param mapKey
     * @param mapValue
     * @return
     */
    public boolean putInMap(final String redisKey, String mapKey, Object mapValue) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
            operations.put(redisKey, mapKey, mapValue);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将缓存保存在map集合中
     * @param redisKey
     * @param map
     * @return
     */
    public boolean putInMap(final String redisKey, Map map) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
            operations.putAll(redisKey, map);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过key从Map集合中获取value
     * @param redisKey
     * @param mapKey
     * @return
     */
    public Object getOneByMapKey(final String redisKey, String mapKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.get(redisKey, mapKey);
    }

    /**
     * 通过多个key从Map集合获取多个value
     * @param redisKey
     * @return
     */
    public Object getMultiByMapKeys(final String redisKey, Collection mapKeys) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.multiGet(redisKey, mapKeys);
    }

    /**
     * 获取Map集合的所有value
     * @param redisKey
     * @return
     */
    public Object getAllFromMap(final String redisKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.values(redisKey);
    }

    /**
     * 获取Map集合的所有值
     * @param redisKey
     * @return
     */public Map getEntriesFromMap(final String redisKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.entries(redisKey);
    }


    /**
     * 删除Map中的一个键值对
     * @param redisKey
     * @param mapKey
     */
    public void removeOneFromMap(final String redisKey, Object mapKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        operations.delete(redisKey, mapKey);
    }

    /**
     * 删除Map中的多个键值对
     * @param redisKey
     * @param mapKeys
     */
    public void removeMultiFromMap(final String redisKey, Object...mapKeys) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        operations.delete(redisKey, mapKeys);
    }

    /**
     * 添加一条记录到List集合
     * @param redisKey
     * @param value
     * @return
     */
    public boolean addInList(final String redisKey, Object value) {
        boolean result = false;
        try {
            ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
            listOperations.leftPush(redisKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 得到List集合所有记录
     * @param redisKey
     * @return
     */
    public Object getList(final String redisKey) {
        ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range(redisKey,0,listOperations.size(redisKey));
    }


    public RedisTemplate<Serializable, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        this.redisTemplate = redisTemplate;
    }
}
