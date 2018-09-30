package com.sida.dcloud.activity.util;

import com.google.common.base.CaseFormat;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.xframework.util.DESUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Test {
    public static void main(String[] args) {
        //e10adc3949ba59abbe56e057f20f883e
        System.out.println(MD5Util.MD5PWD("123456"));
        Field[] fields = CustomerActivitySignupNote.class.getDeclaredFields();
        Arrays.stream(fields).map(field -> field.getName()).forEach(System.out::println);

        Map<String, String> map = new HashMap<String, String>() {
            {
                put("1", "1");
                put("2", "2");
                put("3", "3");
                put("4", "4");
            }
        };
        String idString = ",2,3,";
        map.values().stream().filter(s -> idString.indexOf(String.format(",%s,", s)) >= 0).collect(Collectors.toList()).forEach(map::remove);
        System.out.println(map);

        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//test-data

        Optional.ofNullable(null).ifPresent(System.out::println);
    }
}
