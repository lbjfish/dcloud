package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysDictionaryDataMapper;
import com.sida.dcloud.system.po.SysDictionaryData;
import com.sida.dcloud.system.service.SysCacheVersionService;
import com.sida.dcloud.system.service.SysDictionaryDataService;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDictionaryDataServiceImpl extends BaseServiceImpl<SysDictionaryData> implements SysDictionaryDataService {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryDataServiceImpl.class);

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Autowired
    private SysCacheVersionService sysCacheVersionService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IMybatisDao<SysDictionaryData> getBaseDao() {
        return sysDictionaryDataMapper;
    }

    @Override
    public Page<SysDictionaryData> findPageList(SysDictionaryData param) {
        PageHelper.startPage(param.getP(), param.getS());
        return (Page<SysDictionaryData>) sysDictionaryDataMapper.selectByCondition(param);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addData(SysDictionaryData param) {
        //1、检查重复
        checkExistsSameCode(param);
        //2、更新数据库
        sysDictionaryDataMapper.insert(param);
        //3、更新缓存
        updateCache(param);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(SysDictionaryData param) {
        //1、检查重复
        checkExistsSameCode(param);
        //2、更新数据库
        sysDictionaryDataMapper.updateByPrimaryKeySelective(param);
        //3、更新缓存
        updateCache(param);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        //1、删除数据字典
        SysDictionaryData pos = new SysDictionaryData();
        pos.setDeleteFlag(true);
        sysDictionaryDataMapper.updateByIdsSelective(pos, ids);

        //2、更新缓存
        List<SysDictionaryData> dataList = sysDictionaryDataMapper.selectByPrimaryKeys(ids);
        if(BlankUtil.isNotEmpty(dataList)) {
            Map<String, Map<String, String>> groupMap = redisUtil.getEntriesFromMap(RedisKey.DICT_DATA);
            for (SysDictionaryData data : dataList) {
                if(groupMap.containsKey(data.getGroupCode())) {
                    groupMap.get(data.getGroupCode()).remove(data.getDicCode());
                }
            }
            redisUtil.putInMap(RedisKey.DICT_DATA, groupMap);
            sysCacheVersionService.updateCacheVersion(RedisKey.DICT_DATA);
        }
    }


    /**
     * 检查是否已存在重复的编码
     * @param param
     */
    private void checkExistsSameCode(SysDictionaryData param) {
        SysDictionaryData checkCondition = new SysDictionaryData();
        checkCondition.setDeleteFlag(false);
        checkCondition.setGroupCode(param.getGroupCode());
        checkCondition.setDicCode(param.getDicCode());
        List<SysDictionaryData> datas = sysDictionaryDataMapper.selectByCondition(checkCondition);
        if(BlankUtil.isNotEmpty(datas)) {
            if(datas.size()>1) {
                throw new ServiceException("0000", "编号已存在");
            }
            if(!datas.get(0).getId().equals(param.getId())) {
                throw new ServiceException("0000", "编号已存在");
            }
        }
    }

    /**
     * 更新字典缓存
     * @param data
     */
    private void updateCache(SysDictionaryData data) {
        //1、更新缓存
        Map<String, String> dataMap = redisUtil.getDicCodeNameMapByGroupCode(data.getGroupCode());
        dataMap.put(data.getDicCode(), data.getDicName());
        redisUtil.putInMap(RedisKey.DICT_DATA, data.getGroupCode(), dataMap);
        //2、更新缓存版本
        sysCacheVersionService.updateCacheVersion(RedisKey.DICT_DATA);
    }

    public Map<String, Map<String, String>> findAllForCache() {
        List<SysDictionaryData> dicts = sysDictionaryDataMapper.findAllForCache();
        //字典组，分组管理数据字典，sex:性别，   status:状态
        Map<String, Map<String, String>> groupMap = new HashMap<>();
        if(BlankUtil.isNotEmpty(dicts)) {
            //字典数据（[01:男，02:女]）
            Map<String, String> dataMap = new HashMap<>();
            for (SysDictionaryData dict : dicts) {
                if(groupMap.containsKey(dict.getGroupCode())) {
                    dataMap = groupMap.get(dict.getGroupCode());
                    dataMap.put(dict.getDicCode(), dict.getDicName());
                } else {
                    dataMap = new HashMap<>();
                    dataMap.put(dict.getDicCode(), dict.getDicName());
                    groupMap.put(dict.getGroupCode(), dataMap);
                }
            }
        }
        return groupMap;
    }

    @Override
    public void loadDictsToRedis() {
        //字典组，分组管理数据字典，sex:性别，status:状态
        Map<String, Map<String, String>> groupMap = findAllForCache();
        redisUtil.remove(RedisKey.DICT_DATA);
        redisUtil.putInMap(RedisKey.DICT_DATA, groupMap);
    }

}