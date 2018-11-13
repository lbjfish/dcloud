package com.sida.dcloud.operation.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.operation.po.TagGroup;
import com.sida.dcloud.operation.vo.TagGroupVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface TagGroupService extends IBaseService<TagGroup> {
    Page<TagGroupVo> findPageList(TagGroup po);
    List<TagGroupVo> findAllList(TagGroup po);
}