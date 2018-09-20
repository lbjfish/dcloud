package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysDictionaryMapper;
import com.sida.dcloud.system.po.SysDictionary;
import com.sida.dcloud.system.service.SysDictionaryService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictionaryServiceImpl extends BaseServiceImpl<SysDictionary> implements SysDictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryServiceImpl.class);

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Override
    public IMybatisDao<SysDictionary> getBaseDao() {
        return sysDictionaryMapper;
    }

    @Override
    public int deleteById(String id) {
        return sysDictionaryMapper.deleteById(id);
    }

    @Override
    public List<SysDictionary> selectByPCode(String pCode) {
        return sysDictionaryMapper.selectByPCode(pCode);
    }

    @Override
    public List<SysDictionary> selectByPid(String pid) {
        return sysDictionaryMapper.selectByPid(pid);
    }

    @Override
    public void loadDictsToRedis() {
        //todo 已废弃，SysDictionaryData代替
        /*List<SysDictionary> dicts = sysDictionaryMapper.findAllDicts();
        if(BlankUtil.isNotEmpty(dicts)) {
            //数据字典表
            Map<String, String> dictMap = new HashMap<>();

            //组容器，分组管理数据字典，性别，状态，类型，颜色
            Map<String, Map<String, String>> groupMap = new HashMap<>();
            //组
            Map<String, String> group = new HashMap<>();

            // type和dicKey的对应表
            // 先用的type作为group的key来集合元素，后面会换成dictKey作为redis的键
            Map<String, String> typeToDicKey = new HashMap<>();

            for (SysDictionary dict : dicts) {
                if(groupMap.containsKey(dict.getType())) {
                    group = groupMap.get(dict.getType());
                    group.put(dict.getId(), dict.getDicValue());
                } else {
                    group = new HashMap<>();
                    group.put(dict.getId(), dict.getDicValue());
                    groupMap.put(dict.getType(), group);
                }
                if(dict.getParentId().equals("0")) {
                    typeToDicKey.put(dict.getId(), dict.getDicKey());
                }
                dictMap.put(dict.getId(), dict.getDicValue());
            }


            // 分组缓存数据字典(将dicKey代替type作为redis key保存数据字典)
            for(String key : groupMap.keySet()) {
                if("0".equals(key)) {
                    jedisCluster.hmset(RedisKey.DICT_DATA +"_0", groupMap.get(key));
                } else {
                    jedisCluster.hmset(RedisKey.DICT_DATA +"_"+typeToDicKey.get(key), groupMap.get(key));
                }
            }

            // 缓存所有数据字典
            jedisCluster.hmset(RedisKey.DICT_DATA, dictMap);

        }*/

    }
}