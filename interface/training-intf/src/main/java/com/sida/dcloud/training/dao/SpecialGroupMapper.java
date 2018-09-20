/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.vo.SpecialGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecialGroupMapper extends IMybatisDao<SpecialGroup> {
    List<SpecialGroupVo> findVoList(@Param("po")SpecialGroup po);
    int checkMultiCountByUnique(@Param("po")SpecialGroup po);
    void increaseTotal(@Param("po") SpecialGroup po);
}