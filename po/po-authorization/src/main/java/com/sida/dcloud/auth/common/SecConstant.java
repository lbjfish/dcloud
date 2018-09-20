package com.sida.dcloud.auth.common;


/**
 * 鉴权模块-常量表
 * Created by wuzhenwei.
 */
public class SecConstant {

    public static final String CREATED_AT = "created_at";   //创建时间
    public static final String SORT = "sort";               //排序字段
    public static final String DESC = "desc";               //降序
    public static final String ASC = "asc";                 //升序

    //是否具备权限常亮
    public static final String NO = "NO";                 //无权限
    public static final String YES = "YES";               //有权限


    public static final String DEFAULT_ = "default_";     //默认按钮标识



    //判断员工性别
    public static final String MAN = "MAN";             //男士
    public static final String WOMAN = "WOMAN"; //女士

    //生成的地区表xml的路径定义：先写死为固定地址
    public final static String XML_ROOT_PATH = "E:/region/";
    public final static String REGION_XML_FILE_PATH = XML_ROOT_PATH + "region.xml";
    public final static String REGION_XML_FILE_NAME = "region.xml";
    public final static String REGION_XML_FILE_KEY = "20171206164127482c6e7bc3d54440a9112ee1e41ed666" + "/" + REGION_XML_FILE_NAME;
    public final static String CHINA = "China";

    //后端返回给前端的密码值(不把真的密码返回)
    public final static String RETURN_PASSWORD = "****************";
    public final static String DEFAULT_PASSWORD = "123456";
    public final static String DEFAULT_PASSWORD2 = "spring";


    //sys_employee表 status字段 在职状态（0正式员工   1试用期  2已离职）
    public final static Integer EMP_STATUS_0 = 0;
    public final static Integer EMP_STATUS_1 = 1;
    public final static Integer EMP_STATUS_2 = 2;


    //自定义key
    public final static String buttons = "buttons";
    public final static String fields = "fields";

    //sys_role：学员code
    public final static String ORDER_USER_ROLECODE = "STUDENT";
    //默认用户角色
    public final static String DEFAULT_USER_ROLECODE = "DEFAULT";

    //app文件样式
    public final static String FILE_STYLE = "imageView2/1/w/200/h/200";



}
