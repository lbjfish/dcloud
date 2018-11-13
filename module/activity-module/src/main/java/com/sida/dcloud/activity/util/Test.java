package com.sida.dcloud.activity.util;

import com.google.common.base.CaseFormat;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.common.util.Xiruo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public final class Test {
    static class LOG {
        private static final Logger logger = LoggerFactory.getLogger(Test.class);
        public static void info(Object msg) {
            logger.info(String.valueOf(msg));
        }

        public static void info(Object msg, String...args) {
            logger.info(String.valueOf(msg), args);
        }
    }
    
    public static void main(String[] args) throws Exception {
        //e10adc3949ba59abbe56e057f20f883e
        LOG.info(MD5Util.MD5PWD("123456"));
        Field[] fields = CustomerActivitySignupNote.class.getDeclaredFields();
        Arrays.stream(fields).map(field -> field.getName()).forEach(LOG::info);

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
        LOG.info(map.toString());

        LOG.info(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
        LOG.info(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
        LOG.info(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData

        LOG.info(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
        LOG.info(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
        LOG.info(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//test-data

        Optional.ofNullable(null).ifPresent(System.out::println);

        LocalDateTime datetime = LocalDateTime.now();
        LOG.info(String.format("%s-%s-%s %s:%s:%s"
                , datetime.getYear(), datetime.getMonthValue(), datetime.getDayOfMonth(), datetime.getHour(), datetime.getMinute(), datetime.getSecond()));
        LOG.info(datetime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.MINUTE));
        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.HOUR));
        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.DAY));
        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.WEEK));
        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.MONTH));
        LOG.info(Xiruo.getLiveTime(Xiruo.TIMEPOINT.YEAR));

        String ationNo = "activity_order_20181005_00000000001";
        int lastIndex = ationNo.lastIndexOf("_");
        LOG.info(Xiruo.nullToLong(ationNo.substring(lastIndex + 1)));

        int capacity = 1;
        int initialCapacity = 17;
        while (capacity < initialCapacity)
            capacity <<= 1;
        LOG.info(capacity);

        LOG.info(String.valueOf(System.currentTimeMillis()).length());

        ActivityInfo info = new ActivityInfo();
        info.setId("2132113");
        info.setName("namename");
        Map<String, String> mm = BeanUtils.describe(info);
        LOG.info(mm.get("name"));
    }
}
