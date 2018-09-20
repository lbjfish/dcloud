/**
 * create by yjr
 * @date 2017-08
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysTrainingfieldModels extends BaseEntity implements Serializable {
    @ApiModelProperty("训练场id")
    private String trainingfieldId;

    @ApiModelProperty("车型code（从培训车型枚举中取：C1、C2、A1、A2、A3、B1、B2、C3）")
    private String modelsId;

    private static final long serialVersionUID = 1L;

    public String getTrainingfieldId() {
        return trainingfieldId;
    }

    public void setTrainingfieldId(String trainingfieldId) {
        this.trainingfieldId = trainingfieldId == null ? null : trainingfieldId.trim();
    }

    public String getModelsId() {
        return modelsId;
    }

    public void setModelsId(String modelsId) {
        this.modelsId = modelsId == null ? null : modelsId.trim();
    }
}