/**
 * create by yjr
 * @date 2017-08
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysTrainingfield;
import com.sida.dcloud.system.vo.SysTrainingfieldVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTrainingfieldMapper extends IMybatisDao<SysTrainingfield> {
    /**
     * 获取列表
     * @param param
     * @return
     */
    List<SysTrainingfieldVo> findList(SysTrainingfieldVo param);

    /**
     * 获取vo
     * @param id
     * @return
     */
    SysTrainingfieldVo selectVoByPrimaryKey(@Param(value = "id") String id);
}