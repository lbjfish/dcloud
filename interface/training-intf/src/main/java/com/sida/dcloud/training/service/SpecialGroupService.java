package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.vo.SpecialGroupVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface SpecialGroupService extends IBaseService<SpecialGroup> {
    Page<SpecialGroupVo> findPageList(SpecialGroup po);
    int checkMultiCountByUnique(SpecialGroup po);
    void increaseTotal(SpecialGroup po);
}