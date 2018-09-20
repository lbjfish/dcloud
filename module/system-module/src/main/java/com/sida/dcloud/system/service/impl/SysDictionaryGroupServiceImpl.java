package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysDictionaryGroupMapper;
import com.sida.dcloud.system.po.SysDictionaryGroup;
import com.sida.dcloud.system.service.SysCacheVersionService;
import com.sida.dcloud.system.service.SysDictionaryGroupService;
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
public class SysDictionaryGroupServiceImpl extends BaseServiceImpl<SysDictionaryGroup> implements SysDictionaryGroupService {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryGroupServiceImpl.class);

    @Autowired
    private SysDictionaryGroupMapper sysDictionaryGroupMapper;

    @Autowired
    private SysCacheVersionService sysCacheVersionService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IMybatisDao<SysDictionaryGroup> getBaseDao() {
        return sysDictionaryGroupMapper;
    }

    @Override
    public Page<SysDictionaryGroup> findPageList(SysDictionaryGroup param) {
        PageHelper.startPage(param.getP(), param.getS());
        return (Page<SysDictionaryGroup>) sysDictionaryGroupMapper.selectByCondition(param);
    }


    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        //1、删除数据
        SysDictionaryGroup pos = new SysDictionaryGroup();
        pos.setDeleteFlag(true);
        sysDictionaryGroupMapper.updateByIdsSelective(pos, ids);

        //2、更新缓存
        List<SysDictionaryGroup> dataList = sysDictionaryGroupMapper.selectByPrimaryKeys(ids);
        if(BlankUtil.isNotEmpty(dataList)) {
            Object[] groupCodes = dataList.stream().map(e -> e.getGroupCode()).toArray();
            redisUtil.removeMultiFromMap(RedisKey.DICT_GROUP, groupCodes);
            sysCacheVersionService.updateCacheVersion(RedisKey.DICT_GROUP);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(SysDictionaryGroup param) {
        //1、检查重复编码
        checkExistsSameCode(param);
        //2、修改数据
        sysDictionaryGroupMapper.updateByPrimaryKeySelective(param);
        //3、更新缓存
        updateCache(param);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addGroup(SysDictionaryGroup param) {
        //1、检查重复编码
        checkExistsSameCode(param);
        //2、新增数据
        sysDictionaryGroupMapper.insert(param);
        //3、更新缓存
        updateCache(param);
    }

    /**
     * 检查是否已存在重复的编码
     * @param param
     */
    private void checkExistsSameCode(SysDictionaryGroup param) {
        SysDictionaryGroup checkCondition = new SysDictionaryGroup();
        checkCondition.setDeleteFlag(false);
        checkCondition.setGroupCode(param.getGroupCode());
        List<SysDictionaryGroup> groups = sysDictionaryGroupMapper.selectByCondition(checkCondition);
        if(BlankUtil.isNotEmpty(groups)) {
            if(groups.size()>1) {
                throw new ServiceException("0000", "编号已存在");
            }
            if(!groups.get(0).getId().equals(param.getId())) {
                throw new ServiceException("0000", "编号已存在");
            }
        }
    }

    /**
     * 更新字典缓存
     * @param data
     */
    private void updateCache(SysDictionaryGroup data) {
        //1、更新缓存
        redisUtil.putInMap(RedisKey.DICT_GROUP, data.getGroupCode(), data.getGroupName());
        //2、更新缓存版本
        sysCacheVersionService.updateCacheVersion(RedisKey.DICT_GROUP);
    }


    public Map<String, String> findAllForCache() {
        List<SysDictionaryGroup> groups = sysDictionaryGroupMapper.findAllForCache();
        Map<String, String> map = new HashMap<>();
        if(BlankUtil.isNotEmpty(groups)) {
            for(SysDictionaryGroup group : groups) {
                map.put(group.getGroupCode(), group.getGroupName());
            }
        }
        return map;
    }

    @Override
    public void loadDictGroupsToRedis() {
        Map<String, String> map = findAllForCache();
        redisUtil.remove(RedisKey.DICT_GROUP);
        redisUtil.putInMap(RedisKey.DICT_GROUP, map);
    }

}