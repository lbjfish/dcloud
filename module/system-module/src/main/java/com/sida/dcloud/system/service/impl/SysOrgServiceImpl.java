package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.dao.SysAreaMapper;
import com.sida.dcloud.system.dao.SysEmployeeMapper;
import com.sida.dcloud.system.dao.SysOrgMapper;
import com.sida.dcloud.system.po.SysArea;
import com.sida.dcloud.system.service.*;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrg> implements SysOrgService {
    private static final Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysCacheVersionService sysCacheVersionService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;
    @Autowired
    private SysEmployeeOrgService sysEmployeeOrgService;


    @Override
    public IMybatisDao<SysOrg> getBaseDao() {
        return sysOrgMapper;
    }

    @Override
    public Object findTree() {
        SysOrg condition = new SysOrg();
        condition.setOrderField(SecConstant.SORT);
        condition.setOrderString(SecConstant.ASC);
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        List<SysOrg> list = sysOrgMapper.selectByCondition(condition);
        return BuildTree.buildTree(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        //1、删除数据
        sysOrgMapper.deleteEmployeePositionRela(id);
        sysOrgMapper.deleteById(id);
        //2、更新缓存
        redisUtil.removeOneFromMap(RedisKey.ORG_MAP, id);
        //3、更新缓存版本
        sysCacheVersionService.updateCacheVersion(RedisKey.ORG_MAP);

    }


    /**
     * 根据组织id获取对应的组织信息
     *
     * @param id
     * @return
     * @author xieguopei
     * @date 2017/09/05
     */
    @Override
    public Object selectOrgMesById(String id) {
        SysOrg sysOrg;

        sysOrg = sysOrgMapper.selectByPrimaryKey(id);

        return sysOrg;
    }

    @Override
    public List<SysOrg> findListByVo(SysOrgVo condition) {
        return sysOrgMapper.findListByVo(condition);
    }

    @Override
    public List<SysOrg> findOrgsByIds(SysOrgVo condition) {
        return sysOrgMapper.findOrgsByIds(condition);
    }

    @Override
    public List<SysOrg> findStoresByAreaId(String areaId) {
        return sysOrgMapper.findStoresByAreaId(areaId);
    }

    public Map<String, String> findAllForCache() {
        List<SysOrg> orgList = sysOrgMapper.findAllOrg();
        Map<String, String> map = new HashMap<>();
        if(BlankUtil.isNotEmpty(orgList)) {
            for (SysOrg org : orgList) {
                if(BlankUtil.isNotEmpty(org.getStoreCode())) {
                    map.put(org.getId(), org.getName()+"("+org.getStoreCode()+")");
                }else{
                    map.put(org.getId(), org.getName());
                }
            }
        }
        return map;
    }

    @Override
    public void loadOrgsToRedis() {
        Map<String, String> map = findAllForCache();
        redisUtil.remove(RedisKey.ORG_MAP);
        redisUtil.putInMap(RedisKey.ORG_MAP, map);
    }

    /**
     * 更新机构缓存和缓存版本
     * @param data
     */
    private void updateCache(SysOrg data) {
        //1、更新缓存
        redisUtil.putInMap(RedisKey.ORG_MAP, data.getId(), data.getName());
        //2、更新缓存版本
        sysCacheVersionService.updateCacheVersion(RedisKey.ORG_MAP);
    }

    /**
     * 批量更新机构缓存和缓存版本
     * @param data
     */
    public void batchUpdateCache(List<SysOrg> data) {
        if(BlankUtil.isNotEmpty(data)) {
            //1、更新缓存
            Map<String, String> orgMap = new HashMap<>();
            data.forEach(obj->{
                if(BlankUtil.isNotEmpty(obj.getStoreCode())) {
                    orgMap.put(obj.getId(), obj.getName() + "（" + obj.getStoreCode() + "）");
                }else{
                    orgMap.put(obj.getId(), obj.getName());
                }
            });
            redisUtil.putInMap(RedisKey.ORG_MAP, orgMap);
            //2、更新缓存版本
            sysCacheVersionService.updateCacheVersion(RedisKey.ORG_MAP);
        }
    }

    @Override
    @Transactional
    public void initArea() {
        //1.逻辑删除原来的片区数据
        sysAreaMapper.deleteAll();
        //2.获取已存在的片区对应的org数据
        List<SysOrg> list = sysOrgMapper.findModifyOrg(SysEnums.OrgTypeEnums.AREA.getType());
        //3.更新修改数据
        sysAreaMapper.updateAreaByModifyOrg(list);
        //4.插入新增数据
        sysAreaMapper.insertAreaByNewOrg(SysEnums.OrgTypeEnums.AREA.getType());

    }

    @Override
    @Transactional
    public void initStore() {
        //TODO:wuzhenwei 调用营销模块接口，逻辑删除所有门店信息
        //TODO:wuzhenwei 从营销模块获取门店表所有主键id集合
        List<String> ids = Lists.newArrayList();
        //1.获取已存在的门店对应的org数据
        if (BlankUtil.isNotEmpty(ids)){
            List<SysOrgVo> list = sysOrgMapper.findModifyStore(SysEnums.OrgTypeEnums.STORE.getType());
            //3.更新修改数据
            sysOrgMapper.updateStoreByModifyOrg(list);
        }
        //4.插入新增数据
        sysOrgMapper.insertStoreByNewOrg(SysEnums.OrgTypeEnums.STORE.getType(),SysEnums.OrgTypeEnums.AREA.getType());
    }

    @Override
    public List<SysOrg> findAreasByUserId(String userId) {
        SysOrg condition = new SysOrg();
        if(BlankUtil.isNotEmpty(userId)){
            UserInfo info = sysUserService.getUserInfo(userId,false);
            condition.setOrgId(info.getSchoolId());
        }else {
            condition.setOrgId(LoginManager.getCurrentOrgId());
        }
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        condition.setType(SysEnums.OrgTypeEnums.AREA.getType());
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.DESC);
        return sysOrgMapper.selectByCondition(condition);
    }

    @Override
    public List<SysOrg> findStoresByUserId(String userId) {
        String areaPath = this.findAreaPathByUserId(userId);
        List<SysOrg> list = sysOrgMapper.findStoresByAreaPath(areaPath,SysEnums.OrgTypeEnums.STORE.getType());
        for(SysOrg org:list){
            if(BlankUtil.isNotEmpty(org.getStoreCode())){
                org.setName(org.getName()+"("+org.getStoreCode()+")");
            }
        }
        return list;
    }

    public String findAreaPathByUserId(String userId) {
        //1.获取用户所在组织path
        String areaPath = "";
        String areaId;
        if(BlankUtil.isEmpty(userId)){
            areaId = LoginManager.getUser().getAreaId();
        }else {
            UserInfo userInfo = sysUserService.getUserInfo(userId, false);
            areaId = userInfo.getAreaId();
        }

        //2.根据id获取所在片区
        SysOrg org = selectByPrimaryKey(areaId);
        if (BlankUtil.isNotEmpty(org)){
            return org.getIdPath();
        }
        return areaPath;
    }

    @Override
    public List<SysOrg> findAreasAndStoresByUserId(String userId) {
        String orgId;
        if (BlankUtil.isNotEmpty(userId)){
            UserInfo info = sysUserService.getUserInfo(userId,false);
            orgId = info.getSchoolId();
        }else {
            orgId = LoginManager.getCurrentOrgId();
        }
        List<SysOrg> orgList = sysOrgMapper.selectAreasAndStores(orgId,SysEnums.OrgTypeEnums.AREA.getType(),SysEnums.OrgTypeEnums.STORE.getType());
        if(BlankUtil.isNotEmpty(orgList)){
            for(SysOrg org:orgList){
                if(BlankUtil.isNotEmpty(org.getStoreCode())){
                    org.setName(org.getName()+"("+org.getStoreCode()+")");
                }
            }
        }
        orgList = BuildTree.buildTree(orgList);
        return orgList;
    }

    @Override
    public List<SysOrgVo> findVoByCondition(SysOrgVo condition) {
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.DESC);
        return sysOrgMapper.findVoByCondition(condition);
    }

    @Override
    @Transactional
    public int saveOrUpdateOrg(SysOrg param) {
        int flag = 0;
        //1、更新或者新增机构
        if (StringUtils.isBlank(param.getId())){
            PoDefaultValueGenerator.putDefaultValue(param);
            //处理路径问题
            if (StringUtils.isNotBlank(param.getParentId())){
                SysOrg parentOrg = this.selectByPrimaryKey(param.getParentId());
                if (parentOrg!=null){
                    param.setIdPath(parentOrg.getIdPath());
                    param.setCodePath(parentOrg.getCodePath());
                    param.setNamePath(parentOrg.getNamePath());
                }
            }
            //拼接自身路径
            param.setIdPath((param.getIdPath()==null?"":param.getIdPath()) + param.getId() + ",");
            param.setCodePath((param.getCodePath()==null?"":param.getCodePath()) + param.getCode() + ",");
            param.setNamePath((param.getNamePath()==null?"":param.getNamePath()) + param.getName() + ",");
            flag = this.insertSelective(param);
        }else {
            PoDefaultValueGenerator.putDefaultValue(param);
            flag = this.updateByPrimaryKeySelective(param);
        }

        //2、更新缓存
        updateCache(param);
        return flag;
    }

    @Override
    public List<SysOrg> findStoresByAreaIds(List<String> areaIds) {
        List<SysOrg> list = sysOrgMapper.findStoresByAreaIds(areaIds,SysEnums.OrgTypeEnums.STORE.getType(),SysEnums.OrgTypeEnums.STORE.getType());
        if(BlankUtil.isNotEmpty(list)){
            for(SysOrg org:list){
                if(BlankUtil.isNotEmpty(org.getStoreCode())){
                    org.setName(org.getName()+"("+org.getStoreCode()+")");
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String,Object>> findAllStores() {
        List<Map<String,Object>> mapList = Lists.newArrayList();
        List<SysOrg> list = sysOrgMapper.findAllStores();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg org : list){
                Map<String,Object> map = new HashMap<>();
                map.put("storeId",org.getId());
                if(BlankUtil.isNotEmpty(org.getStoreCode())) {
                    map.put("storeName", org.getName()+"("+org.getStoreCode()+")");
                }else{
                    map.put("storeName", org.getName());
                }
                map.put("storeCode",org.getStoreCode());
                map.put("areaId",org.getParentId());
                map.put("orgId",org.getOrgId());
                map.put("orgPath",org.getIdPath());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> findAllAreas() {
        List<Map<String,Object>> mapList = Lists.newArrayList();
        SysOrg condition = new SysOrg();
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        condition.setType(SysEnums.OrgTypeEnums.AREA.getType());
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrgId(LoginManager.getCurrentOrgId());
        condition.setOrderString(SecConstant.DESC);
        List<SysOrg> list = sysOrgMapper.selectByCondition(condition);
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg org : list){
                Map<String,Object> map = new HashMap<>();
                map.put("areaId",org.getId());
                map.put("areaName",org.getName());
                map.put("orgId",org.getOrgId());
                map.put("orgPath",org.getIdPath());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public Object findTreeFrom(String orgId) {
        List<SysOrg> list = sysOrgMapper.findTreeFrom(orgId);
        return BuildTree.buildTree(list);
    }

    @Override
    public List<EmpDTO> orgEmps(String orgId) {
        return sysOrgMapper.findOrgEmps(orgId);
    }

    @Override
    public List<Map<String,Object>> findUserOrgsList(SysBaseDTO param) {
        List<Map<String,Object>> list = Lists.newArrayList();
        //片区
        Map<String,Object> areaMapOptions = new HashMap<>();
        List<Map<String,Object>> areaMapList = Lists.newArrayList();
        //其他
        Map<String,Object> orgMapOptions = new HashMap<>();
        List<Map<String,Object>> orgMapList = Lists.newArrayList();
        //判断员工是否具备片区id
        SysUserVo userVo = LoginManager.getUser();
        String areaId = userVo.getAreaId();
        String orgId = userVo.getOrgId();
        if ((BlankUtil.isEmpty(param) || BlankUtil.isEmpty(param.getFilterFlag()) || param.getFilterFlag()) && BlankUtil.isNotEmpty(areaId)){
            Map<String,Object> areaMap = new HashMap<>();
            areaMap.put("value",areaId);
            SysOrg area = sysOrgMapper.selectByPrimaryKey(areaId);
            if (BlankUtil.isNotEmpty(area)){
                areaMap.put("label",area.getName());
            }
            areaMapList.add(areaMap);
        }else {
            List<SysOrg> orgList = sysOrgMapper.findTreeFrom(orgId);
            if (BlankUtil.isNotEmpty(orgList)){
                for (SysOrg org : orgList){
                    //过滤门店
                    if (BlankUtil.isNotEmpty(org.getType()) && SysEnums.OrgTypeEnums.STORE.getType().equals(org.getType())){
                        continue;
                    }
                    //片区
                    if (BlankUtil.isNotEmpty(org.getType()) && SysEnums.OrgTypeEnums.AREA.getType().equals(org.getType())){
                        Map<String,Object> areaMap = new HashMap<>();
                        areaMap.put("value",org.getId());
                        areaMap.put("label",org.getName());
                        areaMapList.add(areaMap);
                    }
                    else {
                        Map<String,Object> orgMap = new HashMap<>();
                        orgMap.put("value",org.getId());
                        orgMap.put("label",org.getName());
                        orgMapList.add(orgMap);
                    }
                }
            }
        }
        areaMapOptions.put("label","片区");
        areaMapOptions.put("options",areaMapList);
        orgMapOptions.put("label","其他");
        orgMapOptions.put("options",orgMapList);
        list.add(areaMapOptions);
        list.add(orgMapOptions);
        return list;
    }

    @Override
    public List<Map<String,Object>> findUserOrgsWithInsert() {
        List<Map<String,Object>> list = Lists.newArrayList();
        //片区
        Map<String,Object> areaMapOptions = new HashMap<>();
        List<Map<String,Object>> areaMapList = Lists.newArrayList();
        //其他
        Map<String,Object> orgMapOptions = new HashMap<>();
        List<Map<String,Object>> orgMapList = Lists.newArrayList();

        List<SysOrg> orgList = sysOrgMapper.findTreeFrom(LoginManager.getCurrentOrgId());
        if (BlankUtil.isNotEmpty(orgList)){
            for (SysOrg org : orgList){
                //过滤门店
                if (BlankUtil.isNotEmpty(org.getType()) && SysEnums.OrgTypeEnums.STORE.getType().equals(org.getType())){
                    continue;
                }
                //片区
                if (BlankUtil.isNotEmpty(org.getType()) && SysEnums.OrgTypeEnums.AREA.getType().equals(org.getType())){
                    Map<String,Object> areaMap = new HashMap<>();
                    areaMap.put("value",org.getId());
                    areaMap.put("label",org.getName());
                    areaMapList.add(areaMap);
                }
                else {
                    Map<String,Object> orgMap = new HashMap<>();
                    orgMap.put("value",org.getId());
                    orgMap.put("label",org.getName());
                    orgMapList.add(orgMap);
                }
            }
        }
        areaMapOptions.put("label","片区");
        areaMapOptions.put("options",areaMapList);
        orgMapOptions.put("label","其他");
        orgMapOptions.put("options",orgMapList);
        list.add(areaMapOptions);
        list.add(orgMapOptions);
        return list;
    }

    @Override
    @Transactional
    public void firstInitOrg() {
        initArea();
        initStore();
    }

    @Override
    @Transactional
    public InitStoreDTO incrSyncInitOrg(List<SysOrg> orgList) {
        InitStoreDTO param = new InitStoreDTO();

        List<String> ids = Lists.newArrayList();
        Map<String,SysOrg> areaTypeIdsMap = new HashMap<>();
        Map<String,SysOrg> storeTypeIdsMap = new HashMap<>();
        //1.获取本次增量同步org主键id集合
        if (BlankUtil.isNotEmpty(orgList)){
            for (SysOrg org : orgList){
                ids.add(org.getId());
                if (BlankUtil.isNotEmpty(org.getType()) && org.getType().equals(SysEnums.OrgTypeEnums.AREA.getType())){
                    areaTypeIdsMap.put(org.getId(),org);
                }
                if (BlankUtil.isNotEmpty(org.getType()) && org.getType().equals(SysEnums.OrgTypeEnums.STORE.getType())){
                    storeTypeIdsMap.put(org.getId(),org);
                }
            }
        }
        //2.共有两种情况：2.1-原存在现更新；2.2-原不存在现新增
        Map<String,SysArea> areaMap = sysAreaService.findMapByIdIn(ids);
        List<SysOrg> updateOrgs = Lists.newArrayList();
        List<String> deleteIds = Lists.newArrayList();
        List<SysOrg> insertOrgs = Lists.newArrayList();
        if (areaMap != null){
            for (SysOrg org : orgList){
                if (areaMap.containsKey(org.getId())){
                    //2.1.1：原来是片区，更新后还是片区
                    if (BlankUtil.isNotEmpty(org.getType()) && org.getType().equals(SysEnums.OrgTypeEnums.AREA.getType())){
                        updateOrgs.add(org);
                    }
                    //2.1.2：原来是片区，更新后已不再是片区
                    else{
                        deleteIds.add(org.getId());
                    }
                }
                //2.2 原不存在，现新增
                if (!areaMap.containsKey(org.getId()) && areaTypeIdsMap.containsKey(org.getId())){
                    insertOrgs.add(org);
                }
            }
        }
        //3.处理原存在数据
        sysAreaService.updateWithOrgs(updateOrgs);
        sysAreaService.deleteByIds(deleteIds);
        //4.新增新数据
        sysAreaService.insertWithOrgs(insertOrgs);

        /********************** 触发刷新门店 **************************/
        List<SysOrgVo> orgVoList = sysOrgMapper.findVoByIds(ids);
        param.setOrgList(orgVoList);
        param.setIds(ids);
        param.setStoreMap(storeTypeIdsMap);
        return param;
    }

    @Override
    public List<SysOrgVo> findAllOrgsByType(Integer type) {
        return sysOrgMapper.findAllOrgsByType(type);
    }

    @Override
    public void updateStoreBusinessStatus() {
        sysOrgMapper.updateStoreBusinessStatus();
    }

    @Override
    public void updateSchoolFlag() {
        sysOrgMapper.updateSchoolFlag();
    }

    @Override
    public void updateSchoolFlag(List<String> ids) {
        sysOrgMapper.updateSchoolFlagWithIds(ids);
    }

    @Override
    public List<SysOrg> findOrgsBySchoolFlag() {
        return sysOrgMapper.findOrgsBySchoolFlag();
    }

    @Override
    public Map<String, SysOrg> findMapByIds(List<String> ids) {
        Map<String, SysOrg> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(ids)){
            List<SysOrg> poList = sysOrgMapper.findByIds(ids);
            if (BlankUtil.isNotEmpty(poList)){
                for (SysOrg po : poList){
                    map.put(po.getId(),po);
                }
            }
        }
        return map;
    }


    @Override
    public Map<String, Object> findLeaderFirstByEmployeeId(String employeeId) {
        Map<String,Object> map = new HashMap<>();
        map.put("data",null);
        //根据员工id，获取组织相关信息
        EmpOrgDTO empOrgDTO = findEmpOrgInfoByEmpId(employeeId);
        //根据组织获取部门信息
        if (BlankUtil.isNotEmpty(empOrgDTO) && BlankUtil.isNotEmpty(empOrgDTO.getLeaderOrg())){
            List<EmpDTO> list = sysOrgMapper.findOrgEmps(empOrgDTO.getLeaderOrg());
            map.put("data",list);
            map.put("default",empOrgDTO.getLeaderPerson());
        }
        return map;
    }

    @Override
    public EmpOrgDTO findEmpOrgInfoByEmpId(String employeeId) {
        //根据关联关系 获取所属组织
        EmpOrgDTO empOrgDTO = new EmpOrgDTO();
        SysOrg sysOrg = sysOrgMapper.findOrgByEmpId(employeeId);
        if (BlankUtil.isNotEmpty(sysOrg) && BlankUtil.isNotEmpty(sysOrg.getIdPath())){
            empOrgDTO.setSchoolId(sysOrg.getOrgId());

            //如果是片区人员，则片区为所属组织
            if (BlankUtil.isNotEmpty(sysOrg.getType()) && sysOrg.getType().equals(SysEnums.OrgTypeEnums.AREA.getType())){
                empOrgDTO.setAreaId(sysOrg.getId());
                empOrgDTO.setLeaderOrg(sysOrg.getId());
            }else {
                //否则根据idPath 获取 父级组织
                String[] orgs = sysOrg.getIdPath().split(",");
                List<String> orgStrs = Arrays.asList(orgs);
                List<SysOrg> list = sysOrgMapper.findByIds(orgStrs);
                if (BlankUtil.isNotEmpty(list)){
                    String areaPath = "";
                    String areaId = "";
                    for (SysOrg org : list){
                        if (SysEnums.OrgTypeEnums.AREA.getType().equals(org.getType())){
                            if (org.getIdPath().length() > areaPath.length()){
                                areaId = org.getId();
                                areaPath = org.getIdPath();
                            }
                        }
                    }
                    //父级组织存在片区，则该片区为所属组织
                    if (BlankUtil.isNotEmpty(areaId)){
                        empOrgDTO.setAreaId(areaId);
                        empOrgDTO.setLeaderOrg(areaId);
                    }else {
                        //否则当前组织为所属组织
                        empOrgDTO.setLeaderOrg(sysOrg.getId());
                    }
                }
            }
        }

        //置入负责人
        if (BlankUtil.isNotEmpty(empOrgDTO.getLeaderOrg())){
            //如果责任部门是片区
            if (BlankUtil.isNotEmpty(empOrgDTO.getAreaId()) && empOrgDTO.getAreaId().equals(empOrgDTO.getLeaderOrg())){
                for (int i = 0; i < 3; i++){
                    EmployeeConditionDTO dto = new EmployeeConditionDTO();
                    String positionCode = "";
                    if (i == 0){
                        positionCode = SysEnums.LeaderRoleEnums.AREA.getFirstCode();
                    }else if (i == 1){
                        positionCode = SysEnums.LeaderRoleEnums.AREA.getSecondCode();
                    }else{
                        positionCode = SysEnums.LeaderRoleEnums.AREA.getThirdCode();
                    }
                    if (BlankUtil.isNotEmpty(positionCode)){
                        dto.setPositionCode(positionCode);
                        dto.setOrganizationId(empOrgDTO.getLeaderOrg());
                        List<SysEmployee> list = sysEmployeeMapper.findEmpsByPositionAndOrgId(dto);
                        if (list != null && list.size() >0){
                            empOrgDTO.setLeaderPerson(list.get(0).getId());
                            break;
                        }
                    }
                }
            }else {
                //如果责任部门为非片区
                for (int i = 0; i < 3; i++){
                    EmployeeConditionDTO dto = new EmployeeConditionDTO();
                    List<String> positionCodeGroup = Lists.newArrayList();
                    if (i == 0){
                        positionCodeGroup = SysEnums.LeaderRoleEnums.getFirstCodes();
                    }else if (i == 1){
                        positionCodeGroup = SysEnums.LeaderRoleEnums.getSecondCodes();
                    }else{
                        positionCodeGroup = SysEnums.LeaderRoleEnums.getThirdCodes();
                    }
                    if (BlankUtil.isNotEmpty(positionCodeGroup)){
                        dto.setPositionCodeGroup(positionCodeGroup);
                        dto.setOrganizationId(empOrgDTO.getLeaderOrg());
                        List<SysEmployee> list = sysEmployeeMapper.findEmpsByPositionAndOrgId(dto);
                        if (list != null && list.size() >0){
                            empOrgDTO.setLeaderPerson(list.get(0).getId());
                            break;
                        }
                    }
                }
            }
        }
        return empOrgDTO;
    }

    @Override
    public Object findTreeFromWithoutStore(String orgId) {
        List<SysOrg> list = sysOrgMapper.findTreeFrom(orgId);
        List<SysOrg> orgList = Lists.newArrayList();
        for (SysOrg org : list){
            if (BlankUtil.isEmpty(org.getType()) || !org.getType().equals(SysEnums.OrgTypeEnums.STORE.getType())){
                orgList.add(org);
            }
        }
        return BuildTree.buildTree(orgList);
    }

    @Override
    public List<EmpDTO> orgEmpsForCar(String orgId, String plateColor) {
        List<EmpDTO> list = Lists.newArrayList();
        SysOrg example = new SysOrg();
        if (BlankUtil.isNotEmpty(orgId) && BlankUtil.isNotEmpty(plateColor)){
            //黄色车牌，优先显示教练
            if (plateColor.equals("0")){
                example.setOrderField(" order by FIELD(`emp_type`,'1') desc ");
            }else {
                example.setOrderField(" order by FIELD(`emp_type`,'1') asc ");
            }

            //如果是片区，同时包含下属门店
            SysOrg org = sysOrgMapper.selectByPrimaryKey(orgId);
            if (BlankUtil.isNotEmpty(org)){
                if (BlankUtil.isNotEmpty(org.getType()) && org.getType().equals(SysEnums.OrgTypeEnums.AREA.getType())){
                    example.setIdPath(org.getIdPath());
                }else {
                    example.setId(org.getId());
                }
                list = sysOrgMapper.orgEmpsForCar(example);
            }
        }
        return list;
    }

    /**
     * 根据第三方ids获取组织
     * @param thirdPartyIds
     * @return
     */
    public List<SysOrg> findSysOrgByThirdPartyIds(List<String> thirdPartyIds){
        return this.sysOrgMapper.findSysOrgByThirdPartyIds(thirdPartyIds);
    }

    @Override
    public String findOrgIdByName(String name) {
        String id = "";
        if (BlankUtil.isNotEmpty(name)){
            SysOrg condition = new SysOrg();
            condition.setName(name);
            condition.setDeleteFlag(false);
            List<SysOrg> list = sysOrgMapper.selectByCondition(condition);
            if (BlankUtil.isNotEmpty(list) && list.size() == 1){
                id = list.get(0).getId();
            }
        }
        return id;
    }

    @Override
    public List<SysOrg> findDeptsByOrgId(String orgId) {
        return sysOrgMapper.findDeptsByOrgId(orgId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = false)
    public void updateStoreByMarketing(SysOrg sysOrg) {
        SysOrg org = sysOrgMapper.selectByPrimaryKey(sysOrg.getId());
        org.setBusinessStatus(sysOrg.getBusinessStatus());
        sysOrgMapper.updateByPrimaryKeySelective(org);
        updateCache(org);
    }
}