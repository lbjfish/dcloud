package com.sida.xiruo.xframework.test;

import com.sida.xiruo.xframework.util.DESUtils;

public class Test {
    public static void main(String[] args) {
        System.out.println(DESUtils.getEncryptString("root"));
        System.out.println(DESUtils.getEncryptString("BuTong@123!?"));
        System.out.println(DESUtils.getDecryptString("fcdbf9d177ab3aac1e2b624e98e53686"));
        System.out.println(String.format("1.%s, 2.%s, 3.%s", "1", "2", "3", "4", "5", "6"));
    }
}
