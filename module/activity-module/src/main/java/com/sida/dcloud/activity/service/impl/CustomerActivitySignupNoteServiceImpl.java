package com.sida.dcloud.activity.service.impl;

//import com.codingapi.tx.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.client.OperationClient;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.CustomerActivitySignupNoteMapper;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.dto.ActivitySignupNoteSettingDto;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import net.sf.json.regexp.RegexpUtils;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class CustomerActivitySignupNoteServiceImpl extends BaseServiceImpl<CustomerActivitySignupNote> implements CustomerActivitySignupNoteService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerActivitySignupNoteServiceImpl.class);
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + CustomerActivitySignupNoteServiceImpl.class.getName();
    public static final String ACTION_NO_KEY = "SIGNUP";
    public static final String THIRD_PART_CODE_KEY = "THIRD_PART_CODE";
    private static final Map<String, Field> NOTE_FIELD_MAP = new HashMap<>();

    static {
        Field[] fieldArray = CustomerActivitySignupNote.class.getDeclaredFields();
        Arrays.stream(fieldArray).forEach(field -> NOTE_FIELD_MAP.put(field.getName(), field));
    }

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private ActivityCacheUtil activityCacheUtil;
    @Autowired
    private OperationClient operationClient;

    @Autowired
    private CustomerActivitySignupNoteMapper customerActivitySignupNoteMapper;
    @Autowired
    private ActivityOrderService activityOrderService;
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivitySignupNoteSettingService activitySignupNoteSettingService;

    @Override
    public IMybatisDao<CustomerActivitySignupNote> getBaseDao() {
        return customerActivitySignupNoteMapper;
    }


    @Override
    public List<TableMeta> findTableMeta() {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setTableSchema("activity");
        tableMeta.setTableName("customer_activity_signup_note");
        return customerActivitySignupNoteMapper.findTableMeta(tableMeta);
    }

    @Override
    public Page<CustomerActivitySignupNoteVo> findPageList(CustomerActivitySignupNoteVo vo) {
        PageHelper.startPage(vo.getP(),vo.getS());
//        vo.setUserId(LoginManager.getCurrentUserId());
        List<CustomerActivitySignupNoteVo> voList = customerActivitySignupNoteMapper.findVoList(vo);
        Map<String, Object> map = (Map<String, Object>)activityCacheUtil.getRedisUtil().getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        voList.forEach(o -> o.setRegionName(((SysRegionLayerDto)map.get(vo.getRegionId())).getName()));
        return (Page) voList;
    }

    @Override
    public Map<String, String> findSimpleOneToClient(String id) {
        CustomerActivitySignupNote note = customerActivitySignupNoteMapper.selectByPrimaryKey(id);
        Optional.ofNullable(note).orElseThrow(() -> new ActivitiException("报名表不存在，id=" + id));
        ActivityOrder order = new ActivityOrder();
        order.setNoteId(note.getId());
        List<ActivityOrder> orderList = activityOrderService.selectByCondition(order);
        if(!orderList.isEmpty()) order = orderList.get(0);
        Map<String, String> resMap = new HashMap<>();
        resMap.put("id", note.getId());
        resMap.put("signCode", note.getThirdPartCode());
        resMap.put("name", note.getName());
        resMap.put("faceUrl", note.getFaceUrl());
        resMap.put("orderId", order.getId());
        resMap.put("userId", note.getUserId());
        resMap.put("activityId", note.getActivityId());

        return resMap;
    }

    @Override
    public List<ActivitySignupNoteSettingDto> findOneToClient(String id) {
        CustomerActivitySignupNote note = customerActivitySignupNoteMapper.selectByPrimaryKey(id);
        Optional.ofNullable(note).orElseThrow(() -> new ActivitiException("报名表不存在，id=" + id));
        String version = note.getVersion();
        List<ActivitySignupNoteSettingDto> settingList = activitySignupNoteSettingService.selectByVersionToClient(version);
        settingList.forEach(setting -> {
            Optional.ofNullable(NOTE_FIELD_MAP.get(setting.getCode())).ifPresent(field -> {
                field.setAccessible(true);
                try {
                    Object value = field.get(note);
                    setting.setValue(value);
                } catch (IllegalAccessException e) {
                    throw new ActivityException(e);
                }
            });
        });
        return settingList;
    }

    @Override
    public String getCurrentNoteNo() {
        return customerActivitySignupNoteMapper.getCurrentNoteNo();
    }

    @Override
    public String getCurrentThirdPartCode() {
        return customerActivitySignupNoteMapper.getCurrentThirdPartCode();
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(CustomerActivitySignupNote po) {
        checkValidationForSetting(po);
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (customerActivitySignupNoteMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("用户不允许重复报名同一活动");
                }
                po.setNoteNo(activityCacheUtil.getActionNoByKey(ACTION_NO_KEY));
                po.setThirdPartCode(activityCacheUtil.getThirdPartCode());
                result = super.insert(po);
                sendCodeToThirdPart(po.getThirdPartCode());
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".insert method occured exception", e);
                throw new ActivityException(e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    /**
     * Todo
     * 发送识别码到第三方
     * @param code
     */
    private void sendCodeToThirdPart(String code){

    }


    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int updateByPrimaryKey(CustomerActivitySignupNote po) {
        checkValidationForSetting(po);
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (customerActivitySignupNoteMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("用户不允许重复报名同一活动");
                }
                result = super.updateByPrimaryKey(po);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateByPrimaryKey method occured exception", e);
                throw new ActivityException(e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    /**
     * 根据报名设置检查用户输入有效性
     * @param po
     */
    private void checkValidationForSetting(CustomerActivitySignupNote po) {
        //从缓存获取报名规则
        Map<String, ActivitySignupNoteSetting> settingMap = activityCacheUtil.getVersionSettingMap();
        if(!settingMap.isEmpty()) {
            NOTE_FIELD_MAP.values().forEach(field -> {
                ActivitySignupNoteSetting setting = null;
                if((setting = settingMap.get(field.getName())) != null) {
                    //字段隐藏则无需判断
                    if (setting.getHideStatus()) {
                        //打开私有访问
                        field.setAccessible(true);
                        try {
                            Object value = field.get(po);
                            //为空校验
                            if (!setting.getAllowEmpty() && (value == null || StringUtils.isBlank(value + ""))) {
                                throw new ActivityException(String.format("[%s] 不能空", setting.getDisplayName()));
                            }
                            //长度校验
                            if (setting.getSizeLimit() > 0 && String.valueOf(value).length() > setting.getSizeLimit()) {
                                throw new ActivityException(String.format("[%s] 长度不能超过 %d", setting.getDisplayName(), setting.getSizeLimit()));
                            }
                            //正则表达式校验
                            if (!StringUtils.isBlank(setting.getvRegexp())) {
                                if (!RegexpUtils.getMatcher(setting.getvRegexp()).matches(String.valueOf(value))) {
                                    throw new ActivityException(String.format("[%s] 不匹配设置的格式 [%s]", setting.getDisplayName(), setting.getvRegexp()));
                                }
                            }
                        } catch (IllegalAccessException e) {
                            throw new ActivityException(e);
                        }
                    }
                }
            });
        }
    }

    @Override
//    @TxTransaction(isStart = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, String> insertSignupNoteAndOrder(ActivitySignupNoteDto dto) {
        Map<String, String> resMap = new HashMap<>();
        ActivityInfo activityInfo = activityInfoService.selectByPrimaryKey(dto.getActivityId());
        checkValidationForActivityInfo(activityInfo);
        CustomerActivitySignupNote note = dto.getCustomerActivitySignupNote();
        checkValidationForSetting(note);
        ActivityOrder activityOrder = dto.getActivityOrder();
        activityOrder.setOrderName(activityInfo.getTitle());
//        Optional.ofNullable(dto.getActivityOrder()).orElseThrow(() -> new ActivitiException("订单不能空"));
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = 0;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                //一期不限制
//                if (customerActivitySignupNoteMapper.checkMultiCountByUnique(dto.getCustomerActivitySignupNote()) > 0) {
//                    throw new ActivityException("用户不允许重复报名同一活动");
//                }

                note.setNoteNo(activityCacheUtil.getActionNoByKey(ACTION_NO_KEY));
                note.setThirdPartCode(activityCacheUtil.getThirdPartCode());
                dto.getCustomerActivitySignupNote().setFaceUrl(dto.getFaceUrl());
                //插入报名表
                result = super.insertSelective(dto.getCustomerActivitySignupNote());
                //插入订单
                activityOrderService.insert(dto.getActivityOrder());
                //更新人脸识别图片
//                Optional.ofNullable(dto.getFaceUrl()).ifPresent(faceUrl -> {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", dto.getUserId());
//                    map.put("faceUrl", faceUrl);
//                    operationClient.updateFaceUrl(map);
//                    LoginManager.getUser().setFaceUrl(dto.getFaceUrl());
//                });
                //发送验证码到第三方
                sendCodeToThirdPart(note.getThirdPartCode());
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateByPrimaryKey method occured exception", e);
                throw new ActivityException(e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }
//        resMap.put("result", result + "");
        resMap.put("id", note.getId());
        resMap.put("signCode", note.getThirdPartCode());
        resMap.put("name", note.getName());
        resMap.put("faceUrl", dto.getFaceUrl());
        resMap.put("orderId", dto.getActivityOrder().getId());
        resMap.put("userId", dto.getUserId());
        resMap.put("activityId", dto.getActivityId());

        return resMap;
    }

    /**
     * 校验活动有效性
     * @param activityInfo
     */
    private void checkValidationForActivityInfo(ActivityInfo activityInfo) {
        Optional.ofNullable(activityInfo).orElseThrow(() -> new ActivitiException("活动不存在"));
        if(activityInfo.getDisable()) {
            throw new ActivitiException("活动被锁定");
        }
        long lnow = System.currentTimeMillis();
        if(activityInfo.getSignEndTime().getTime() < lnow) {
            throw new ActivitiException("活动报名已结束");
        }
        if(activityInfo.getEndTime().getTime() < lnow) {
            throw new ActivitiException("活动已结束");
        }
    }
}