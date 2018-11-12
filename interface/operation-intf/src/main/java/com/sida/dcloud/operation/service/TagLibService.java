package com.sida.dcloud.operation.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.operation.po.CompanyRelTag;
import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.vo.TagLibVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface TagLibService extends IBaseService<TagLib> {
    Page<TagLibVo> findPageList(TagLib po);
    List<TagLibVo> findAllList(TagLib po);
    List<CompanyRelTag> findAllRelList();
    Map<String, Map<String, String>> findRelMapGroupByCompany();
}