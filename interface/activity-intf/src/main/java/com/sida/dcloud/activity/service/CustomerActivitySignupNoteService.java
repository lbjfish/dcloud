package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerActivitySignupNoteService extends IBaseService<CustomerActivitySignupNote> {
    List<TableMeta> findTableMeta();
    List<CustomerActivitySignupNote> findVoList(@Param("po")CustomerActivitySignupNote po);
}