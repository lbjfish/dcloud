/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ExamTypeRule;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTypeRuleMapper extends IMybatisDao<ExamTypeRule> {
    List<ExamTypeRule> loadRuleBySubject(@Param("subject") String subject);
    void deleteBySubject(@Param("subject") String subject);
    void insertPos(@Param("examTypeRuleList")List<ExamTypeRule> examTypeRuleList);
}