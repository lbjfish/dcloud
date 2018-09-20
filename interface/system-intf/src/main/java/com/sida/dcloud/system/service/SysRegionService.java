package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.auth.vo.RegionTreeDTO;
import com.sida.xiruo.xframework.service.IBaseService;
import org.dom4j.Document;

import java.util.List;
import java.util.Map;

public interface SysRegionService extends IBaseService<SysRegion> {

    /**
     * 获取地区树
     * @return
     */
    List<RegionTreeDTO> findTree();

    /**
     * 获取地区列表
     * @return
     */
    List<SysRegion> findAll();

    /**
     * 根据邮政编码获取地区
     * @param code
     * @return
     */
    SysRegion selectByCode(String code);

    /**
     * 根据路径删除区域信息
     * @param id
     */
    int deleteById(String id);

    /**
     * 根据数据库数据生成xml文件
     */
    void createRegionXml();

    /**
     * 根据数据库数据生成xml文件
     */
    Document createRegionXml(final String regionPath);

    /**
     * 获取城市集合
     * @return
     */
    Object findCitys();

    Map<String,String> getProvinceMap();

    /**
     * 初始化地区数据
     */
    void init();

    /**
     * 根据地区编码code集合获取名称map
     * @param param
     * @return
     */
    Map<String,String> findNameMapByCodeList(List<String> param);

    /**
     *
     * @param code
     */
    String getNameByCode(String code);
}