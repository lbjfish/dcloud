/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityGoodsGroup;
import com.sida.dcloud.activity.vo.ActivityGoodsGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityGoodsGroupMapper extends IMybatisDao<ActivityGoodsGroup> {
    List<ActivityGoodsGroupVo> findVoList(@Param("po") ActivityGoodsGroup po);
    int checkMultiCountByUnique(@Param("po") ActivityGoodsGroup po);
    int checkRemovableByRel(@Param("ids") String ids);
    List<ActivityGoodsGroupVo> findGroupListByActivityId(@Param("activityId") String activityId);
    List<ActivityGoodsGroupVo> findGroupListByGoodsId(@Param("goosId") String goodsId);
    List<ActivityGoodsGroup> findListByIds(@Param("ids") String ids);
    int updateGroupPayPrice(@Param("groupId") String groupId, @Param("payPrice") Double payPrice);
    int upByPrimaryKeys(@Param("ids") String ids);
    int downByPrimaryKeys(@Param("ids") String ids);
}