/**
 * create by user
 * @date 2018-11
 */
package com.sida.dcloud.content.dao;

import com.sida.dcloud.content.po.SpecialSubject;
import com.sida.dcloud.content.vo.SpecialSubjectVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;

public interface SpecialSubjectMapper extends IMybatisDao<SpecialSubject> {
    /**
     * @descript 根据专题分类查资讯详情
     * @param subjectCategory
     * @author lee
     * @return SpecialSubjectVo
     */
    SpecialSubjectVo findSpecialToChildsByType(String subjectCategory);
}