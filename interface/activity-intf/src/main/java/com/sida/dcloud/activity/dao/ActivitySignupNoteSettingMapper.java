/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import feign.Param;

import java.util.List;

public interface ActivitySignupNoteSettingMapper extends IMybatisDao<ActivitySignupNoteSetting> {
    void batchInsert(@Param("list") List<ActivitySignupNoteSetting> settingList);
    List<ActivitySignupNoteSetting> selectByVersion(@Param("version") String version);
    int deleteByVersion(@Param("version") String version);
    int resumeByVersion(@Param("version") String version);
}