/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.dao;

import com.sida.dcloud.operation.po.TagGroup;
import com.sida.dcloud.operation.vo.TagGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagGroupMapper extends IMybatisDao<TagGroup> {
    List<TagGroupVo> findVoList(@Param("po") TagGroup po);
}