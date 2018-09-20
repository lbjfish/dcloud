/**
 * create by Administrator
 * @date 2018-07
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.IconGroup;
import com.sida.dcloud.training.vo.IconGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IconGroupMapper extends IMybatisDao<IconGroup> {
    List<IconGroupVo> findVoList(@Param("po")IconGroup po);
    int checkMultiCountByUnique(@Param("po")IconGroup po);
    void increaseTotal(@Param("po") IconGroup po);
}