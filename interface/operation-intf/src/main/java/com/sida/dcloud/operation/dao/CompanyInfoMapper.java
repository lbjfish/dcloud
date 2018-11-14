/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.dao;

import com.sida.dcloud.operation.po.CompanyInfo;
import com.sida.dcloud.operation.vo.CompanyInfoVo;
import com.sida.xiruo.po.common.IdAndNameDto;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CompanyInfoMapper extends IMybatisDao<CompanyInfo> {
    List<CompanyInfoVo> findVoList(@Param("po") CompanyInfo po);
    @MapKey("id")
    Map<String, IdAndNameDto> selectNamesByIds(@Param("ids") String ids);
    @MapKey("id")
    Map<String, CompanyInfoVo> findVoMap(@Param("po") CompanyInfo po);
    int checkMultiCountByUnique(@Param("po")CompanyInfo po);
    int checkRemovableByRel(@Param("ids") String ids);
    void increaseFieldCount(@Param("fieldName")String fieldName, @Param("id")String id, @Param("count")int count);
}