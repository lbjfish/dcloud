package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ConsultationInfo;
import com.sida.dcloud.activity.vo.ConsultationInfoVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ConsultationInfoService extends IBaseService<ConsultationInfo> {
    Page<ConsultationInfoVo> findPageList(ConsultationInfo po);
    int batchInsert(List<ConsultationInfo> list);
    public String findCompanyIdsByNoteId(String noteId);
}