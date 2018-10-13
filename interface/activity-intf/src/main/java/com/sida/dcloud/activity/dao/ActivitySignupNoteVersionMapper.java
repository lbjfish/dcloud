/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivitySignupNoteVersion;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import feign.Param;

public interface ActivitySignupNoteVersionMapper extends IMybatisDao<ActivitySignupNoteVersion> {
    int insertVersion(@Param("po") ActivitySignupNoteVersion po);
    int deleteByVersion(@Param("version") String version);
    int resumeByVersion(@Param("version") String version);
    ActivitySignupNoteVersion getCurrentUsedVerion();
    ActivitySignupNoteVersion selectByVerion(@Param("version") String version);
    void setCurrentUsedVersion(@Param("version") String version);
}