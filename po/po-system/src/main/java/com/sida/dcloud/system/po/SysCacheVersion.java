/**
 * create by Administrator
 * @date 2017-10
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SysCacheVersion extends BaseEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("业务数据缓存键")
    private String redisKey;

    @ApiModelProperty("业务数据最后更新时间")
    private Date lastUpdatedTime;

    @ApiModelProperty("Redis版本与数据库不一致是更新Redis")
    private String lastUpdatedVersion;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey == null ? null : redisKey.trim();
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedVersion() {
        return lastUpdatedVersion;
    }

    public void setLastUpdatedVersion(String lastUpdatedVersion) {
        this.lastUpdatedVersion = lastUpdatedVersion == null ? null : lastUpdatedVersion.trim();
    }
}