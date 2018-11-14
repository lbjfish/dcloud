package com.sida.dcloud.content.service;

import com.sida.dcloud.content.po.SpecialSubject;
import com.sida.dcloud.content.vo.SpecialSubjectVo;
import com.sida.xiruo.xframework.service.IBaseService;

public interface SpecialSubjectService extends IBaseService<SpecialSubject> {
    /**
     * @descript 根据专题分类查资讯详情
     * @param subjectCategory
     * @author lee
     * @return SpecialSubjectVo
     */
    SpecialSubjectVo findSpecialToChildsByType(String subjectCategory);
}