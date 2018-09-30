/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.dcloud.activity.po.ActivityOrderGoodsGroup;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityOrderGoodsGroupMapper extends IMybatisDao<ActivityOrderGoodsGroup> {
    List<ActivityOrderGoodsGroup> findListByOrderId(@Param("orderId") String orderId);
    List<ActivityOrderGoodsGroup> findListByIds(@Param("ids") String ids);
    int batchInsert(@Param("list") List<ActivityOrderGoodsGroup> list);
    int physicalDeleteByOrderId(@Param("orderId") String orderId);
}