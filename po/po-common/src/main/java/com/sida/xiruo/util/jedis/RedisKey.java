package com.sida.xiruo.util.jedis;

public class RedisKey {

    /**
     * 系统模块启动加载资源集合
     */
    public static final String RESOURCE_ALL="resource.all.";

    /**
     * 系统模块初始化加载组织机构到redis缓存(orgId为key，orgName为值)
     */
    public static final String ORG_MAP = "org_map";

    /**
     * 系统模块初始化加载数据字典分组到redis缓存
     */
    public static final String DICT_GROUP = "dict_group";

    /**
     * 系统模块初始化加载数据字典到redis缓存
     */
    public static final String DICT_DATA = "dict_data";

    /**
     * 缓存版本信息RedisKey
     */
    public static final String CACHE_VERSION = "cache_version";

    /**
     * 牌证所有办证状态信息
     */
    public static final String  CERT_ALL_STATE="cert_all_state";

    /**
     * 全局配置
     */
    public static final String  GLOBAL_VARIABLE = "global_variable";

    /**
     * 短信验证码-redis验证码集合key 与失效时间集合key
     */
    public static final String  SHORT_MSG_AUTH_CODE = "short_msg_auth_code";
    public static final String  SHORT_MSG_AUTH_CODE_VALIDITY = "short_msg_auth_code_validity";

    /**
     * 短信验证码业务类型key（验证码根据业务相互隔离）
     */
    public static final String  REMOTE_REGISTER = "remote_register";//远程教育-注册
    public static final long  REMOTE_REGISTER_VALIDITY = 30*60*1000;//有效时间（毫秒数）30分钟
    public static final String  REMOTE_LOGIN = "remote_login";//远程教育-登录
    public static final long  REMOTE_LOGIN_VALIDITY = 30*60*1000;//有效时间（毫秒数）30分钟

    public static final String SYS_REGION_CACHE = "SYS_REGION_CACHE";
    public static final String SYS_REGION_CACHE_WITH_CITY = "SYS_REGION_CACHE_WITH_CITY";
    public static final String SYS_REGION_CACHE_WITH_PROVINCE = "SYS_REGION_CACHE_WITH_PROVINCE";
    public static final String SYS_REGION_CACHE_WITH_COUNTRY = "SYS_REGION_CACHE_WITH_COUNTRY";
    public static final String SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER = "SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER";
    public static final String SYS_REGION_CACHE_WITH_ALL_BY_FLAT = "SYS_REGION_CACHE_WITH_ALL_BY_FLAT";
}
