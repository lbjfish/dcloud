/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ConsultationInfo;
import com.sida.dcloud.activity.vo.ConsultationInfoVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsultationInfoMapper extends IMybatisDao<ConsultationInfo> {
    List<ConsultationInfoVo> findVoList(@Param("po") ConsultationInfo po);
    int batchInsert(@Param("list")List<ConsultationInfo> list);
    String findCompanyIdsByNoteId(@Param("noteId")String noteId);
}