package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysAttachment;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface SysAttachmentService extends IBaseService<SysAttachment> {
    /**
     * 批量插入
     * @param list
     */
    void batchInsert(List<SysAttachment> list);

    /**
     * 文件迁移专用：获取同名文件
     * @param fileNameList
     * @return
     */
    Map<String,SysAttachment> findMapByFileNameList(List<String> fileNameList);

    /**
     * 文件迁移专用：获取同原文件名
     * @param originNameList
     * @return
     */
    Map<String,SysAttachment> findMapByOriginNameList(List<String> originNameList);
}