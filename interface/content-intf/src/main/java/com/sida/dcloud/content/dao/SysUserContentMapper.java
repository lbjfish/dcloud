/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.content.dao;

import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.content.po.SysUserContent;
import com.sida.dcloud.content.vo.SysUserContentVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserContentMapper extends IMybatisDao<SysUserContent> {
    int updateByUserPrimaryKey(@Param("po") SysUser po);
    List<SysUserContentVo> selectVoList(SysUserContent condition);

    int insertDto(@Param("map") Map<String, String> map);
    int updateMobile(@Param("map") Map<String, String> map);
    int updateUserInfo(@Param("map") Map<String, String> map);
    int bindThirdPartAccount(@Param("map") Map<String, String> map);
    int unbindThirdPartAccount(@Param("loginFrom") String loginFrom, @Param("mobile") String mobile);

    int testDistributeTransaction(@Param("id")String id, @Param("remark")String remark);
}