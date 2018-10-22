/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityInfoMapper extends IMybatisDao<ActivityInfo> {
    List<ActivityInfoVo> findVoList(@Param("po") ActivityInfo po);
    List<ActivityInfo> selectSelfAndChildrenByPrimaryKey(@Param("id")String id);
    int checkMultiCountByUnique(@Param("po")ActivityInfo po);
    int checkRemovableByRel(@Param("ids") String ids);
    int checkCountForActivityStatus(@Param("ids") String ids, @Param("activityStatus") String activityStatus);
    int updateActivityStatus(@Param("activityId")String activityId, @Param("activityStatus")String activityStatus);
    void increaseCommentCount(@Param("activityId")String activityId, @Param("count")int count);
    void increaseSignCount(@Param("activityId")String activityId, @Param("count")int count);
    void increaseFavoriteCount(@Param("activityId")String activityId, @Param("count")int count);
}