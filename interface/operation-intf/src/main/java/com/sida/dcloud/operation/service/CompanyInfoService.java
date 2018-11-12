package com.sida.dcloud.operation.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.operation.po.CompanyInfo;
import com.sida.dcloud.operation.vo.CompanyInfoVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface CompanyInfoService extends IBaseService<CompanyInfo> {
    Page<CompanyInfoVo> findPageList(CompanyInfo po);
    List<CompanyInfoVo> findAllInList(CompanyInfo po);
    Map<String, CompanyInfoVo> findAllInMap(CompanyInfo po);
    void increaseFieldCount(String fieldName, String id, int count);
}