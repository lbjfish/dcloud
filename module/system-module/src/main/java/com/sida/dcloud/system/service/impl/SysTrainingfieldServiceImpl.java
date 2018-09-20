package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysTrainingfieldMapper;
import com.sida.dcloud.system.dao.SysTrainingfieldModelsMapper;
import com.sida.dcloud.system.po.SysTrainingfield;
import com.sida.dcloud.system.po.SysTrainingfieldModels;
import com.sida.dcloud.system.service.SysTrainingfieldService;
import com.sida.dcloud.system.vo.SysTrainingfieldVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.sida.xiruo.xframework.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysTrainingfieldServiceImpl extends BaseServiceImpl<SysTrainingfield> implements SysTrainingfieldService {
    private static final Logger logger = LoggerFactory.getLogger(SysTrainingfieldServiceImpl.class);

    @Autowired
    private SysTrainingfieldMapper sysTrainingfieldMapper;
    @Autowired
    private SysTrainingfieldModelsMapper sysTrainingfieldModelsMapper;

    @Override
    public IMybatisDao<SysTrainingfield> getBaseDao() {
        return sysTrainingfieldMapper;
    }

    @Override
    public Page<SysTrainingfieldVo> findPageList(SysTrainingfieldVo param) {
        PageHelper.startPage(param.getP(),param.getS());
        List<SysTrainingfieldVo> list = sysTrainingfieldMapper.findList(param);
        return (Page) list;
    }

    @Override
    @Transactional
    public void save(SysTrainingfieldVo param) {
        String action = param.getId();
        //设置通用参数
        PoDefaultValueGenerator.putDefaultValue(param);
        //删除关联关系
        sysTrainingfieldModelsMapper.deleteByFieldId(param.getId());
        //构建关联关系
        if (BlankUtil.isNotEmpty(param.getCarModels())){
            List<SysTrainingfieldModels> modelList = Lists.newArrayList();
            String[] models = StringUtil.split(param.getCarModels(),",");
            for (int i=0; i<models.length; i++){
                SysTrainingfieldModels model = new SysTrainingfieldModels();
                model.setTrainingfieldId(param.getId());
                model.setModelsId(models[i]);
                modelList.add(model);
            }
            if (BlankUtil.isNotEmpty(modelList)){
                sysTrainingfieldModelsMapper.insertBatch(modelList);
            }
        }
        //新增
        if (BlankUtil.isEmpty(action)){
            sysTrainingfieldMapper.insertSelective(param);
        }//更新
        else {
            sysTrainingfieldMapper.updateByPrimaryKeySelective(param);
        }
    }

    @Override
    public SysTrainingfieldVo selectVoByPrimaryKey(String id) {
        return sysTrainingfieldMapper.selectVoByPrimaryKey(id);
    }
}