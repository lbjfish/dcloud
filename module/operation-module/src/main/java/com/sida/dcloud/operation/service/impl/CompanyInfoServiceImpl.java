package com.sida.dcloud.operation.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dao.CompanyInfoMapper;
import com.sida.dcloud.operation.po.CompanyInfo;
import com.sida.dcloud.operation.service.CompanyInfoService;
import com.sida.dcloud.operation.vo.CompanyInfoVo;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.xiruo.po.common.IdAndNameDto;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyInfoServiceImpl extends BaseServiceImpl<CompanyInfo> implements CompanyInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyInfoServiceImpl.class);

    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + CompanyInfoServiceImpl.class.getName();
    private static final String LOCK_KEY_CHECK_ACTIVITY_STATUS = "LOCK_KEY_CHECK_ACTIVITY_STATUS_" + CompanyInfoServiceImpl.class.getName();

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public IMybatisDao<CompanyInfo> getBaseDao() {
        return companyInfoMapper;
    }

    @Override
    public Page<CompanyInfoVo> findPageList(CompanyInfo po) {
//        LOG.info("每页 {} 条", po.getS());
        PageHelper.startPage(po.getP(),po.getS());
        Map<String, Object> map = (Map<String, Object>)redisUtil.getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        List<CompanyInfoVo> voList = companyInfoMapper.findVoList(po);
        voList.forEach(vo -> vo.setRegionName(((SysRegionLayerDto)map.get(vo.getRegionId())).getName()));
        return (Page) voList;
    }

    @Override
    public Map<String, IdAndNameDto> selectNamesByIds(String ids) {
        return companyInfoMapper.selectNamesByIds(ids);
    }

    @Override
    public List<CompanyInfoVo> findAllInList(CompanyInfo po) {
        Map<String, Object> map = (Map<String, Object>)redisUtil.getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        List<CompanyInfoVo> voList = companyInfoMapper.findVoList(po);
        voList.forEach(vo -> vo.setRegionName(((SysRegionLayerDto)map.get(vo.getRegionId())).getName()));
        return voList;
    }

    @Override
    public Map<String, CompanyInfoVo> findAllInMap(CompanyInfo po) {
        Map<String, Object> map = (Map<String, Object>)redisUtil.getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        Map<String, CompanyInfoVo> voMap = companyInfoMapper.findVoMap(po);
//        voMap.values().forEach(vo -> vo.setRegionName(((SysRegionLayerDto)map.get(vo.getRegionId())).getName()));
        voMap.values().forEach(vo -> {
//            LOG.info("{}={}", vo.getRegionId(),map.get(vo.getRegionId()));
            Optional.ofNullable(map.get(vo.getRegionId())).ifPresent(region ->
            vo.setRegionName(((SysRegionLayerDto)region).getName()));
        });
        return voMap;
    }

    @Override
    public void increaseFieldCount(String fieldName, String companyId, int count) {
        companyInfoMapper.increaseFieldCount(fieldName, companyId, count);
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(CompanyInfo po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (companyInfoMapper.checkMultiCountByUnique(po) > 0) {
                    throw new OperationException("企业名称已经存在，不能重复");
                }
                result = super.insert(po);
            } catch(Exception e) {
                LOG.error("Insert method occured exception", e);
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
    public int updateByPrimaryKey(CompanyInfo po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (companyInfoMapper.checkMultiCountByUnique(po) > 0) {
                    throw new OperationException("企业名称已经存在，不能重复");
                }
                result = super.updateByPrimaryKey(po);
            } catch(Exception e) {
                LOG.error("UpdateByPrimaryKey method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            try {
                result = companyInfoMapper.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    @Cacheable(cacheNames = "CompanyInfo", key = "'id:' + #id")
    public CompanyInfo selectByPrimaryKey(Object id) {
        return super.selectByPrimaryKey(id);
    }

    @CacheEvict(cacheNames="CompanyInfo", key="'id:' + #id", condition="#id != ''")
    @Override
    public int deleteByPrimaryKey(Object id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    @CachePut(cacheNames="CompanyInfo", key="'id:' + #po.id", condition="#po.id != ''")
    public int updateByPrimaryKeySelective(CompanyInfo po) {
        return super.updateByPrimaryKeySelective(po);
    }
}