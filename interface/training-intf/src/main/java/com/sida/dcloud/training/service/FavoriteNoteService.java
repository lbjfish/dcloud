package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.training.vo.FavoriteNoteVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface FavoriteNoteService extends IBaseService<FavoriteNote> {
    Page<FavoriteNoteVo> findPageList(FavoriteNote po);
}