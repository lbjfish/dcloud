package com.sida.dcloud.auth.common;

import com.google.common.collect.Lists;

import java.util.List;

public class SysEnums {

    /**
     * 资源类型：（“page”，“button”，“field”）
     */
    public enum ResourceTypeEnums {
        PAGE("page"),
        BUTTON("button"),
        FIELD("field");
        private String name;

        public String getName() {
            return name;
        }

        ResourceTypeEnums(String name) {
            this.name = name;
        }
    }


    /**
     * 特殊部门标识（0.非特殊部门  1.牌证部门  2.片区  3.门店）
     */
    public enum OrgTypeEnums {
        COMMON(0),
        CERT(1),
        AREA(2),
        STORE(3);
        private Integer type;

        public Integer getType() {
            return type;
        }

        OrgTypeEnums(Integer type) {
            this.type = type;
        }
    }

    /**
     * 国籍(0-中国大陆，1-港／澳／台，2-外籍，3-其他)
     */
    public enum NationalityEnums {
        DL("0"),
        GAT("1"),
        WJ("2"),
        QT("3");
        private String type;

        public String getType() {
            return type;
        }

        NationalityEnums(String type) {
            this.type = type;
        }
    }

    /**
     * 证件类型(0-身份证；1-军官证；2-护照；3-其他)
     */
    public enum IdTypeEnums {
        IdCard("0"),
        JGZ("1"),
        HZ("2"),
        QT("3");
        private String type;

        public String getType() {
            return type;
        }

        IdTypeEnums(String type) {
            this.type = type;
        }
    }

    /**
     * 驾校名称-code-简称 枚举
     */
    public enum SchoolNameEnums {
        BBJD("深圳市斑斑驾道网络科技有限公司","bbjd","斑斑驾道","深圳市"),
        TPRC("深圳市拓普软创科技有限公司","tprc","拓普软创","深圳市"),
        ZQSG("肇庆市深港机动车驾驶培训有限公司","zqsg","肇庆深港","肇庆市"),
        YJSG("阳江市深港机动车驾驶培训有限公司","yjsg","阳江深港","阳江市"),
        CQSG("重庆深港凯旋机动车驾驶培训有限公司","cqsg","重庆深港","重庆市"),
        HZSG("惠州市深港机动车驾驶培训有限公司","hzsg","惠州深港","惠州市"),
        BJLL("北京喱喱科技有限公司","bjll","北京喱喱","北京市"),
        CDYC("成都悦成驾驶培训有限公司","cdyc","成都悦成","成都市"),
        PAPX("鹏安培训公司","papx","鹏安培训","深圳市"),
        DGSS("东莞市山水天地农业生态园有限公司","dgss","东莞山水","东莞市"),
        GSTL("甘肃天昱国际汽车文化产业园公司","gstl","甘肃天昱","兰州市"),
        SZFL("深圳市飞林环境建设有限公司","szfl","深圳飞林","深圳市"),
        DGPA("东莞市鹏安房地产开发有限公司","dgpa","东莞鹏安","东莞市"),
        DGHH("东莞市华慧智能装备科技有限公司","dghh","东莞华慧","东莞市"),
        ZQGR("肇庆市广仁出租车有限公司","zqgr","肇庆广仁","肇庆市"),
//        SZGR("深圳市广仁汽车销售有限公司","szgr","深圳广仁","深圳市"),
        SZFN("深圳市峰能氢电汽车有限公司","szfn","深圳峰能","深圳市"),
        SZSLS("深圳市赛洛诗健康科技有限公司","szsls","深圳赛洛诗","深圳市"),
        GDJK("广东玖凯水氢公交车科技有限公司","gdjk","广东玖凯","深圳市"),
        SZYS("深圳市云哨智能科技有限公司","szys","深圳云哨","深圳市"),
        SZHL("深圳市哈乐网络科技有限公司","szhl","深圳哈乐","深圳市"),
        SZSG("深港驾培集团深港驾驶学校","szsg","深圳深港","深圳市");


        private String name;//名称
        private String code;//编码
        private String abName;//简称
        private String regionName;//对应市区的 公安局交通警察支队车辆管理所

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getAbName() {
            return abName;
        }

        public String getRegionName() {
            return regionName;
        }

        SchoolNameEnums(String name, String code, String abName, String regionName) {
            this.name = name;
            this.code = code;
            this.abName = abName;
            this.regionName = regionName;
        }

        public static String getCodeByName(String name){
            if (name != null){
                for (SchoolNameEnums enums : SchoolNameEnums.values()) {
                    if (enums.name.equals(name)){
                        return enums.code;
                    }
                }
            }
            return "";
        }

        public static String getAbNameByName(String name){
            if (name != null){
                for (SchoolNameEnums enums : SchoolNameEnums.values()) {
                    if (enums.name.equals(name)){
                        return enums.abName;
                    }
                }
            }
            return "";
        }

        public static String getRegionNameByName(String name){
            if (name != null){
                for (SchoolNameEnums enums : SchoolNameEnums.values()) {
                    if (enums.name.equals(name)){
                        return enums.regionName + "公安局交通警察支队车辆管理所";
                    }
                }
            }
            return "";
        }
    }


    /**
     * 部门负责人 岗位编码枚举
     */
    public enum LeaderRoleEnums {
        CERT("pzjl","牌证经理","","","pzzg","牌证主管"),//牌证部
        FIN("cwjl","财务经理","cwfjl","财务副经理","",""),//财务中心
        XZ("xzjl","行政经理","xzfjl","行政副经理","xzzg","行政主管"),//行政部
        KJJW("jwjl","教务经理","","","jwzg","教务主管"),//科技教务部
        RLZY("rlzybjl","人力资源部经理","rsfjl","人事副经理","rszg","人事主管"),//人力资源部
        SCYX("","","","","yxzg","营销主管"),//市场营销部
        XLC("xlccz","修理厂厂长","","","",""),//修理厂
        KHFW("khfwbjl","客户服务部经理","","","kfzg","客服主管"),//客户服务部
        KJJY("","","JYFJL","教研副经理","",""),//科技教研部
        GC("","","","","gcbzg","工程部主管"),//工程部
        AQ("aqddbjl","安全督导部经理","fjl","副经理","aqzg","安全主管"),//安全部
        CW("cwjl","车务部经理","CWBFJL","车务部副经理","cwzg","车务主管"),//车务部
        DD("","","DDFJL","督导副经理","",""),//督导部
        DKH("","","DKHFJL","大客户副经理","",""),//大客户部
        AREA("pqjl","片区经理","pqfjl","片区副经理","","");//片区
        private String firstCode;
        private String firstName;
        private String secondCode;
        private String secondName;
        private String thirdCode;
        private String thirdName;

        public String getFirstCode() {
            return firstCode;
        }

        public String getSecondCode() {
            return secondCode;
        }

        public String getThirdCode() {
            return thirdCode;
        }

        LeaderRoleEnums(String firstCode, String firstName, String secondCode, String secondName, String thirdCode, String thirdName) {
            this.firstCode = firstCode;
            this.firstName = firstName;
            this.secondCode = secondCode;
            this.secondName = secondName;
            this.thirdCode = thirdCode;
            this.thirdName = thirdName;
        }

        public static List<String> getFirstCodes(){
            List<String> list = Lists.newArrayList();
            for (LeaderRoleEnums enums : LeaderRoleEnums.values()) {
                list.add(enums.firstCode);
            }
            return list;
        }

        public static List<String> getSecondCodes(){
            List<String> list = Lists.newArrayList();
            for (LeaderRoleEnums enums : LeaderRoleEnums.values()) {
                list.add(enums.secondCode);
            }
            return list;
        }

        public static List<String> getThirdCodes(){
            List<String> list = Lists.newArrayList();
            for (LeaderRoleEnums enums : LeaderRoleEnums.values()) {
                list.add(enums.thirdCode);
            }
            return list;
        }
    }
}
