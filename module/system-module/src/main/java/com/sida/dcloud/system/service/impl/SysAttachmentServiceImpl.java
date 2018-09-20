package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAttachmentMapper;
import com.sida.dcloud.system.po.SysAttachment;
import com.sida.dcloud.system.service.SysAttachmentService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachmentServiceImpl.class);

    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;

    @Override
    public IMybatisDao<SysAttachment> getBaseDao() {
        return sysAttachmentMapper;
    }

    @Override
    public void batchInsert(List<SysAttachment> list) {
        sysAttachmentMapper.batchInsert(list);
    }

    @Override
    public Map<String, SysAttachment> findMapByFileNameList(List<String> fileNameList) {
        Map<String,SysAttachment> map = new HashMap<>();
        List<SysAttachment> list = sysAttachmentMapper.findByFileNameList(fileNameList);
        if (BlankUtil.isNotEmpty(list)){
            for (SysAttachment po : list){
                map.put(po.getFileName(),po);
            }
        }
        return map;
    }

    @Override
    public Map<String, SysAttachment> findMapByOriginNameList(List<String> originNameList) {
        Map<String,SysAttachment> map = new HashMap<>();
        List<SysAttachment> list = sysAttachmentMapper.findByOriginNameList(originNameList);
        if (BlankUtil.isNotEmpty(list)){
            for (SysAttachment po : list){
                map.put(po.getOriginName(),po);
            }
        }
        return map;
    }
}