package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysAccessLogDetail;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface SysAccessLogDetailService extends IBaseService<SysAccessLogDetail> {
    Page<SysAccessLogDetail> pageList(SysAccessLogDetail vo);

    Page<SysAccessLogDetail> selecAccessLogstByDate(int pageNum,int pageSize,String startTime,String endTime);
}