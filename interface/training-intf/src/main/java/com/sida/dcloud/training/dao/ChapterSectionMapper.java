/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.vo.ChapterSectionVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterSectionMapper extends IMybatisDao<ChapterSection> {
    List<ChapterSectionVo> findVoList(@Param("po")ChapterSection po);
    int checkMultiCountByUnique(@Param("po")ChapterSection po);
    void increaseTotal(@Param("po") ChapterSection po);
}