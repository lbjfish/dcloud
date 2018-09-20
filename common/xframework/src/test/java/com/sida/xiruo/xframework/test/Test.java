package com.sida.xiruo.xframework.test;

import com.sida.xiruo.xframework.util.DESUtils;

public class Test {
    public static void main(String[] args) {
        System.out.println(DESUtils.getEncryptString("root"));
        System.out.println(DESUtils.getEncryptString("Aa@123"));
    }
}
