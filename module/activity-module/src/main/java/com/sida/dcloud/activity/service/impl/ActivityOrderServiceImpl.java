package com.sida.dcloud.activity.service.impl;

//import com.codingapi.tx.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.util.concurrent.AtomicDouble;
import com.sida.dcloud.activity.common.ActivityConstants;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityOrderMapper;
import com.sida.dcloud.activity.dto.IdAndCountDto;
import com.sida.dcloud.activity.po.*;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.service.*;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ActivityOrderServiceImpl extends BaseServiceImpl<ActivityOrder> implements ActivityOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityOrderServiceImpl.class);
    public static final String ACTION_NO_KEY = "ORDER";
//    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + ActivityOrderServiceImpl.class.getName();
    private static final String LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS = "LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS_" + ActivityOrderServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private ActivityCacheUtil activityCacheUtil;
    @Autowired
    private ActivityOrderMapper activityOrderMapper;
    @Autowired
    private ActivityGoodsService activityGoodsService;
    @Autowired
    private ActivityGoodsGroupService activityGoodsGroupService;
    @Autowired
    private ActivityOrderGoodsService activityOrderGoodsService;
    @Autowired
    private ActivityOrderGoodsGroupService activityOrderGoodsGroupService;

    @Override
    public IMybatisDao<ActivityOrder> getBaseDao() {
        return activityOrderMapper;
    }

    @Override
    public Page<ActivityOrderVo> findPageList(ActivityOrder po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ActivityOrderVo> voList = activityOrderMapper.findVoList(po);
        return (Page) voList;
    }

    /**
     * 活动状态更新需加锁
     * @param activityId
     * @param activityOrderStatus
     * @return
     */
    @Override
    public int updateActivityOrderStatus(String activityId, String activityOrderStatus) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            try {
                result = activityOrderMapper.updateActivityOrderStatus(activityId, activityOrderStatus);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateActivityOrderStatus method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    public List<ActivityOrderGoods> findGoodsListByOrderId(String orderId) {
        return activityOrderGoodsService.findListByOrderId(orderId);
    }

    @Override
    public List<ActivityOrderGoodsGroup> findGroupListByOrderId(String orderId) {
        return activityOrderGoodsGroupService.findListByOrderId(orderId);
    }

    @Override
    public String getCurrentOrderNo() {
        return activityOrderMapper.getCurrentOrderNo();
    }

//    @TxTransaction
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insert(ActivityOrder po) {
        po.setOrderNo(activityCacheUtil.getActionNoByKey(ACTION_NO_KEY));
        checkValidationForOrder(po);
        batchAction(po);
        return activityOrderMapper.insertSelective(po);
    }

    /**
     * 校验订单
     * @param po
     */
    private void checkValidationForOrder(ActivityOrder po) {
        Optional.ofNullable(po).orElseThrow(() -> new ActivitiException("订单不能为空"));
        Optional.ofNullable(po.getNoteId()).orElseThrow(() -> new ActivitiException("报名表不能为空"));
        if(po.getGoodsList().isEmpty() && po.getGroupList().isEmpty()) {
            throw new ActivityException("必须选择至少一个活动商品/商品组下订单");
        }
        //订单状态
        po.setActivityOrderStatus(ActivityConstants.ORDER_STATUS.NOT_PAY.getCode());
        //订单名称 = 活动名称_订单编号
        po.setOrderName(String.format("%s_%s", po.getOrderName(), po.getOrderName()));
        //订单创建时间
        po.setCreateTime(new Date());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateByPrimaryKey(ActivityOrder po) {
        batchAction(po);
        return super.updateByPrimaryKey(po);
    }

    /**
     * 从前端传的参数只有id，数量，排序值
     * 商品销售价，折扣信息从数据库取原始商品和商品组合信息
     * 最终得到总金额和总减免金额
     * @param po 订单
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void batchAction(ActivityOrder po) {
        Map<String, IdAndCountDto> map = new HashMap<>();
        AtomicDouble totalAmount = new AtomicDouble(0);
        AtomicDouble minusAmount = new AtomicDouble(0);
        Optional.ofNullable(po.getGoodsList()).ifPresent(list -> {
            if (!list.isEmpty()) {
                StringBuilder builder = new StringBuilder("0");
                list.stream().map(o -> {
                    map.put(o.getId(), o);
                    return String.format(",%s", o.getId());
                }).forEach(builder::append);
                List<ActivityGoods> originalGoodsList = activityGoodsService.findListByIds(builder.toString());
                List<ActivityOrderGoods> goodsList = new ArrayList<>();
                originalGoodsList.forEach(goods -> {
                    Optional.ofNullable(map.get(goods.getId())).ifPresent(idAndCount -> {
                        ActivityOrderGoods o = new ActivityOrderGoods();
                        o.setId(UUIDGenerate.getNextId());
                        o.setOrderId(po.getId());
                        o.setGoodsId(idAndCount.getId());
                        o.setPayCount(idAndCount.getCount());
                        o.setSort(idAndCount.getSort());
                        o.setPayPrice(goods.getPayPrice());
                        totalAmount.addAndGet(o.getPayPrice());
                        goodsList.add(o);
                    });
                });
                activityOrderGoodsService.batchInsert(goodsList);
                map.clear();
            }
        });
        Optional.ofNullable(po.getGroupList()).ifPresent(list -> {
            if (!list.isEmpty()) {
                StringBuilder builder = new StringBuilder("0");
                list.stream().map(o -> {
                    map.put(o.getId(), o);
                    return String.format(",%s", o.getId());
                }).forEach(builder::append);
                List<ActivityGoodsGroup> originalGroupList = activityGoodsGroupService.findListByIds(builder.toString());
                List<ActivityOrderGoodsGroup> groupList = new ArrayList<>();
                originalGroupList.forEach(group -> {
                    Optional.ofNullable(map.get(group.getId())).ifPresent(idAndCount -> {
                        ActivityOrderGoodsGroup o = new ActivityOrderGoodsGroup();
                        o.setId(UUIDGenerate.getNextId());
                        o.setOrderId(po.getId());
                        o.setGroupId(idAndCount.getId());
                        o.setPayCount(idAndCount.getCount());
                        o.setSort(idAndCount.getSort());
                        o.setPayPrice(group.getPayPrice());
                        totalAmount.addAndGet(o.getPayPrice());
                        minusAmount.addAndGet(group.getMinusAmount());
                        groupList.add(o);
                    });
                });
                activityOrderGoodsGroupService.batchInsert(groupList);
                map.clear();
            }
        });
        po.setGoodsAmount(totalAmount.get());
        po.setMinusAmount(minusAmount.get());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            try {
                if (activityOrderMapper.checkCountForActivityOrderStatus(ids, ActivityConstants.REMOVABLE_ACTIVITY_ORDER_STATUS_FLAGS) > 0) {
                    throw new ActivityException("此状态订单不允许删除");
                }
                result = activityOrderMapper.deleteByPrimaryKeys(ids);
                activityOrderGoodsService.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }
}