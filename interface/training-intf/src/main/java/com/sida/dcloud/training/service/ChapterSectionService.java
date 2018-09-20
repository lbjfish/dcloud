package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.vo.ChapterSectionVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface ChapterSectionService extends IBaseService<ChapterSection> {
    Page<ChapterSectionVo> findPageList(ChapterSection po);
    int checkMultiCountByUnique(ChapterSection po);
    void increaseTotal(ChapterSection po);
}