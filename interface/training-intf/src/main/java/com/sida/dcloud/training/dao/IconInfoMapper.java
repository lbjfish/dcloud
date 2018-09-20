/**
 * create by Administrator
 * @date 2018-07
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.IconInfo;
import com.sida.dcloud.training.vo.IconInfoVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IconInfoMapper extends IMybatisDao<IconInfo> {
    int selectIconInfoSizeByGroupIds(@Param("groupIds")String groupIds);
    List<IconInfoVo> findVoList(@Param("po")IconInfo po);
    int checkMultiCountByUnique(@Param("po")IconInfo po);
    List<GroupCountDto> findRemoveCountGroup(@Param("stringIds")String stringIds);
}