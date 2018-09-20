/**
 * create by yjr
 * @date 2017-08
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysTrainingfieldModels;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTrainingfieldModelsMapper extends IMybatisDao<SysTrainingfieldModels> {
    /**
     * 删除关联关系
     * @param trainingfieldId
     */
    void deleteByFieldId(@Param(value = "trainingfieldId") String trainingfieldId);

    /**
     * 批量插入
     * @param modelList
     */
    void insertBatch(@Param(value = "modelList") List<SysTrainingfieldModels> modelList);
}