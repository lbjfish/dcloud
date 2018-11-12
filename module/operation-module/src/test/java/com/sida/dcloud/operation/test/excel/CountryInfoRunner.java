package com.sida.dcloud.operation.test.excel;

import com.sida.xiruo.xframework.util.ExcelBuilder;
import com.sida.xiruo.xframework.util.UUIDGenerate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CountryInfoRunner {

    //id,org_id,created_at,last_updated,created_user,updated_user,disable,delete_flag,code,name,level,
    // parent_id,path,name_path,post_code,sort,code_path,pinyin,capital_pinyin
    private static final String SQL_TEMP = "INSERT INTO `sys_region` VALUES ('%s', null, now(), now(), null, null, false, false, '%s', '%s', 'COUNTRY', null, '%s,', '%s,', '%s', '0', '%s,', '%s', '%s');";
    public static void main(String[] args) {
        File file = new File("C:/files/设计云/excel数据/国家代码.xlsx");
        try {
            List<Map<String, String>> list = ExcelBuilder.readExcelandToList(new FileInputStream(file));
            list.forEach(map -> {
                String id = UUIDGenerate.getNextId();
                Optional.of(String.format(SQL_TEMP,
                        id,
                        map.get("cell1"),
                        map.get("cell0"),
                        id,
                        map.get("cell0"),
                        map.get("cell1"),
                        map.get("cell1"),
                        map.get("cell2"),
                        map.get("cell3")
                )).ifPresent(System.out::println);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
