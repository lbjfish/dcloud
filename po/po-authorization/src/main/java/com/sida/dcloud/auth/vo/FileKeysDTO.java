package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 获取文件目录
 */
public class FileKeysDTO {

    //请求参数
    @ApiModelProperty("文件名字符串，多个则英文逗号分隔")
    private String fileKeyStr;

    @ApiModelProperty("图片样式，如：imageView2/1/w/200/h/200")
    private String style;

    @ApiModelProperty("字符串分隔符，若设值则用于fileKeyStr字段的分隔解析")
    private String separator;

    @ApiModelProperty("文件名列表")
    private List<String> fileKeyList;

    //返回参数
    @ApiModelProperty("本次所有的下载路径，英文逗号拼接到一个字符串里")
    private String downUrlStr;

    @ApiModelProperty("本次所有的下载路径，封装在列表里")
    private List<String> downUrlList;

    @ApiModelProperty("本次所有的下载路径，map集合，key为传入的文件名，value为下载路径")
    private Map<String,String> downUrlMap;

    public String getFileKeyStr() {
        return fileKeyStr;
    }

    public void setFileKeyStr(String fileKeyStr) {
        this.fileKeyStr = fileKeyStr;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public List<String> getFileKeyList() {
        return fileKeyList;
    }

    public void setFileKeyList(List<String> fileKeyList) {
        this.fileKeyList = fileKeyList;
    }

    public String getDownUrlStr() {
        return downUrlStr;
    }

    public void setDownUrlStr(String downUrlStr) {
        this.downUrlStr = downUrlStr;
    }

    public List<String> getDownUrlList() {
        return downUrlList;
    }

    public void setDownUrlList(List<String> downUrlList) {
        this.downUrlList = downUrlList;
    }

    public Map<String, String> getDownUrlMap() {
        return downUrlMap;
    }

    public void setDownUrlMap(Map<String, String> downUrlMap) {
        this.downUrlMap = downUrlMap;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
