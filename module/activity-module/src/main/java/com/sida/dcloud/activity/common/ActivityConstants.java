package com.sida.dcloud.activity.common;

public interface ActivityConstants {
    String REMOVABLE_ACTIVITY_STATUS_FLAG = "3";
    String REMOVABLE_ACTIVITY_ORDER_STATUS_FLAGS = "'2','4'";

    //以下字典枚举定义
    enum DIC_GROUP_CODE {
        //C端用户角色
        client_role,
        //表单字段类型
        intput_type,
        //上架状态
        publish_status,
        //资讯类型
        information_type,
        //B端运营活动状态
        activity_status,
        //消息类型
        message_type,
        //C端用户订单状态
        activity_order_status,
        //C端用户收藏类型
        favorite_type,
        //C端用户关注类型
        care_type,
        //图标组
        icon_group,
        //附件类型
        attachment_type,
        //统计类型
        statistical_type,
        //支付方式
        payment_mode,
        //业务来源
        business_source,
        //行业领域
        trade_domain,
        //活动信息获取渠道
        gain_channel,
        //证件类型
        id_type,
        //app类型
        app_type,
        //广告位置
        adv_place,
        //专题类别
        subject_category,
        //手机屏幕尺寸
        mobile_screen,
        //

    }
}
