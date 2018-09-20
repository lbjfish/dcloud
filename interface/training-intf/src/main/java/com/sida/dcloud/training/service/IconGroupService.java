package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.IconGroup;
import com.sida.dcloud.training.vo.IconGroupVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface IconGroupService extends IBaseService<IconGroup> {
    Page<IconGroupVo> findPageList(IconGroup po);
    int checkMultiCountByUnique(IconGroup po);
    void increaseTotal(IconGroup po);
}