/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityGoodsRelGroup;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityGoodsRelGroupMapper extends IMybatisDao<ActivityGoodsRelGroup> {
    int deleteByGroupId(@Param("groupId") String groupId);
    int batchInsert(@Param("list") List<ActivityGoodsRelGroup> list);
}