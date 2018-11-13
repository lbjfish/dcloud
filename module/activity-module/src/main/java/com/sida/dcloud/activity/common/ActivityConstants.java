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

    enum ORDER_STATUS {
        NOT_PAY("待付款", 1),
        NOT_JOIN("待参加", 2),
        INVALID("已失效", 3),
        JOINED("已参加", 4),
        NOT_COMMENT("待评价", 5),
        COMPLETED("已完成", 6),
        CLOSED("已关闭", 7),
        REFUNDED("已退款", 8),
        EXPIRED("已过期", 9);
        // 成员变量
        private String name;
        private String code;

        // 构造方法
        ORDER_STATUS(String name, String code) {
            this.name = name;
            this.code = code;
        }

        ORDER_STATUS(String name, int icode) {
            this(name, icode + "");
        }

        // 普通方法
        public static String getName(String code) {
            for (ORDER_STATUS c : ORDER_STATUS.values()) {
                if (c.getCode().equals(code)) {
                    return c.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
