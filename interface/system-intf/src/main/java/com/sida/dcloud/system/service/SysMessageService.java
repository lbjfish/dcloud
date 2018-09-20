package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysMessage;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysMessageService extends IBaseService<SysMessage> {

    /**
     * 获取消息列表
     * @param param
     * @return
     */
    Page<SysMessage> findPageList(SysMessage param);

    /**
     * 逻辑删除
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 物理删除
     * @param ids
     */
    void removeByIds(List<String> ids);


    void removeByProcInstId(String procId);

    /**
     * 批量新增
     * @param list
     */
    void batchSave(List<SysMessage> list);

    /**
     * 消息已读
     * @param id
     * @return
     */
    Object readMessage(String id);
}