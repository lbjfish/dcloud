package com.sida.dcloud.operation.test.excel;

import com.sida.xiruo.common.util.PinYinUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.ExcelBuilder;
import com.sida.xiruo.xframework.util.UUIDGenerate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyInfoRunner {
    /**
     *     id, created_user, created_at, updated_user, last_updated, disable, delete_flag, red_string1,
     *     red_string2, red_string3, red_string4, red_string5, red_string6, sort, name, code,
     *     short_name, eng_name, eng_short_name, region_id, address, legal_representative, link_man,
     *     phone, mobile, email, homepage, found_date, person_total, logo_url, company_cat, read_count,
     *     lauded_count, commented_count, cared_count, title_count, comment_count, laud_count,
     *     care_count, active_status, online_minutes, facebook_no, twitter_no, sina_weibo,
     *     vplus, major_scope, honord_items, brief, remark
     */
    private static final String COMPANY_TEMP = "INSERT INTO `company_info` VALUES (" +
            "'%s', '100', now(), '100', now(), false, false, ''," +
            "'','','','','',0,'%s',''," +
            "'','%s','%s','%s','%s','',''," +
            "'%s','%s','%s','%s','%s','%s','','',0," +
            "0,0,0,0,0,0," +
            "0,false,0,'','',''," +
            "0, '', '','',''" +
            ");";
    /**
     * 企业信息导入后用以下语句更新
     update company_info a set a.remark = (select id from system.sys_region b where b.level = 'CITY' and instr(b.name, a.region_id) > 0);
     update company_info a set a.remark = (select id from system.sys_region b where b.level = 'COUNTRY' and instr(b.name, a.region_id) > 0) where a.remark is null;
     update company_info set region_id = remark,remark = null;
     */

    /**
     *     id, disable, delete_flag, red_string1, red_string2, red_string3, red_string4, red_string5,
     *     red_string6, name, group_id, add_time, code, alias, eng_name, version, sort, remark
     */
    private static final String TAG_LIB_TEMP = "INSERT INTO `tag_lib` VALUES (" +
            "'%s', false, false, '','','','',''," +
            "'','%s','%s',now(),'%s','','','20181109',0,''" +
            ");";

    private static final String TAG_REL_TEMP = "INSERT INTO `company_rel_tag` VALUES (" +
            "'%s', '%s',''" +
            ");";
    /**
     * 标签数据导入后用以下语句更新
     update company_rel_tag a join tag_lib b on a.tag_id = b.name  set a.tag_id = b.id,a.tag_code = b.code;
     */

    /**
     * 1. 产品领域
     * 2. 具体产品
     * 3. 公司属性
     * 4. 所在地区
     */

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        File file = new File("C:/files/设计云/excel数据/展商标签-20181110-imp.xlsx");
        try {
            Map<String, Set<String>> tagMap = new HashMap<String, Set<String>>() {
                {
                    put("1", new HashSet<>());
                    put("2", new HashSet<>());
                    put("3", new HashSet<>());
                    put("4", new HashSet<>());
                }
            };
            List<Map<String, String>> list = ExcelBuilder.readExcelandToList(new FileInputStream(file));
            list.forEach(map -> {
                String id = UUIDGenerate.getNextId();
                //企业信息
                Optional.of(String.format(COMPANY_TEMP,
                        id,
                        filterUnvalidChar(map.get("cell0")),
                        PinYinUtil.getFirstSpell(filterUnvalidChar(map.get("cell0"))).toUpperCase(),
                        filterUnvalidChar(map.get("cell1")),
                        filterUnvalidChar(map.get("cell5")),
                        filterUnvalidChar(map.get("cell4")),
                        filterUnvalidChar(map.get("cell6")),
                        filterUnvalidChar(map.get("cell7")),
                        filterUnvalidChar(map.get("cell8")),
                        filterUnvalidChar(map.get("cell9")),
                        BlankUtil.isEmpty(map.get("cell10")) ? "1970-01-01" : (filterUnvalidChar(map.get("cell10")) + "-01-01"),
                        filterUnvalidChar(map.get("cell11"))
                )).ifPresent(System.out::println);

                String tag_3_1 = filterUnvalidChar(map.get("cell12"));
                String tag_3_2 = filterUnvalidChar(map.get("cell13"));
                String tag_3_3 = filterUnvalidChar(map.get("cell14"));
                String tag_3_4 = filterUnvalidChar(map.get("cell15"));
                String tag_1_1 = filterUnvalidChar(map.get("cell16"));
                String tag_1_2 = filterUnvalidChar(map.get("cell17"));
                String tag_1_3 = filterUnvalidChar(map.get("cell18"));
                String tag_1_4 = filterUnvalidChar(map.get("cell19"));
                String tag_1_5 = filterUnvalidChar(map.get("cell20"));
                String tag_1_6 = filterUnvalidChar(map.get("cell21"));
                String tag_1_7 = filterUnvalidChar(map.get("cell22"));
                String tag_1_8 = filterUnvalidChar(map.get("cell23"));
                String tag_1_9 = filterUnvalidChar(map.get("cell24"));
                String tag_1_10 = filterUnvalidChar(map.get("cell25"));
                String tag_1_11 = filterUnvalidChar(map.get("cell26"));
                String tag_1_12 = filterUnvalidChar(map.get("cell27"));
                String tag_1_13 = filterUnvalidChar(map.get("cell28"));
                String tag_1_14 = filterUnvalidChar(map.get("cell29"));
                String tag_1_15 = filterUnvalidChar(map.get("cell30"));
                String tag_2_1 = filterUnvalidChar(map.get("cell31"));
                String tag_2_2 = filterUnvalidChar(map.get("cell32"));
                String tag_2_3 = filterUnvalidChar(map.get("cell33"));
                String tag_2_4 = filterUnvalidChar(map.get("cell34"));
                String tag_2_5 = filterUnvalidChar(map.get("cell35"));
                String tag_4_1 = filterUnvalidChar(map.get("cell5"));

                if(BlankUtil.isNotEmpty(tag_3_1)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_3_1)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_3_2)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_3_2)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_3_3)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_3_3)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_3_4)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_3_4)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_1_1)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_1)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_1_2)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_2)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_1_3)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_3)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_1_4)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_4)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_1_5)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_5)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_6)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_6)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_7)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_7)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_8)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_8)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_9)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_9)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_10)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_10)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_11)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_11)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_12)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_12)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_13)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_13)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_14)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_14)).ifPresent(System.out::println);
                }

                if(BlankUtil.isNotEmpty(tag_1_15)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_1_15)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_2_1)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_2_1)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_2_2)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_2_2)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_2_3)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_2_3)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_2_4)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_2_4)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_2_5)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_2_5)).ifPresent(System.out::println);
                }
                if(BlankUtil.isNotEmpty(tag_4_1)) {
                    Optional.of(String.format(TAG_REL_TEMP, id, tag_4_1)).ifPresent(System.out::println);
                }

                //去重
                tagMap.get("3").add(tag_3_1);
                tagMap.get("3").add(tag_3_2);
                tagMap.get("3").add(tag_3_3);
                tagMap.get("3").add(tag_3_4);
                tagMap.get("1").add(tag_1_1);
                tagMap.get("1").add(tag_1_2);
                tagMap.get("1").add(tag_1_3);
                tagMap.get("1").add(tag_1_4);
                tagMap.get("1").add(tag_1_5);
                tagMap.get("1").add(tag_1_6);
                tagMap.get("1").add(tag_1_7);
                tagMap.get("1").add(tag_1_8);
                tagMap.get("1").add(tag_1_9);
                tagMap.get("1").add(tag_1_10);
                tagMap.get("1").add(tag_1_11);
                tagMap.get("1").add(tag_1_12);
                tagMap.get("1").add(tag_1_13);
                tagMap.get("1").add(tag_1_14);
                tagMap.get("1").add(tag_1_15);
                tagMap.get("2").add(tag_2_1);
                tagMap.get("2").add(tag_2_2);
                tagMap.get("2").add(tag_2_3);
                tagMap.get("2").add(tag_2_4);
                tagMap.get("2").add(tag_2_5);
                tagMap.get("4").add(tag_4_1);
//                System.out.println(map);
            });
            //标签信息
            AtomicInteger ai = new AtomicInteger(0);
            tagMap.keySet().forEach(groupId -> {
                tagMap.get(groupId).stream().filter(BlankUtil::isNotEmpty).forEach(tag -> {
                    Optional.of(String.format(TAG_LIB_TEMP, UUIDGenerate.getNextId(), tag, groupId, ai.incrementAndGet())).ifPresent(System.out::println);
                });
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String filterUnvalidChar(String source) {
        if(BlankUtil.isEmpty(source)) {
            return "";
        }
        return source.replaceAll("[\\r\\n]", "").replaceAll("'", "\\\\'");
    }
}
