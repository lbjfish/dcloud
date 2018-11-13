package com.sida.xiruo.util.jedis;

public interface RedisKey {

    /**
     * 系统模块启动加载资源集合
     */
    String RESOURCE_ALL="resource.all.";

    /**
     * 系统模块初始化加载组织机构到redis缓存(orgId为key，orgName为值)
     */
    String ORG_MAP = "org_map";

    /**
     * 系统模块初始化加载数据字典分组到redis缓存
     */
    String DICT_GROUP = "dict_group";

    /**
     * 系统模块初始化加载数据字典到redis缓存
     */
    String DICT_DATA = "dict_data";

    /**
     * 缓存版本信息RedisKey
     */
    String CACHE_VERSION = "cache_version";

    /**
     * 牌证所有办证状态信息
     */
    String  CERT_ALL_STATE="cert_all_state";

    /**
     * 全局配置
     */
    String  GLOBAL_VARIABLE = "global_variable";

    /**
     * 短信验证码-redis验证码集合key 与失效时间集合key
     */
    String  SHORT_MSG_AUTH_CODE = "short_msg_auth_code";
    String  SHORT_MSG_AUTH_CODE_VALIDITY = "short_msg_auth_code_validity";

    /**
     * 短信验证码业务类型key（验证码根据业务相互隔离）
     */
    String  REMOTE_REGISTER = "remote_register";//远程教育-注册
    long  REMOTE_REGISTER_VALIDITY = 30*60*1000;//有效时间（毫秒数）30分钟
    String  REMOTE_LOGIN = "remote_login";//远程教育-登录
    long  REMOTE_LOGIN_VALIDITY = 30*60*1000;//有效时间（毫秒数）30分钟

    String SYS_REGION_CACHE = "SYS_REGION_CACHE";
    String SYS_REGION_CACHE_WITH_CITY = "SYS_REGION_CACHE_WITH_CITY";
    String SYS_REGION_CACHE_WITH_PROVINCE = "SYS_REGION_CACHE_WITH_PROVINCE";
    String SYS_REGION_CACHE_WITH_COUNTRY = "SYS_REGION_CACHE_WITH_COUNTRY";
    String SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER = "SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER";
    String SYS_REGION_CACHE_WITH_ALL_BY_FLAT = "SYS_REGION_CACHE_WITH_ALL_BY_FLAT";

    String OPERATION_COMMPANY_INFO = "OPERATION_COMMPANY_INFO";
    String OPERATION_DESIGNRE_USER = "OPERATION_DESIGNRE_USER";
}
