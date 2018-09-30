package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.CustomerActivitySignupNoteMapper;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.StringUtil;
import net.sf.json.regexp.RegexpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerActivitySignupNoteServiceImpl extends BaseServiceImpl<CustomerActivitySignupNote> implements CustomerActivitySignupNoteService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerActivitySignupNoteServiceImpl.class);
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + CustomerActivitySignupNoteServiceImpl.class.getName();
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
    private CustomerActivitySignupNoteMapper customerActivitySignupNoteMapper;
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
    public Page<CustomerActivitySignupNoteVo> findPageList(CustomerActivitySignupNote po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<CustomerActivitySignupNoteVo> voList = customerActivitySignupNoteMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public ActivitySignupNoteDto findOneToClient(String id) {
        ActivitySignupNoteDto dto = new ActivitySignupNoteDto();
        Optional.ofNullable(customerActivitySignupNoteMapper.selectByPrimaryKey(id)).ifPresent(note -> {
            String version = note.getVersion();
            dto.setSettingList(activitySignupNoteSettingService.selectByVersionToClient(version));
            Map<String, Object> valueMap = dto.getValueMap();
            {
                //此实现无法排序
                //将settingList里的所有字段名串起来
//            String codes = String.format(",%s,", dto.getSettingList().stream().map(setting -> setting.getCode()).reduce((code1, code2) -> String.format("%s,%s", code1, code2)).get());
                //只有被设置的字段需要取值并返回，因为字段未知因此采用map存储返回
//            Arrays.stream(NOTE_FIELD_ARRAY).filter(field -> codes.indexOf(String.format(",%s,", field.getName())) > 0)
//                    .forEach(field -> {
//                        //打开私有访问
//                        field.setAccessible(true);
//                        try {
//                            Object value = field.get(note);
//                            valueMap.put(field.getName(), value);
//                        } catch (IllegalAccessException e) {
//                            throw new ActivityException(e);
//                        }
//                    });
            }
            {
                dto.getSettingList().forEach(setting -> {
                    Optional.ofNullable(NOTE_FIELD_MAP.get(setting.getCode())).ifPresent(field -> {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(note);
                            valueMap.put(field.getName(), value);
                        } catch (IllegalAccessException e) {
                            throw new ActivityException(e);
                        }
                    });
                });
            }
        });
        return dto;
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
                result = super.insert(po);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".insert method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
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
}