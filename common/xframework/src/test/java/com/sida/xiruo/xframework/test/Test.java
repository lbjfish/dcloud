package com.sida.xiruo.xframework.test;

import com.alibaba.fastjson.JSONObject;
import com.sida.xiruo.common.components.RandomUtils;
import com.sida.xiruo.xframework.util.DESUtils;

public class Test {
    public static void main(String[] args) {
        System.out.println(DESUtils.getEncryptString("root"));
        System.out.println(DESUtils.getEncryptString("BuTong@123!?"));
        System.out.println(DESUtils.getDecryptString("thZF+dw8OelhWj0L2jU2BA=="));
        System.out.println(String.format("1.%s, 2.%s, 3.%s", "1", "2", "3", "4", "5", "6"));

//        System.out.println(DESUtils.getEncryptString("butong2018butong2018butong2018bt"));
//        System.out.println(DESUtils.getEncryptString("butong.com2018"));

//        System.out.println(DESUtils.getEncryptString("l551NiL9dyb7zGSBglZLhk331N1Y"));
//        System.out.println(DESUtils.getEncryptString("1JYh8nyXrhzHZUKPZ5BIv2W2p2lB"));
        System.out.println(DESUtils.getEncryptString("18023c9d937bffd7b0c08592cc3bcb7c"));

//        System.out.println(DESUtils.getDecryptString("Dl2DZcPKE+yQZzX5OjUfepPi8LloUrbyLT+KTh+EucINzPQokObvCA=="));
//        System.out.println(DESUtils.getDecryptString("r3dSr39G9pHdhljocCnMnA=="));
//        System.out.println(DESUtils.getDecryptString("/nBqEDpFs6NQY0dFmXQntBZQb2q0ItgaSGjkpNlQCfA="));
//        System.out.println(DESUtils.getDecryptString("L6gMKVLXDzHThxaetK2VR18VdbyW5Jx6u2I8f1Bbj6c="));

//        System.out.println(RandomUtils.randomString(32));
//        System.out.println("ACTIVITY_ORDER_20181023_0000000048".length());
//        System.out.println("ACTIVITY_ORDER_20181023_0000000048".replaceAll("^ACTIVITY_", ""));
//
//        System.out.println((System.currentTimeMillis() + "").length());
//
//        JSONObject json = new JSONObject();
//        System.out.println(json.getString("aaa"));

        JSONObject json = JSONObject.parseObject("{\"session_key\":\"sMTA2ZVVA4YFIoTt+DfxCg==\",\"openid\":\"o80eu4jFQ5tKIZEU2F809pxeuI7M\"}");
        System.out.println(json);
        String ip = "113.104.165.122, 10.255.0.4";
        if (ip != null) {
            int index = ip.indexOf(",");
            if(index >= 0) {
                ip = ip.substring(0, index);
                System.out.println(ip);
            }

        }
    }
}
