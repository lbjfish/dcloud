/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerActivitySignupNoteMapper extends IMybatisDao<CustomerActivitySignupNote> {
    List<TableMeta> findTableMeta(@Param("tableMeta") TableMeta tableMeta);
    List<CustomerActivitySignupNoteVo> findVoList(@Param("po")CustomerActivitySignupNote po);
    int checkMultiCountByUnique(@Param("po")CustomerActivitySignupNote po);
    String getCurrentNoteNo();
}