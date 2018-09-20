package com.sida.dcloud.system.common;


/**
 * web-collector常量表
 * Created by wuzhenwei.
 */
public class CollectorConstants {



    public static final String DRIVERPATH_KEY = "phantomjs.binary.path";//驱动路径-键
    public static final String DRIVERPATH_VALUE = "F:/plugins/phantomjs.exe";//驱动路径-值

    public static final Long LONGSLEEPTIME = 5000L;//线程停留时间。给页面渲染留出时间
    public static final String MD5 = "MD5";//加密方式：MD5
    public static final String HREF = "href";//头
    public static final String HOST_URL = "web.driver.host.url";//ip地址
    public static final String UTF_8 = "utf-8";//编码方式

//    public static final String LOGIN_PATH = "http://192.168.185.232:9530/#/login";//登录路径
    public static final String LOGIN_PATH = "http://192.168.185.224:9530/#/login";//登录路径
    public static final String ACCOUNT = "caojianzhou";//账号
    public static final String PASSWORD = "spring";//密码


    //各页面特殊名称
    public static final String ACCOUNT_NAME = "email";//登录页用户名文本框
    public static final String PASSWORD_NAME = "password";//登录页密码文本框
    public static final String LOGIN_BUTTON_TAG = "button";//登录页登录按钮

    public static final String HEADER_CLASS = "header";//顶栏
    public static final String ASIDE_TAG = "aside";//侧边栏
    public static final String ASIDE_DIV_A = "//aside/ul/div/div/a";//叶子项
    public static final String ASIDE_DIV_LI = "//aside/ul/div/div/li";//树

    public static final String LI_UL_A = "./ul/a";//叶子项
    public static final String LI_UL_DIV_DIV_A = "./ul/div/div/a";//叶子项
    public static final String LI_UL_DIV_DIV_LI = "./ul/div/div/li";//树

    public static final String A_LI_SPAN = "./li/span";//文字项
    public static final String LI_DIV_SPAN = "./div/span";//文字项

    public static final String INNERHTML = "innerHTML";//树


    public static final String EL_MENU_ITEM_CLASS = "el-menu-item";//item对象
    public static final String EL_SUBMENU_CLASS = "el-submenu";//父级菜单
    public static final String EL_SUBMENU__TITLE = "el-submenu__title";//title对象
    public static final String MENU_INDENT = "menu-indent";//子级菜单

    public static final String EL_MENU_CLASS = "el-menu";//子级菜单
    public static final String A_TAG = "a";//a标签


    public static final String TAG_SECTION = "section";//选择最后一个section
    public static final String CLASS_BTN_CONTENT = "btn-content";//按钮form
    public static final String XPATH_CURRENT_FORM_BUTTON = ".//form//button";//按钮
    public static final String XPATH_CURRENT_SPAN = "./span";//按钮文本信息

    public static final String XPATH_CURRENT_FOLLWING_DIV_ONE = "./following-sibling::div[1]";//字段form
    public static final String XPATH_CURRENT_TABLE_TH = ".//table//th";//字段form
    public static final String XPATH_CURRENT_DIV = "./div";//按钮文本信息



    public static final String POPOVERBTN = "popoverBtn";//根据class过滤弹出按钮
    public static final String XPATH_PARENT = "..";//父级
    public static final String ATTR_CLASS = "class";
    public static final String CLASS_EL_CHECKBOX = "el-checkbox";
    public static final String ARGUMENTS_ZERO = "arguments[0].click();";
    public static final String ATTR_ID = "id";
    public static final String XPATH_CURRENT_LI = "./li";
    public static final String CLASS_EL_TABS__CONTENT = "el-tabs__content";
    public static final String CLASS_EL_TAB_PANE = "el-tab-pane";



    public static final String WEBSITE_FOR_REGION = "http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html";
    public static final String MsoNormal = "MsoNormal";




    public enum controlType{
        BUTTON("按钮");
        private String typeName;
        controlType(String typeName) {
            this.typeName = typeName;
        }
        public String getTypeName() {
            return typeName;
        }

    }



}
