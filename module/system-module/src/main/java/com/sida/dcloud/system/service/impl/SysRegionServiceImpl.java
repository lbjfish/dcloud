package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.auth.vo.RegionTreeDTO;
import com.sida.dcloud.system.common.CollectorConstants;
import com.sida.dcloud.system.common.SystemCacheUtil;
import com.sida.dcloud.system.dao.SysRegionMapper;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.dcloud.system.service.FileService;
import com.sida.dcloud.system.service.SysRegionService;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.common.util.PinYinUtil;
import com.sida.xiruo.common.util.SystemUtil;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import com.google.common.collect.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class SysRegionServiceImpl extends BaseServiceImpl<SysRegion> implements SysRegionService {
    private static final Logger logger = LoggerFactory.getLogger(SysRegionServiceImpl.class);

    @Autowired
    private SysRegionMapper sysRegionMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private SystemCacheUtil systemCacheUtil;

    @Override
    public IMybatisDao<SysRegion> getBaseDao() {
        return sysRegionMapper;
    }

    @Override
    public List<RegionTreeDTO> findTree(){
        List<SysRegion> list = this.findAll();
        List<RegionTreeDTO> dtoList = Lists.newArrayList();
        for (SysRegion region : list){
            RegionTreeDTO dto = new RegionTreeDTO();
            dto.setId(region.getId());
            dto.setName(region.getName());
            dto.setCode(region.getCode());
            dto.setLevel(region.getLevel());
            dto.setSort(region.getSort());
            dto.setParentId(region.getParentId());
            dtoList.add(dto);
        }
        return BuildTree.buildTree(dtoList);
    }

    @Override
    public List<SysRegionLayerDto> findThreeLevelTree(){
        return (List<SysRegionLayerDto>)systemCacheUtil.getRegionDataByKey(RedisKey.SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER);
    }

    @Override
    public List<SysRegion> findAll() {
        SysRegion condition = new SysRegion();
        condition.setOrderField("sort");
        condition.setOrderString("asc");
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        return sysRegionMapper.selectByCondition(condition);
    }

    @Override
    public SysRegion selectByCode(String code) {
        SysRegion condition = new SysRegion();
        condition.setDeleteFlag(false);
        condition.setCode(code);
        List<SysRegion> list = sysRegionMapper.selectByCondition(condition);
        if (list!=null && list.size() == 1){
            return list.get(0);
        }
        return new SysRegion();
    }

    @Override
    public int deleteById(String id){
        return sysRegionMapper.deleteById(id);
    }

    @Override
    public void createRegionXml() {
        createRegionXml(SecConstant.REGION_XML_FILE_PATH);
    }

    @Override
    public Document createRegionXml(String regionPath) {
        //触发一次覆盖一次
        File file = new File(regionPath);

        final Document document = DocumentHelper.createDocument();
        final Element rootElement = document.addElement("root");

        final List<SysRegion> regionList = this.findAll();

        SysRegion china = this.selectByCode(SecConstant.CHINA);
        if(regionList.size() > 0) {
            createRegionMenu(rootElement, regionList, china.getId());
        }
        regionList.clear();

        try {
            SystemUtil.createXMLOutput(new FileOutputStream(file), document);
        } catch(IOException ioe) {
            throw new ServiceException(ErrorCodeEnums.EXPORT.getCode(),ErrorCodeEnums.EXPORT.getMessage()+ioe.getMessage());
        }

        return document;
    }

    private void createRegionMenu(Element pElement
            , List<SysRegion> regionList
            , String parentRegionId){
        SysRegion region = null;
        Element element = null;
        for(int i = 0; i < regionList.size();) {
            region = regionList.get(i);
            String tempParentId = "";
            try {
//                tempParentId = region.getParentRegion().getRegionId();
                tempParentId = region.getParentId()==null?"":region.getParentId();
            } catch (Exception e) {
                tempParentId = "";
            }
            if(tempParentId.equals(parentRegionId)) {
                element = pElement.addElement("region");
                createRegionNode(element, region);
                regionList.remove(i);
                createRegionMenu(element, regionList, region.getId());
                i = 0;
            } else {
                i++;
            }
        }
    }

    private void createRegionNode(Element element, SysRegion region) {
        element.addAttribute("regionId", Xiruo.nullToEmpty(region.getId()));
        element.addAttribute("regionName", Xiruo.nullToEmpty(region.getName()));
        element.addAttribute("regionCode", Xiruo.nullToEmpty(region.getCode()));
        element.addAttribute("regionPath", Xiruo.nullToEmpty(region.getPath()));
        element.addAttribute("regionParentId", Xiruo.nullToEmpty(region.getParentId()));
        element.addAttribute("dataNum", Xiruo.nullToEmpty(region.getSort()));
        element.addAttribute("valid", Xiruo.nullToInt(region.getDeleteFlag()) + "");
    }

    @Override
    public Object findCitys() {
        List<Map<String,Object>> list = Lists.newArrayList();
        //热门城市:北上广深莞
        Map<String,Object> hotCitysMap = new HashMap<>();
        List<Map<String,Object>> hotCitys = Lists.newArrayList();
        Map<String,Object> BJ = new HashMap<>();
        BJ.put("value","110000");
        BJ.put("label","北京市");
        hotCitys.add(BJ);
        Map<String,Object> SH = new HashMap<>();
        SH.put("value","310000");
        SH.put("label","上海市");
        hotCitys.add(SH);
        Map<String,Object> GZ = new HashMap<>();
        GZ.put("value","440100");
        GZ.put("label","广州市");
        hotCitys.add(GZ);
        Map<String,Object> SZ = new HashMap<>();
        SZ.put("value","440300");
        SZ.put("label","深圳市");
        hotCitys.add(SZ);
        Map<String,Object> DG = new HashMap<>();
        DG.put("value","441900");
        DG.put("label","东莞市");
        hotCitys.add(DG);
        hotCitysMap.put("label","热门城市");
        hotCitysMap.put("options",hotCitys);
        list.add(hotCitysMap);
        List<SysRegion> citys = sysRegionMapper.findCitys();
        if (BlankUtil.isNotEmpty(citys)){
            Map<String,Object> cityMap = new HashMap<>();
            List<Map<String,Object>> cityList = Lists.newArrayList();
            for (SysRegion po : citys){
                    Map<String,Object> map = new HashMap<>();
                    map.put("value",po.getCode());
                    map.put("label",po.getName());
                    cityList.add(map);
            }
            cityMap.put("label","其他城市");
            cityMap.put("options",cityList);
            list.add(cityMap);
        }

            //按省分组
//            Map<String,String> pMap = getProvinceMap();
//            for (Map.Entry<String, String> entry : pMap.entrySet()){
//                Map<String,Object> cityMap = new HashMap<>();
//                cityMap.put("label",entry.getValue());
//                List<Map<String,Object>> cityList = getCityList(entry.getKey(),citys);
//                if (BlankUtil.isNotEmpty(cityList)){
//                    cityMap.put("options",cityList);
//                    list.add(cityMap);
//                }
//            }
        return list;
    }

    private List<Map<String, Object>> getCityList(String key, List<SysRegion> citys) {
        List<Map<String,Object>> list = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(citys) && BlankUtil.isNotEmpty(key)){
            for (SysRegion po : citys){
                if (BlankUtil.isNotEmpty(po.getParentId()) && po.getParentId().equals(key)){
                    Map<String,Object> map = new HashMap<>();
                    map.put("value",po.getCode());
                    map.put("label",po.getName());
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public Map<String, String> getProvinceMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        List<SysRegion> provinceList = sysRegionMapper.findProvinces();
        if (BlankUtil.isNotEmpty(provinceList)){
            for (SysRegion po : provinceList){
                map.put(po.getId(),po.getName());
            }
        }
        return map;
    }

    @Override
    @Transactional
    public void init() {
        //调用爬虫获取省市区数据
        System.setProperty(CollectorConstants.DRIVERPATH_KEY, CollectorConstants.DRIVERPATH_VALUE);
        //获取驱动
        WebDriver webDriver = new PhantomJSDriver();
        List<WebElement> areaElements;

        //这里配置国家统计局官网省市区数据（最新）
        webDriver.get(CollectorConstants.WEBSITE_FOR_REGION);

        areaElements = webDriver.findElements(By.className(CollectorConstants.MsoNormal));

        String parentId = null;
        String path = null;
        String namePath = null;
        String codePath = null;

        String lastProvinceId = null;
        String lastProvinceName = null;
        String lastProvinceCode = null;
        String lastCityId = null;
        String lastCityName = null;
        String lastCityCode = null;

        List<SysRegion> regionList = Lists.newArrayList();

        //手动设置“中国”的数据
        SysRegion china = new SysRegion();
        china.setId(UUIDGenerate.getNextId());
        china.setCreatedAt(new Date());
        china.setLastUpdated(new Date());
        china.setDisable(false);
        china.setDeleteFlag(false);
        china.setCode("China");
        china.setName("中国");
        china.setLevel("COUNTRY");
        china.setParentId(null);
        china.setPath(china.getId()+",");
        china.setNamePath(china.getName()+",");
        china.setPostCode(china.getCode());
        china.setCodePath(china.getCode()+",");
        china.setSort(1);
        regionList.add(china);

        int i = 2;
        for (WebElement web : areaElements){
            List<WebElement> doubleElements = Lists.newArrayList();
            Date date = new Date();
            String code;
            String name;
            String type;
            String id = UUIDGenerate.getNextId();

            doubleElements = web.findElements(By.tagName("span"));

            //找出层级结构：省市区
            if (doubleElements.size() == 3){
                type = "PROVINCE";
            }else if (doubleElements.get(2).getText().length() == 8){
                type = "CITY";
            }else {
                type = "AREA";
            }

            //获取code和name
            if ("PROVINCE".equals(type)){
                code = doubleElements.get(0).getText().trim();
                name = doubleElements.get(2).getText().trim();
            }else {
                code = doubleElements.get(1).getText().trim();
                name = doubleElements.get(3).getText().trim();
                if (code.equals("110100")){
                    name = "北京市";
                }if (code.equals("120100")){
                    name = "天津市";
                }if (code.equals("310100")){
                    name = "上海市";
                }if (code.equals("500000")){
                    name = "重庆市";
                }
            }

            //组装参数
            if ("PROVINCE".equals(type)){
                parentId = china.getId();
                path = china.getPath() + id + ",";
                namePath = china.getNamePath() + name + ",";
                codePath = code + ",";
                lastProvinceId = id;
                lastProvinceName = name;
                lastProvinceCode = code;
                lastCityId = null;
                lastCityName = null;
                lastCityCode = null;

            }else if ("CITY".equals(type)){
                parentId = lastProvinceId;
                path = china.getPath() + lastProvinceId + ","+id + ",";
                namePath = china.getNamePath() + lastProvinceName + ","+ name + ",";
                codePath = lastProvinceCode + "," + code + ",";
                lastCityId = id;
                lastCityName = name;
                lastCityCode = code;

            }else {
                parentId = lastCityId;
                path = china.getPath() + lastProvinceId + "," + lastCityId + "," + id + ",";
                namePath = china.getNamePath() + lastProvinceName + "," + lastCityName + "," + name + ",";
                codePath = lastProvinceCode + "," + lastCityCode + "," + code + ",";
            }
            SysRegion region = new SysRegion();
            region.setId(id);
            region.setCreatedAt(date);
            region.setLastUpdated(date);
            region.setDisable(false);
            region.setDeleteFlag(false);
            region.setCode(code);
            region.setName(name);
            region.setLevel(type);
            region.setParentId(parentId);
            region.setPath(path);
            region.setNamePath(namePath);
            region.setPostCode(code);
            region.setSort(i);
            region.setCodePath(codePath);

            regionList.add(region);
            i = i+1;
        }

        //批量录入数据库
        if (BlankUtil.isNotEmpty(regionList)){
            sysRegionMapper.removeAll();

            //分组批量插入：防止执行sql大小超过数据库字符串缓冲区大小
            int splitNum = 1000; //分段条数
            int startPage = 0; //开始页数
            int endPage = regionList.size() / splitNum;

            for (int page = startPage; page <= endPage; page ++){
                List<SysRegion> list = Lists.newArrayList();
                //非最后一次
                if (page != endPage){
                    //前闭后开区间subList[0,1000) 表示返回索引为0-999的数据
                    list = regionList.subList(page*splitNum,(page+1)*splitNum);
                    System.out.println(list.size());
                    sysRegionMapper.batchInsert(list);
                }
                //最后一次
                else {
                    list = regionList.subList(page*splitNum,regionList.size());
                    System.out.println(list.size());
                    sysRegionMapper.batchInsert(list);
                }
            }
        }

        //关闭webDriver驱动
        webDriver.close();

        //创建xml文件
        this.createRegionXml();

        //上传至七牛云
        Map<String,String> downloadUrlMap = fileService.uploadPublic(SecConstant.REGION_XML_FILE_PATH,SecConstant.REGION_XML_FILE_NAME,SecConstant.REGION_XML_FILE_KEY);
        if (BlankUtil.isNotEmpty(downloadUrlMap)){
            System.out.println(downloadUrlMap.get("downloadUrl"));
        }
    }

    @Override
    public Map<String, String> findNameMapByCodeList(List<String> param) {
        Map<String, String> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(param)){
            List<SysRegion> list = sysRegionMapper.findNameListByCodeList(param);
            if (BlankUtil.isNotEmpty(list)){
                for (SysRegion po : list){
                    map.put(po.getCode(),po.getName());
                }
            }
        }
        return map;
    }

    @Override
    public String getNameByCode(String code) {
        SysRegion region = new SysRegion();
        region.setCode(code);
        List<SysRegion> regions = sysRegionMapper.selectByCondition(region);
        if(BlankUtil.isNotEmpty(regions)) {
            return regions.get(0).getName();
        }
        return null;
    }

    @Override
    public List<SysRegionLayerDto> findSysRegionSingleLayerDtoByLevel(String level) {
        if("CITY".equalsIgnoreCase(level)) {
            return (List<SysRegionLayerDto>)systemCacheUtil.getRegionDataByKey(RedisKey.SYS_REGION_CACHE_WITH_CITY);
        } else if("PROVINCE".equalsIgnoreCase(level)) {
            return (List<SysRegionLayerDto>)systemCacheUtil.getRegionDataByKey(RedisKey.SYS_REGION_CACHE_WITH_PROVINCE);
        } else if("COUNTRY".equalsIgnoreCase(level)) {
            return (List<SysRegionLayerDto>)systemCacheUtil.getRegionDataByKey(RedisKey.SYS_REGION_CACHE_WITH_COUNTRY);
        }
       throw new ServiceException("参数有误");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateSysRegionPinyin() {
        sysRegionMapper.selectByCondition(new SysRegion()).forEach(region -> {
            SysRegionLayerDto dto = new SysRegionLayerDto();
            dto.setId(region.getId());
            dto.setPinyin(PinYinUtil.getPingYin(region.getName()));
            dto.setCapitalPinyin(PinYinUtil.getFirstSpell(region.getName()));
            sysRegionMapper.updateSysRegionPinyin(dto);
        });
        return 0;
    }
}