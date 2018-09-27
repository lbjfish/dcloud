/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityGoodsVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityGoodsMapper extends IMybatisDao<ActivityGoods> {
    List<ActivityGoodsVo> findVoList(@Param("po") ActivityGoods po);
    int checkMultiCountByUnique(@Param("po") ActivityGoods po);
    int checkRemovableByRel(@Param("ids") String ids);
    List<ActivityGoodsVo> findGoodsListByActivityId(@Param("activityId") String activityId);
    List<ActivityGoodsVo> findGoodsListByGroupId(@Param("groupId") String groupId);
}