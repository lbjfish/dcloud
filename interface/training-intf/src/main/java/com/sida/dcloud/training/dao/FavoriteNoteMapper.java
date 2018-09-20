/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.training.vo.FavoriteNoteVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteNoteMapper extends IMybatisDao<FavoriteNote> {
    List<FavoriteNoteVo> findVoList(@Param("po")FavoriteNote po);
}