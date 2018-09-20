/**
 * create by Administrator
 * @date 2017-10
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysAttachment;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAttachmentMapper extends IMybatisDao<SysAttachment> {
    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param(value = "list") List<SysAttachment> list);

    /**
     * 获取同名文件
     * @param fileNameList
     * @return
     */
    List<SysAttachment> findByFileNameList(@Param(value = "fileNameList") List<String> fileNameList);

    /**
     * 获取同原文件名文件
     * @param originNameList
     * @return
     */
    List<SysAttachment> findByOriginNameList(@Param(value = "originNameList") List<String> originNameList);
}