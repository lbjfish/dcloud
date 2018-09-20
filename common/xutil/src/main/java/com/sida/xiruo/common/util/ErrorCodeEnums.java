package com.sida.xiruo.common.util;

public enum ErrorCodeEnums {
    INSERT("4001","插入数据异常！"),
    UPDATE("4002","更新数据异常！"),
    DELETE("4003","删除异常！"),
    SELECT("4004","查询异常！"),
    PAGE("4005","分页异常！"),
    EXPORT("4006","导出异常！"),
    CUSTOM("4007","提示！"),//自定义异常
    COLLERTOR("4008","爬取页面异常！"),
    WORKFLOW_ERROR_USER_NOT_FOUND("409","无法根据角色编码【::ROLE】获取到相应用户，请确认该角色下是否分配了人员！"),
    WORKFLOW_ERROR("4009-1","工作流执行异常！"),
    WORKFLOW_ERROR_START("4009-1","工作流发起异常！"),
    NO_GENERATE("4010-1","未找到对应的生成规则类型！"),
    PARAM_BLANK_ERROR("8001-1","必填参数为空，请检查相关必填参数！"),
    BUSINESS_ERROR("9000","业务异常！");

    private String code;
    private String message;

    ErrorCodeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
