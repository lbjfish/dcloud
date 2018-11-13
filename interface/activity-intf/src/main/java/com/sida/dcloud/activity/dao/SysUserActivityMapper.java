/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.activity.vo.SysUserActivityVo;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface SysUserActivityMapper extends IMybatisDao<SysUserActivity> {
    int updateByUserPrimaryKey(@Param("po") SysUser po);
    List<SysUserActivityVo> selectVoList(SysUserActivity condition);

    int insertDto(@Param("map") Map<String, String> map);
    int updateMobile(@Param("map") Map<String, String> map);
    int updateUserInfo(@Param("map") Map<String, String> map);
    int bindThirdPartAccount(@Param("map") Map<String, String> map);
    int unbindThirdPartAccount(@Param("loginFrom") String loginFrom, @Param("mobile") String mobile);

    int testDistributeTransaction(@Param("id")String id, @Param("remark")String remark);
}