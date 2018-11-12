package com.sida.dcloud.operation.util;

import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.po.CompanyInfo;
import com.sida.dcloud.operation.po.TagGroup;
import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.service.CompanyInfoService;
import com.sida.dcloud.operation.service.TagGroupService;
import com.sida.dcloud.operation.service.TagLibService;
import com.sida.dcloud.operation.vo.CompanyInfoVo;
import com.sida.dcloud.operation.vo.TagGroupVo;
import com.sida.dcloud.operation.vo.TagLibVo;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(1)
public class OperationRedisUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(OperationRedisUtil.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TagGroupService tagGroupService;
    @Autowired
    private TagLibService tagLibService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    public void run(String... strings) {
        //清空缓存，测试时使用
//        clearMatcherInfoInRedis();
        loadTagInfoInRedis();
        loadCompanyInfoInRedis();
        loadCompanyRelTagInRedis();
    }

    public void clearMatcherInfoInRedis() {
        Map<String, Object> map = Optional.ofNullable(redisUtil.getEntriesFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER)).orElse(new HashMap<>());
        if(map.size() != 0) {
            redisUtil.removeMultiFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, map.keySet().toArray());
        }
    }

    private Map<String, Object> getMatcherMap() {
        Map<String, Object> map = Optional.ofNullable(redisUtil.getEntriesFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER))
                .orElseGet(() -> {
                    Map<String, Object> initMap = new HashMap<>();
                    redisUtil.putInMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, initMap);
                    return initMap;
                });
        return map;
    }

    /**
     * 加载标签数据到redis
     */
    public List<TagGroupVo> loadTagInfoInRedis() {
        Map<String, Object> map = getMatcherMap();
        return Optional.ofNullable(map.get(OperationConstants.REDIS_KEY_OPERATION_TAG_INFO)).map(obj -> (List<TagGroupVo>)obj).orElseGet(() -> {
            long sl = System.currentTimeMillis();
            LOG.info("开始加载标签数据到redis: {}", Xiruo.dateToString(sl, "yyyy-MM-dd HH:mm:ss.SSS"));
            TagGroup tagGroup = new TagGroup();
            tagGroup.setOrderField("sort");
            tagGroup.setOrderString("asc");
            List<TagGroupVo> groupList = tagGroupService.findAllList(tagGroup);
            TagLib tagLib = new TagLib();
            tagLib.setOrderField("sort");
            tagLib.setOrderString("asc");
            //设置版本
//            lib.setVersion("20181109");
            List<TagLibVo> libList = tagLibService.findAllList(tagLib);
            if(groupList.isEmpty() || libList.isEmpty()) {
                LOG.warn("标签数据异常: groupSize = {}, libSize = {}", groupList.size(), libList.size());
            } else {
                Map<String, List<TagLibVo>> listMap = new HashMap<>();
                libList.forEach(lib -> {
                    List<TagLibVo> tagList = Optional.ofNullable(listMap.get(lib.getGroupId())).orElseGet(() -> {
                        List<TagLibVo> list = new ArrayList<>();
                        listMap.put(lib.getGroupId(), list);
                        return list;
                    });
                    tagList.add(lib);
                });
                groupList.forEach(group -> {
                    group.setTagList(Optional.ofNullable(listMap.get(group.getId())).orElse(new ArrayList<>()));
                });
                redisUtil.putInMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, OperationConstants.REDIS_KEY_OPERATION_TAG_INFO, groupList);
                long el = System.currentTimeMillis();
                LOG.info("加载标签数据到redis完成: {}", Xiruo.dateToString(el, "yyyy-MM-dd HH:mm:ss.SSS"));
                LOG.info("加载标签数据到redis，用时: {}", Xiruo.milsecondToDesc(el - sl));
            }
            return groupList;
        });
    }

    public void clearTagInfoInRedis() {
        redisUtil.removeOneFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, OperationConstants.REDIS_KEY_OPERATION_TAG_INFO);
    }

    /**
     * 加载企业数据到redis
     */
    private Map<String, CompanyInfoVo> loadCompanyInfoInRedis() {
        Map<String, Object> map = getMatcherMap();
        return Optional.ofNullable(map.get(OperationConstants.REDIS_KEY_OPERATION_COMPANY_INFO)).map(obj -> (Map<String, CompanyInfoVo>)obj).orElseGet(() -> {
            long sl = System.currentTimeMillis();
            LOG.info("开始加载企业数据到redis: {}", Xiruo.dateToString(sl, "yyyy-MM-dd HH:mm:ss.SSS"));
            CompanyInfo param = new CompanyInfo();
            param.setOrderField("sort");
            param.setOrderString("asc");
            //此处用map更有效
//            List<CompanyInfoVo> companyList = companyInfoService.findAllInList(param);
            Map<String, CompanyInfoVo> companyMap = companyInfoService.findAllInMap(param);
            redisUtil.putInMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER,  OperationConstants.REDIS_KEY_OPERATION_COMPANY_INFO, companyMap);
            long el = System.currentTimeMillis();
            LOG.info("加载企业数据到redis完成: {}", Xiruo.dateToString(el, "yyyy-MM-dd HH:mm:ss.SSS"));
            LOG.info("加载企业数据到redis，用时: {}", Xiruo.milsecondToDesc(el - sl));
            return companyMap;
        });
    }

    public void clearCompanyInfoInRedis() {
        redisUtil.removeOneFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, OperationConstants.REDIS_KEY_OPERATION_COMPANY_INFO);
    }

    /**
     * 加载标签与企业的关联关系到redis
     */
    private List<String[]> loadCompanyRelTagInRedis() {
        Map<String, Object> map = getMatcherMap();
        return Optional.ofNullable(map.get(OperationConstants.REDIS_KEY_OPERATION_COMPANY_REL_TAG)).map(obj -> (List<String[]>)obj).orElseGet(() -> {
            long sl = System.currentTimeMillis();
            LOG.info("开始加载标签与企业的关联关系到redis: {}", Xiruo.dateToString(sl, "yyyy-MM-dd HH:mm:ss.SSS"));
            Map<String, Map<String, String>> relMap = tagLibService.findRelMapGroupByCompany();
            List<String[]> companyRelTagList = new ArrayList<>();
            relMap.forEach((companyId, valueMap) -> {
                companyRelTagList.add(new String[] {valueMap.get("tag_codes"), companyId, valueMap.get("tag_ids"), valueMap.get("count")});
            });
            redisUtil.putInMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER,  OperationConstants.REDIS_KEY_OPERATION_COMPANY_REL_TAG, companyRelTagList);
            long el = System.currentTimeMillis();
            LOG.info("加载标签与企业的关联关系到redis完成: {}", Xiruo.dateToString(el, "yyyy-MM-dd HH:mm:ss.SSS"));
            LOG.info("加载标签与企业的关联关系到redis，用时: {}", Xiruo.milsecondToDesc(el - sl));
            return companyRelTagList;
        });
    }

    public void clearCompanyRelTagInRedis() {
        redisUtil.removeOneFromMap(OperationConstants.REDIS_KEY_OPERATION_TAG_MATCHER, OperationConstants.REDIS_KEY_OPERATION_COMPANY_REL_TAG);
    }

    private static final int INTELLIGENT_MATCH_RESULT_SIZE = 5;

    /**
     * 智能匹配
     * {
     * '01':'1,2',
     * '02':'3,4,5,6,7',
     * '03':'8,9',
     * '04':'10,11',
     * 'groupId':'tagCode sequence'
     * }
     * @return
     */
    public Map<String, List<CompanyInfoVo>> intelligentMatch(Map<String, String> param) {
        long sl = System.currentTimeMillis();
        LOG.info("开始进行智能匹配: {}", Xiruo.dateToString(sl, "yyyy-MM-dd HH:mm:ss.SSS"));
        Map<String, List<CompanyInfoVo>> resultMap = new HashMap<String, List<CompanyInfoVo>>() {
            {
                put("exact", new ArrayList<>());//精确的
                put("inexact", new ArrayList<>());//不精确的
            }
        };
        //数据加载到内存
//        List<TagGroupVo> groupList = loadTagInfoInRedis();
        Map<String, CompanyInfoVo> companyMap =  loadCompanyInfoInRedis();
        List<String[]> relList = loadCompanyRelTagInRedis();
        List<String[]> seqList = new ArrayList<>();
        param.forEach((groupId, codeSeq) -> {
            if(BlankUtil.isNotEmpty(codeSeq)) {
                seqList.add(codeSeq.split(","));
            }
        });
        List<String[]> seqTempList = (List<String[]>)((ArrayList<String[]>) seqList).clone();
//        List list = (List)doExchange(seqTempList)
//                .stream()
//                .map(obj -> ".*,(" + Xiruo.arrayToStringNoQuote((List)obj, "|") + "),.*")
//                .collect(Collectors.toList());
        List list = doExchange(seqTempList);
        List<String> companyIdList = filterCompanyId(relList, list);

        int size = companyIdList.size();
        if(size > INTELLIGENT_MATCH_RESULT_SIZE) {
            while(resultMap.get("exact").size() < INTELLIGENT_MATCH_RESULT_SIZE) {
                //随机取
                resultMap.get("exact").add(companyMap.get(companyIdList.remove(RandomUtils.nextInt(new Random(), size))));
                size = companyIdList.size();
            }
        } else {
            companyIdList.forEach(companyId -> {
                resultMap.get("exact").add(companyMap.get(companyId));
            });
            //数量不够，进行模糊匹配
            if(size < INTELLIGENT_MATCH_RESULT_SIZE) {
                seqList.remove(seqList.size() - 1);
                inexactMatch(relList, seqList, resultMap.get("inexact"), companyMap,INTELLIGENT_MATCH_RESULT_SIZE - size);
            }
        }
        long el = System.currentTimeMillis();
        LOG.info("智能匹配完成: {}", Xiruo.dateToString(el, "yyyy-MM-dd HH:mm:ss.SSS"));
        LOG.info("智能匹配，用时: {}", Xiruo.milsecondToDesc(el - sl));
        return resultMap;
    }

    private void inexactMatch( List<String[]> relList,
                               List<String[]> seqList,
                               List<CompanyInfoVo> voList,
                               Map<String, CompanyInfoVo> companyMap,
                               int residualSize) {
        List<String[]> seqTempList = (List<String[]>)((ArrayList<String[]>) seqList).clone();
//        LOG.info("{}", seqTempList);
        List list = seqTempList;
        if(seqTempList.size() >= 2) {
            list = doExchange(seqTempList);
        }
        List<String> companyIdList = filterCompanyId(relList, list);
        int size = companyIdList.size();
        if(size > INTELLIGENT_MATCH_RESULT_SIZE) {
            while(voList.size() < INTELLIGENT_MATCH_RESULT_SIZE) {
                //随机取
                voList.add(companyMap.get(companyIdList.remove(RandomUtils.nextInt(new Random(), size))));
                size = companyIdList.size();
            }
        } else {
            companyIdList.forEach(companyId -> {
                voList.add(companyMap.get(companyId));
            });
            //数量不够，进行模糊匹配
            if(size < residualSize) {
                if(seqList.size() > 1) {
                    seqList.remove(seqList.size() - 1);
                    inexactMatch(relList, seqList, voList, companyMap, residualSize - size);
                }
            }
        }
    }

    private List<String> filterCompanyId(List<String[]> relList, List list) {
        return relList.stream().filter(values -> {
            for(Object obj : list) {
                if(matchTag(values[0], obj)) {
                    return true;
                }
            }
            return false;
        }).map(strArr -> strArr[1]).collect(Collectors.toList());
    }

    private boolean matchTag(String source, Object o) {
        source = "," + source + ",";
        //list是多维度组合数据，需要全匹配
        if(o instanceof List) {
            for(Object obj : (List)o) {
                if(source.indexOf("," + obj + ",") < 0) {
                    return false;
                }
            }
        } else {
            //Array是单维度，匹配其一即可，采用正则表达式
            return source.matches(".*,(" + Xiruo.arrayToStringNoQuote(Arrays.asList((String[])o), "|") + "),.*");
        }

        return true;
    }

    /**
     * 排列组合
     * @param arrayLists
     */
    public static List doExchange(List arrayLists){

        int len=arrayLists.size();
        //判断数组size是否小于2，如果小于说明已经递归完成了，否则你们懂得的，不懂？断续看代码
        if (len<2){
//            this.arrayLists=arrayLists;
            return (List)arrayLists.get(0);
        }
        //拿到第一个数组
        int len0;
        if (arrayLists.get(0) instanceof String[]){
            String[] arr0= (String[]) arrayLists.get(0);
            len0=arr0.length;
        }else {
            len0=((ArrayList<String>)arrayLists.get(0)).size();
        }

        //拿到第二个数组
        String[] arr1= (String[]) arrayLists.get(1);
        int len1=arr1.length;

        //计算当前两个数组一共能够组成多少个组合
        int lenBoth=len0*len1;

        //定义临时存放排列数据的集合
        ArrayList<ArrayList<String>> tempArrayLists=new ArrayList<>(lenBoth);

        //第一层for就是循环arrayLists第一个元素的
        for (int i=0;i<len0;i++){
            //第二层for就是循环arrayLists第二个元素的
            for (int j=0;j<len1;j++){
                //判断第一个元素如果是数组说明，循环才刚开始
                if (arrayLists.get(0) instanceof String[]){
                    String[] arr0= (String[]) arrayLists.get(0);
                    ArrayList<String> arr=new ArrayList<>();
                    arr.add(arr0[i]);
                    arr.add(arr1[j]);
                    //把排列数据加到临时的集合中
                    tempArrayLists.add(arr);
                }else {
                    //到这里就明循环了最少一轮啦，我们把上一轮的结果拿出来继续跟arrayLists的下一个元素排列
                    ArrayList<ArrayList<String>> arrtemp= (ArrayList<ArrayList<String>>) arrayLists.get(0);
                    ArrayList<String> arr=new ArrayList<>();
                    for (int k=0;k<arrtemp.get(i).size();k++){
                        arr.add(arrtemp.get(i).get(k));
                    }
                    arr.add(arr1[j]);
                    tempArrayLists.add(arr);
                }
            }
        }

        //这是根据上面排列的结果重新生成的一个集合
        List newArrayLists = new ArrayList<>();
        //把还没排列的数组装进来，看清楚i=2的喔，因为前面两个数组已经完事了，不需要再加进来了
        for (int i=2;i<arrayLists.size();i++){
            newArrayLists.add(arrayLists.get(i));
        }
        //记得把我们辛苦排列的数据加到新集合的第一位喔，不然白忙了
        newArrayLists.add(0,tempArrayLists);

        //你没看错，我们这整个算法用到的就是递归的思想。
        return doExchange(newArrayLists);
    }

    public static void main(String[] args) {
        List<String[]> seqList = new ArrayList<>();
        seqList.add(new String[] {"1", "2"});
        seqList.add(new String[] {"3", "4", "5", "6", "7"});
        seqList.add(new String[] {"8", "9"});
        seqList.add(new String[] {"10", "11"});
        List<String[]> tempList = (List<String[]>)((ArrayList<String[]>) seqList).clone();
        List list = (List)doExchange(tempList)
                .stream()
                .map(obj -> ".*,(" + Xiruo.arrayToStringNoQuote((List)obj, "|") + "),.*")
                .collect(Collectors.toList());
        System.out.println(list);
        System.out.println(tempList);

        System.out.println(",1,2,3,4,5,6,7,8,9,10,".matches(".*(?=,11,).*"));
//        for(int i = 0; i < 10; i++) {
//            System.out.println(RandomUtils.nextInt(new Random(), 6));
//        }
    }
}
