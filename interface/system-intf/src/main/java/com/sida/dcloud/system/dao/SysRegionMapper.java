package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.system.dto.SysRegionSingleLayerDto;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRegionMapper extends IMybatisDao<SysRegion> {

    /**
     * 根据path逻辑删除其本身和子级数据
     * @param id
     * @return
     */
    int deleteById(@Param("id") String id);

    /**
     * 获取城市
     * @return
     */
    List<SysRegion> findCitys();

    /**
     * 获取省份
     * @return
     */
    List<SysRegion> findProvinces();

    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param(value = "list") List<SysRegion> list);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 根据地区编码code集合获取名称map
     * @param codeList
     * @return
     */
    List<SysRegion> findNameListByCodeList(@Param(value = "codeList") List<String> codeList);

    /**
     * 扁平化返回同一级别的地区
     * @param level
     * @return
     */
    List<SysRegionSingleLayerDto> findSysRegionSingleLayerDtoByLevel(@Param("level") String level);

    int updateSysRegionPinyin(@Param("po") SysRegionSingleLayerDto po);
}