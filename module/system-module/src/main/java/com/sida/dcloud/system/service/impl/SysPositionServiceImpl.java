package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.vo.EmpListConditionDTO;
import com.sida.dcloud.auth.vo.SysPositionVo;
import com.sida.dcloud.system.dao.SysEmployeeMapper;
import com.sida.dcloud.system.dao.SysPositionMapper;
import com.sida.dcloud.system.service.SysPositionService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
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
public class SysPositionServiceImpl extends BaseServiceImpl<SysPosition> implements SysPositionService {
    private static final Logger logger = LoggerFactory.getLogger(SysPositionServiceImpl.class);

    @Autowired
    private SysPositionMapper sysPositionMapper;
    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Override
    public IMybatisDao<SysPosition> getBaseDao() {
        return sysPositionMapper;
    }

    @Override
    public Page<SysPositionVo> findPageList(SysPosition param){
        //过滤逻辑删
        param.setDeleteFlag(false);
        //设置默认排序
        if (StringUtils.isBlank(param.getOrderField())){
            param.setOrderField(SecConstant.CREATED_AT);
            param.setOrderString(SecConstant.DESC);
        }
        PageHelper.startPage(param.getP(),param.getS());
        List<SysPositionVo> list = sysPositionMapper.findVoList(param);
        return (Page) list;
    }

    @Override
    public Page<SysEmployee> findEmpListByPositionId(EmpListConditionDTO param) {
        PageHelper.startPage(param.getP(),param.getS());
        List<SysEmployee> list = sysEmployeeMapper.findEmpListByPositionId(param);
        return (Page) list;
    }

    @Override
    @Transactional
    public int savePosition(SysPosition po) {
        Boolean disable = po.getDisable();
        if (StringUtils.isBlank(po.getId())){
            PoDefaultValueGenerator.putDefaultValue(po);
            po.setDisable(disable);
            return getBaseDao().insertSelective(po);
        }else {
            PoDefaultValueGenerator.putDefaultValue(po);
            return getBaseDao().updateByPrimaryKeySelective(po);
        }
    }

    @Override
    public Map<String, String> findNewRoleCode(List<String> posiIdList) {
        Map<String, String> map = new HashMap<>();
        //获取岗位列表
        List<SysPosition> list = sysPositionMapper.findNewRoleCode(posiIdList);
        if (BlankUtil.isNotEmpty(list)){
            for (SysPosition po : list){
                map.put(po.getId(),po.getCode());
            }
        }
        return map;
    }
}