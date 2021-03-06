package com.sida.dcloud.operation.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.sida.dcloud.operation.dao.CommonUserMapper;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.CommonUser;
import com.sida.dcloud.operation.service.CommonUserService;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonUserServiceImpl extends BaseServiceImpl<CommonUser> implements CommonUserService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUserServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonUserMapper commonUserMapper;

    @Override
    public IMybatisDao<CommonUser> getBaseDao() {
        return commonUserMapper;
    }

    @Override
    public Map<String, String> selectByPrimaryKeyToAuth(String id) {
        Map<String, Object> map = (Map<String, Object>)redisUtil.getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        Map<String, String> resultMap = commonUserMapper.selectByPrimaryKeyToAuth(id);
        if(map != null) {
            if(BlankUtil.isNotEmpty(resultMap.get("region_id"))) {
                resultMap.put("region_name", ((SysRegionLayerDto) map.get(resultMap.get("region_id"))).getName());
            }
        }
        return resultMap;
    }

    @Override
    public int saveOrUpdateDto(CommonUserOperation dto) {
        return commonUserMapper.saveOrUpdateDto(dto);
    }

    @Override
    public int updateUserInfo(CommonUserOperation dto) {
        return commonUserMapper.updateUserInfo(dto);
    }

    @Override
    public int bindThirdPartAccount(CommonUserOperation dto) {
        return commonUserMapper.bindThirdPartAccount(dto);
    }

    @Override
    public int unbindThirdPartAccount(String loginFrom, String mobile) {
        return commonUserMapper.unbindThirdPartAccount(loginFrom, mobile);
    }

    @TxTransaction
    @Override
    public int updateFaceUrl(Map<String, String> map) {
        return commonUserMapper.updateFaceUrl(map);
    }

    @Override
    public int testDistributeTransaction(String id, String remark) {
        return commonUserMapper.testDistributeTransaction(id, remark);
    }
}