package com.sida.dcloud.activity.util;

import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.xframework.util.DESUtils;

public final class Test {
    public static void main(String[] args) {
        //e10adc3949ba59abbe56e057f20f883e
        System.out.println(MD5Util.MD5PWD("123456"));
    }
}
