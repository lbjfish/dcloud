/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrainingUserSettingMapper extends IMybatisDao<TrainingUserSetting> {
    List<TrainingUserSetting> selectByUserId(@Param("userId")String userId);
}