/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityOrderMapper extends IMybatisDao<ActivityOrder> {
    List<ActivityOrderVo> findVoList(@Param("po") ActivityOrder po);
}