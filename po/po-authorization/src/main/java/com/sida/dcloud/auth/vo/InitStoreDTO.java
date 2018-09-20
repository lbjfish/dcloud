package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysOrg;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 初始化门店参数
 */
public class InitStoreDTO {

    @ApiModelProperty("增量同步组织集合")
    private List<SysOrgVo> orgList;
    @ApiModelProperty("增量同步组织id集合")
    private List<String> ids;
    @ApiModelProperty("增量同步组织中属于门店的map集合")
    private Map<String,SysOrg> storeMap;

    public List<SysOrgVo> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<SysOrgVo> orgList) {
        this.orgList = orgList;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Map<String, SysOrg> getStoreMap() {
        return storeMap;
    }

    public void setStoreMap(Map<String, SysOrg> storeMap) {
        this.storeMap = storeMap;
    }
}
