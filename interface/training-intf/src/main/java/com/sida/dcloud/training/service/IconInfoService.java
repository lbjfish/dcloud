package com.sida.dcloud.training.service;

import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.IconInfo;
import com.sida.dcloud.training.vo.IconInfoVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;

public interface IconInfoService extends IBaseService<IconInfo> {
    int selectIconInfoSizeByGroupIds(String groupIds);
    Page<IconInfoVo> findPageList(IconInfo po);
    int checkMultiCountByUnique(IconInfo po);
    List<GroupCountDto> findRemoveCountGroup(String stringIds);
}