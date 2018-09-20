/**
 * create by Administrator
 * @date 2018-02
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysMessage;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMessageMapper extends IMybatisDao<SysMessage> {
    /**
     * 批量逻辑删除
     * @param ids
     */
    void deleteByIds(@Param(value = "ids") List<String> ids);

    /**
     * 批量物理删除
     * @param ids
     */
    void removeByIds(@Param(value = "ids") List<String> ids);

    /**
     * 根据流程实例id删除消息
     * @param processInstanceId
     */
    void removeByProcInstId(@Param(value = "processInstanceId") String processInstanceId);

    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param(value = "list") List<SysMessage> list);
}