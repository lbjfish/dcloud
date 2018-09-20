/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ExamSectionRule;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamSectionRuleMapper extends IMybatisDao<ExamSectionRule> {
    List<ExamSectionRule> loadRuleBySubject(@Param("subject") String subject);
    void deleteBySubject(@Param("subject") String subject);
    void insertPos(@Param("examSectionRuleList")List<ExamSectionRule> examSectionRuleList);
}