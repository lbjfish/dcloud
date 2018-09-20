package com.sida.dcloud.auth.common;

/**
 * Created by xieguopei on 2017/9/6.
 */
public enum  EmployeeHideColumnEnum {
    WORKCARD("工号"), NAME("姓名"), DEPT("部门"), JOB("职位"), SEX("性别"), IDNUMBER("身份证");

    private final String typeName;
    EmployeeHideColumnEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
