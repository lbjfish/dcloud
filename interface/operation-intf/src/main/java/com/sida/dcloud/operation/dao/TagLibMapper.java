/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.dao;

import com.sida.dcloud.operation.po.CompanyRelTag;
import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.vo.TagLibVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TagLibMapper extends IMybatisDao<TagLib> {
    List<TagLibVo> findVoList(@Param("po") TagLib po);
    List<CompanyRelTag> findAllRelList();
    @MapKey("company_id")
    Map<String, Map<String, String>> findRelMapGroupByCompany();
}