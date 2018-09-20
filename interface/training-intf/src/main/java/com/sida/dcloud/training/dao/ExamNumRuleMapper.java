/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ExamNumRule;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

public interface ExamNumRuleMapper extends IMybatisDao<ExamNumRule> {
    ExamNumRule loadRuleBySubject(@Param("subject") String subject);
}