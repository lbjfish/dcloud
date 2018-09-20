package com.sida.xiruo.xframework.util;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {

    /**
     *
     * 读取csv中的内容
     * @return
     * @throws IOException
     * @return List<Map<String,String>>
     * @see
     */
    public static List<Map<String,String>> readCSVFile(InputStream inputStream, int startRow,String unicode) throws IOException {
        List<Map<String,String>> strArrayList = new ArrayList<Map<String,String>>();
        CSVReader reader = null;
        DataInputStream in = new DataInputStream(inputStream);
        reader = new CSVReader(new InputStreamReader(in,unicode));
        String[] nextLine;
        int i = 1;
        Map<String,String> map;
        while ((nextLine = reader.readNext()) != null) {
            if (i>=startRow)
                if(BlankUtil.isNotEmpty(nextLine)){
                    map=new HashMap<>();
                    int s=0;
                    for (String value:nextLine){
                        map.put("cell"+s,BlankUtil.isEmpty(value)?value:value.trim());
                        s++;
                    }
                    strArrayList.add(map);
                }

            i++;
        }

        return strArrayList;
    }
    /**
     *
     * 读取csv中的内容
     * Map key:csvFileFirstRow csv文件，表头标题；
     *     key:csvFileContent  csv文件，内容(除去表头内容)
     *
     * @param file       csv文件对象
     * @param startRow   开始行
     * @param characters 分隔符
     * @return
     * @throws IOException
     * @return Map<String,List<String[]>>
     * @exception 异常描述
     * @see
     */
    public static Map<String, List<String[]>> readCSVFileWithMap(File file, int startRow, String characters) throws IOException{
        List<String[]> csvFileFirstRowArrayList = new ArrayList<String[]>();
        List<String[]> strArrayList = new ArrayList<String[]>();

        CSVReader reader = null;
        if (",".equalsIgnoreCase(characters)){
            reader = new CSVReader(new FileReader(file));
        } else {
            //带分隔符
            reader = new CSVReader(new FileReader(file),characters.toCharArray()[0]);
        }

        String[] nextLine;
        int i = 1;
        while ((nextLine = reader.readNext()) != null) {
//          System.out.println("Name: [" + nextLine[0] + "]\nAddress: [" + nextLine[1] + "]\nEmail: [" + nextLine[2] + "]");

            if (i>=startRow)
                strArrayList.add(nextLine);
            else
                csvFileFirstRowArrayList.add(nextLine);

            i++;
        }

        Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
        map.put("csvFileFirstRow", csvFileFirstRowArrayList);
        map.put("csvFileContent", strArrayList);
        return map;
    }
}
