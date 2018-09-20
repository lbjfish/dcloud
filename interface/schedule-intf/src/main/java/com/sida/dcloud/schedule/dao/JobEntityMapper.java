/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.sida.dcloud.schedule.dao;

import com.sida.dcloud.schedule.po.JobEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobEntityMapper {

    int deleteByPrimaryKey(Object id);

    int insert(JobEntity po);

    int insertSelective(JobEntity po);

    JobEntity selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(JobEntity po);

    int updateByPrimaryKey(JobEntity po);

    List<JobEntity> selectByCondition(JobEntity condition);

    int updateByIdsSelective(@Param("po") JobEntity po, @Param("ids") List ids);
}