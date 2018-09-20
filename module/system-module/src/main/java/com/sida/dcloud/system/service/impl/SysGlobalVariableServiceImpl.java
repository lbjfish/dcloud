package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.system.dao.SysGlobalVariableMapper;
import com.sida.dcloud.system.po.SysGlobalVariable;
import com.sida.dcloud.system.service.SysGlobalVariableService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysGlobalVariableServiceImpl extends BaseServiceImpl<SysGlobalVariable> implements SysGlobalVariableService {
    private static final Logger logger = LoggerFactory.getLogger(SysGlobalVariableServiceImpl.class);

    @Autowired
    private SysGlobalVariableMapper sysGlobalVariableMapper;

    @Override
    public IMybatisDao<SysGlobalVariable> getBaseDao() {
        return sysGlobalVariableMapper;
    }

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Page<SysGlobalVariable> findPageList(SysGlobalVariable param) {
        //过滤逻辑删
        param.setDeleteFlag(false);
        //设置默认排序
        if (StringUtils.isBlank(param.getOrderField())){
            param.setOrderField(SecConstant.CREATED_AT);
            param.setOrderString(SecConstant.DESC);
        }
        PageHelper.startPage(param.getP(),param.getS());
        List<SysGlobalVariable> list = sysGlobalVariableMapper.selectByCondition(param);
        return (Page) list;

    }

    @Override
    public List<SysGlobalVariable> selectByCondition() {
        SysGlobalVariable po = new SysGlobalVariable();
        po.setDeleteFlag(false);
        return selectByCondition(po);
    }

    @Override
    public void loadDatasToRedis() {
        redisUtil.remove(RedisKey.GLOBAL_VARIABLE);
        List<SysGlobalVariable> list = selectByCondition();
        if(BlankUtil.isNotEmpty(list)) {
            Map<String, String> codeValuePairMap = new HashMap<>();
            list.forEach(po -> codeValuePairMap.put(po.getCode(), po.getValue()));
            redisUtil.putInMap(RedisKey.GLOBAL_VARIABLE, codeValuePairMap);
        }
    }
}