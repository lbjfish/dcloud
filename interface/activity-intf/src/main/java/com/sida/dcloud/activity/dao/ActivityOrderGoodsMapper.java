/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityOrderGoodsMapper extends IMybatisDao<ActivityOrderGoods> {
    List<ActivityOrderGoods> findListByOrderId(@Param("orderId") String orderId);
    int batchInsert(@Param("list") List<ActivityOrderGoods> list);
    int physicalDeleteByOrderId(@Param("orderId") String orderId);
}