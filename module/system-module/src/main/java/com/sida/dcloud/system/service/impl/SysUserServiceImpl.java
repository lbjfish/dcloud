package com.sida.dcloud.system.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.dao.*;
import com.sida.dcloud.system.service.AuthcodeService;
import com.sida.dcloud.system.service.FileService;
import com.sida.dcloud.system.service.SysRoleService;
import com.sida.dcloud.system.service.SysUserService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.AuthCodeConstants;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthcodeService authcodeService;

    @Override
    public IMybatisDao<SysUser> getBaseDao() {
        return sysUserMapper;
    }

    @Override
    public SysUser selectUserByName(String name) {
        return sysUserMapper.selectUserByName(name);
    }

    @Override
    public Page<SysUserVo> findPageList(SysUser param) {
        SysUser condition = new SysUser();
        condition.setAccount(param.getAccount());
        condition.setName(param.getName());
        condition.setStatus(param.getStatus());
        PageHelper.startPage(param.getP(),param.getS());
        BaseEntityUtil.putSort(condition,param);
        List<SysUserVo> list = sysUserMapper.selectVoList(condition);
        return (Page) list;

    }

    @Override
    @Transactional(readOnly = false)
    public int saveOrUpdateUser(SysUserVo param) {
        if (super.saveOrUpdate(param) > 0){
            //删除用户原有角色
            sysUserMapper.deleteByUserId(param.getId());
            return sysUserMapper.addUserRoleRela(param.getId(),param.getRoleId());
        }
        return -1;
    }

    @Override
    public List<SysUserVo> findUsersByCondition(UserConditionDTO param) {
        //1.角色编码  来源：前端传参。必填
        //2.所在部门id  来源：前端传参
        //2.1 若前端不传 则由发起人id获取发起人部门为其所在部门id，<注：若发起人不传，则直接按角色编码获取人员>
        //2.2 若由角色和部门id无法获取至少1个对应用户，则获取上级部门id，再次查询，<注：若直至最上一层部门仍无法获取人员，则直接按角色编码获取人员>
        if (BlankUtil.isEmpty(param.getRoleCode())){
            return null;
        }
        String schoolId = "";//驾校id
        //获取部门
        if (BlankUtil.isEmpty(param.getOrganizationId()) && BlankUtil.isNotEmpty(param.getUserId())){
            UserInfo info = getUserInfo(param.getUserId(),false);
            if (BlankUtil.isNotEmpty(info)){
                if(BlankUtil.isNotEmpty(info.getOrgId())){
                    param.setOrganizationId(info.getOrgId());
                }
                schoolId = info.getSchoolId();
            }
        }
        if (BlankUtil.isNotEmpty(param.getOrganizationId())){
            SysOrg org = sysOrgMapper.selectByPrimaryKey(param.getOrganizationId());
            if (BlankUtil.isNotEmpty(org) && BlankUtil.isNotEmpty(org.getIdPath())){
                String[] orgStrs = org.getIdPath().split(",");
                for (int i = orgStrs.length-1;i>=0;i--){
                    //由下往上遍历组织架构。获取该角色的用户
                    List<SysUserVo> list = sysUserMapper.findUsersByCondition(param.getRoleCode(),orgStrs[i]);
                    if (BlankUtil.isNotEmpty(list)){
                        return list;
                    }
                }
            }
        }

        return sysUserMapper.findUsersByRoleCodeAndSchoolId(param.getRoleCode(),schoolId);
    }

    @Override
    @Transactional
    public int insertUserWithEmployeeAndRoleCode(SysEmployee sysEmployee, String roleCode) {
        int insertEmp = insertUserWithEmployee(sysEmployee);
        int insertRole = 0;
        if (BlankUtil.isNotEmpty(roleCode)){
            SysRole role = sysRoleService.selectByCode(roleCode);
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysEmployee.getId());
            userRole.setRoleId(role.getId());
            insertRole = sysUserRoleMapper.insertSelective(userRole);
        }
        return insertEmp + insertRole;
    }

    @Override
    public List<SysUser> findUsersByNames(String orgId, List<String> nameList) {
        return sysUserMapper.findUsersByNames(orgId, nameList);
    }

    @Override
    @Transactional
    public int insertUserWithEmployee(SysEmployee sysEmployee) {
        SysUser user = transEmployee(sysEmployee);
        return sysUserMapper.insertSelective(user);
    }

    private SysUser transEmployee(SysEmployee employee) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(employee,user);
        //TODO:登录账号待确定，另员工离职是否要创建用户账号
        if (StringUtils.isBlank(employee.getMobile())){
            throw new ServiceException(ErrorCodeEnums.CUSTOM.getCode(),"手机号码不能为空!");
        }
        user.setAccount(employee.getMobile());
        user.setMobile(employee.getMobile());
        user.setPassword(MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD));
        user.setCode(employee.getWorkCard());
        user.setValidDate(new Date());
        user.setLocked(false);
        user.setStatus(false);
        //若是离职员工，默认停用账户
        if (employee.getStatus() != null && SecConstant.EMP_STATUS_2.equals(employee.getStatus())){
            user.setStatus(true);
        }
        return user;
    }

    @Override
    public UserInfo getUserInfo(){
        return getUserInfo(null,true);
    }

    @Override
    public UserInfo getUserInfo(String userId, Boolean resFlag) {
        if (BlankUtil.isEmpty(userId)){
            userId = LoginManager.getCurrentUserId();
        }
        //获取基本信息
        UserInfo info = sysUserMapper.getUserInfo(userId);

        if (BlankUtil.isEmpty(info)){
            throw new ServiceException(ErrorCodeEnums.SELECT,"无法根据userId【"+userId+"】获取到用户信息！");
        }

        List<RoleDTO> roleList = sysRoleService.findRoleListByUserId(userId);
        info.setRoleList(roleList);

        addOtherOrgInfoByOrgPath(info,info.getOrgPath());

        if (resFlag){
            //获取页面资源信息
            Map<String,List<SysResourceVo>> allRes = this.findAllResMap();

            //获取资源信息
            List<SysResourceVo> resources = sysResourceMapper.findUserResources(userId);
            List<SysResourceVo> resourcesTree = BuildTree.buildTree(resources);

            LinkedHashMap<String,Object> userResMap = new LinkedHashMap<>();
            buildUserResMap(userResMap,resourcesTree,null,null,allRes);

            List<String> menus = Lists.newArrayList();
            List<String> hrefs = Lists.newArrayList();
            Map<String,List<String>> resMap = new HashMap<>();
            if (BlankUtil.isNotEmpty(resourcesTree)){
                for (SysResourceVo vo : resourcesTree){
                    menus.add(vo.getCode());
                }
            }
            if (BlankUtil.isNotEmpty(resources)){
                for (SysResource po : resources){
                    if (BlankUtil.isNotEmpty(po.getHref())){
                        hrefs.add(po.getHref());
                    }
                }
            }
            buildResMap(resMap,null,resourcesTree);

            info.setUserResMap(userResMap);
//            info.setResources(resourcesTree);
            info.setMenus(menus);
            info.setResMap(resMap);
            info.setHrefs(hrefs);
        }

        return info;
    }

    public Map<String, List<SysResourceVo>> findAllResMap() {
        Map<String, List<SysResourceVo>> resMap = new HashMap<>();
        List<SysResourceVo> voList = sysResourceMapper.findResources();
        if (BlankUtil.isNotEmpty(voList)){
            voList = BuildTree.buildTree(voList);
        }
        buildPageCodeMap(resMap,voList,null);
        return resMap;
    }

    private void buildPageCodeMap(Map<String, List<SysResourceVo>> resMap, List<SysResourceVo> voList, String type) {
        if (BlankUtil.isNotEmpty(voList)){
            for (SysResourceVo vo : voList){
                //1.本身是页面
                //2.下级是按钮与字段
                if ((BlankUtil.isEmpty(type) && vo.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName()))
                        || (BlankUtil.isNotEmpty(type) && type.equals(SysEnums.ResourceTypeEnums.PAGE.getName())) ){
                    if (BlankUtil.isNotEmpty(vo.getChildren())){
                        Boolean pageFlag = false;
                        //是否存在按钮
                        for (SysResourceVo children : vo.getChildren()){
                            if ( children.getType().equals(SysEnums.ResourceTypeEnums.BUTTON.getName())
                                    || children.getType().equals(SysEnums.ResourceTypeEnums.FIELD.getName())){
                                pageFlag = true;
                                break;
                            }
                        }
                        //是否即存在按钮、又存在页面
                        if (pageFlag){
                            for (SysResourceVo children : vo.getChildren()){
                                if (children.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName())){
                                    buildPageCodeMap(resMap,vo.getChildren(),vo.getType());
//                                    resMap.put((children.getParentId()==null?"":children.getParentId())+children.getCode(),children.getChildren());
                                }
                            }
                        }
                        if (pageFlag){
                            //以parentId+页面code作为key。防止不同分支下同code产生的覆盖行为
                            resMap.put((vo.getParentId()==null?"":vo.getParentId()) + vo.getCode(),vo.getChildren());
                        }else {
                            buildPageCodeMap(resMap,vo.getChildren(),vo.getType());
                        }
                    }else {
                        resMap.put((vo.getParentId()==null?"":vo.getParentId()) + vo.getCode(),Lists.newArrayList());
                    }
                }
            }
        }
    }

    /**
     * 构建资源集合
     * @param userResMap    返回值容器
     * @param resourcesTree 入参：用户具有的资源树
     * @param pageCode  页面code
     * @param type      资源类型
     * @param allRes    获取的所有 最下级页面-按钮 对照map
     */
    private void buildUserResMap(LinkedHashMap<String,Object> userResMap, List<SysResourceVo> resourcesTree, String pageCode, String type, Map<String,List<SysResourceVo>> allRes) {
        Map<String,Object> button = new HashMap<>();
        List<String> field = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(resourcesTree)){
            //是否是页面资源 -本次遍历的是否是按钮或菜单
            Boolean pageFlag = false;
            Map<String,SysResourceVo> resourcesTreeMap = new HashMap<>();
            for (SysResourceVo vo : resourcesTree){
                if (vo.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName())){
                    LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                    buildUserResMap(map,vo.getChildren(),(vo.getParentId()==null?"":vo.getParentId())+vo.getCode(),vo.getType(),allRes);
                    userResMap.put(vo.getCode(),map);
                    resourcesTreeMap.put(vo.getId(),vo);
                }else {
                    pageFlag = true;
                    resourcesTreeMap.put(vo.getId(),vo);
                    continue;
                }
            }

            /*补充校验：如果该层次用户具有的资源仅有页面，则还需确认在资源树中是否存在按钮及其他*/
            if (!pageFlag){
                if (allRes.get(pageCode) != null){
                    for (SysResourceVo vo : allRes.get(pageCode)){
                        if (!vo.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName())){
                            pageFlag = true;
                            break;
                        }
                    }
                }
            }

            if (pageFlag){
                if (allRes.get(pageCode) != null){
                    //遍历比较
                    for (SysResourceVo vo : allRes.get(pageCode)){
                        if (vo.getType().equals(SysEnums.ResourceTypeEnums.BUTTON.getName())){
                            if (resourcesTreeMap.containsKey(vo.getId())){
                                button.put(vo.getCode(),true);
                            }else {
                                button.put(vo.getCode(),false);
                            }
                        }else if (vo.getType().equals(SysEnums.ResourceTypeEnums.FIELD.getName())){
                            if ( !resourcesTreeMap.containsKey(vo.getId())){
                                field.add(vo.getName());
                            }
                        }else if (vo.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName())){
                            if ( resourcesTreeMap.containsKey(vo.getId())){
                                LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                                buildUserResMap(map,resourcesTreeMap.get(vo.getId()).getChildren(),(vo.getParentId()==null?"":vo.getParentId())+vo.getCode(),vo.getType(),allRes);
                                userResMap.put(vo.getCode(),map);
                            }
                        }
                    }
                    userResMap.put(SysEnums.ResourceTypeEnums.BUTTON.getName(),button);
                    userResMap.put(SysEnums.ResourceTypeEnums.FIELD.getName(),field);
                }
            }
        }

        //资源下没有
        else if (SysEnums.ResourceTypeEnums.PAGE.getName().equals(type)){
            if (allRes.get(pageCode) != null){
                //遍历比较
                for (SysResourceVo vo : allRes.get(pageCode)){
                    if (vo.getType().equals(SysEnums.ResourceTypeEnums.BUTTON.getName())){
                        button.put(vo.getCode(),false);
                    }else if (vo.getType().equals(SysEnums.ResourceTypeEnums.FIELD.getName())){
                        field.add(vo.getName());
                    }else if (vo.getType().equals(SysEnums.ResourceTypeEnums.PAGE.getName())){
                        userResMap.put(vo.getCode(),new HashMap<>());
                    }
                }
                userResMap.put(SysEnums.ResourceTypeEnums.BUTTON.getName(),button);
                userResMap.put(SysEnums.ResourceTypeEnums.FIELD.getName(),field);
            }
        }
    }

    /**
     * 构建父子结构Map
     * @param resMap
     * @param resList
     */
    private void buildResMap(Map<String, List<String>> resMap, SysResourceVo parent, List<SysResourceVo> resList) {
        List<String> childCodes = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(resList)){
            for (SysResourceVo res : resList){
                childCodes.add(res.getCode());
                if (BlankUtil.isNotEmpty(res.getChildren())){
                    buildResMap(resMap,res,res.getChildren());
                }else {
                    if ( BlankUtil.isNotEmpty(res.getType())
                            && !res.getType().equals(SysEnums.ResourceTypeEnums.FIELD.getName())
                            && BlankUtil.isNotEmpty(res.getCode())){
                        resMap.put(res.getCode(),Lists.newArrayList());
                    }
                }
            }
        }
        if (BlankUtil.isNotEmpty(parent)){
            if ( BlankUtil.isNotEmpty(parent.getType())
                    && !parent.getType().equals(SysEnums.ResourceTypeEnums.FIELD.getName())
                    && BlankUtil.isNotEmpty(parent.getCode())){
                resMap.put(parent.getCode(),childCodes);
            }
        }
    }

    private void addOtherOrgInfoByOrgPath(UserInfo info, String orgPath) {
        if (StringUtils.isNotBlank(orgPath)){
            String[] orgs = orgPath.split(",");
            List<String> orgStrs = Arrays.asList(orgs);
            List<SysOrg> list = sysOrgMapper.findByIds(orgStrs);
            String storePath = "";
            String areaPath = "";
            String certPath = "";
            if (BlankUtil.isNotEmpty(list)){
                for (SysOrg org : list){
                    if (SysEnums.OrgTypeEnums.CERT.getType().equals(org.getType())){
                        if (org.getIdPath().length() > certPath.length()){
                            info.setCertId(org.getId());
                        }
                    }else if (SysEnums.OrgTypeEnums.AREA.getType().equals(org.getType())){
                        if (org.getIdPath().length() > areaPath.length()){
                            info.setAreaId(org.getId());
                        }
                    }else if (SysEnums.OrgTypeEnums.STORE.getType().equals(org.getType())){
                        if (org.getIdPath().length() > storePath.length()){
                            info.setStoreId(org.getId());
                        }
                    }
                }
                String deptName = (list.size()<2?"":list.get(1).getName())
                        + "-" + (list.get(0)==null?"":list.get(0).getName());
                info.setDeptName(deptName);
            }
        }
    }

    @Override
    @Transactional
    public OrderUserDTO createUserByOrder(OrderUserDTO userDTO) {
        OrderUserDTO returnDTO = new OrderUserDTO();
        //检验入参
        FormCheckUtil.checkRequiredField(userDTO.getAction(),"操作类型");
        FormCheckUtil.checkRequiredField(userDTO.getIdType(),"证件类型");
        FormCheckUtil.checkRequiredField(userDTO.getIdNum(),"证件号码");

        //新增
        if (userDTO.getAction().equals(0)){
            FormCheckUtil.checkRequiredField(userDTO.getName(),"用户名");
            FormCheckUtil.checkRequiredField(userDTO.getMobile(),"手机号");
            SysUser user = this.findUserByIdTypeAndNum(userDTO.getIdType(),userDTO.getIdNum());
            if (BlankUtil.isEmpty(user)){
                //历史没有则新增
                user = new SysUser();
                //校验登录账号唯一
                checkUserAccount(userDTO.getMobile());
                PoDefaultValueGenerator.putDefaultValue(user);
                user.setAccount(userDTO.getMobile());
                user.setPassword(MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD));
                user.setName(userDTO.getName());
                user.setValidDate(new Date());
                user.setLocked(false);
                user.setStatus(false);
                user.setNationality(userDTO.getNationality());
                user.setSex(userDTO.getSex());
                user.setBirthday(userDTO.getBirthday());
                user.setMobile(userDTO.getMobile());
                user.setIdType(userDTO.getIdType());
                user.setIdNum(userDTO.getIdNum());
                user.setTel(userDTO.getTel());
                user.setQq(userDTO.getQq());
                user.setWechat(userDTO.getWechat());
                sysUserMapper.insertSelective(user);
            }
            //新建学员角色
            insertOrderUserRole(user.getId());
            returnDTO.setUserId(user.getId());
            returnDTO.setSysUser(user);
        }else if (userDTO.getAction().equals(1)){
            FormCheckUtil.checkRequiredField(userDTO.getUserId(),"用户id");
            SysUser update = sysUserMapper.selectByPrimaryKey(userDTO.getUserId());
            if (BlankUtil.isNotEmpty(update)){
                //构建新对象，更新
                SysUser user = new SysUser();
                //如果原用户没有录入证件号码。可以在这次录入证件号码
                if (BlankUtil.isEmpty(update.getIdNum())){
                    user.setIdType(userDTO.getIdType());
                    user.setIdNum(userDTO.getIdNum());
                }
                //如果原用户证件号码与证件类型 与 本次修改后不一致，则若系统不存在此证件号码。允许更新
                if (BlankUtil.isNotEmpty(update.getIdType()) && BlankUtil.isNotEmpty(update.getIdNum())
                        && BlankUtil.isNotEmpty(userDTO.getIdType()) && BlankUtil.isNotEmpty(userDTO.getIdNum())
                        && (!update.getIdType().equals(userDTO.getIdType()) || !update.getIdNum().equals(userDTO.getIdNum()))
                        ){
                    SysUser checkIdTypeAndIdNum = null;
                    try{
                        checkIdTypeAndIdNum = this.findUserByIdTypeAndNum(userDTO.getIdType(),userDTO.getIdNum());
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new ServiceException("数据异常！用户修改后的证件类型与号码在系统中存在多个，请联系管理员！");
                    }
                    if (BlankUtil.isEmpty(checkIdTypeAndIdNum)){
                        user.setIdType(userDTO.getIdType());
                        user.setIdNum(userDTO.getIdNum());
                    }else {
                        throw new ServiceException("用户修改后的证件类型与号码在系统中已存在，更新失败！");
                    }
                }
                user.setId(update.getId());
                user.setNationality(userDTO.getNationality());
                user.setSex(userDTO.getSex());
                user.setBirthday(userDTO.getBirthday());
                user.setMobile(userDTO.getMobile());
                user.setTel(userDTO.getTel());
                user.setQq(userDTO.getQq());
                user.setWechat(userDTO.getWechat());
                PoDefaultValueGenerator.putDefaultValue(user);
                sysUserMapper.updateByPrimaryKeySelective(user);
                SysUser newUser = sysUserMapper.selectByPrimaryKey(userDTO.getUserId());
                returnDTO.setUserId(userDTO.getUserId());
                returnDTO.setSysUser(newUser);
            }else {
                throw new ServiceException("未找到该id【"+userDTO.getUserId()+"】对应的用户对象！");
            }
        }else {
            throw new ServiceException("未定义的操作类型！");
        }
        return returnDTO;
    }


    public void createUserByOrderList(List<SysUser> listUser) {
            sysUserMapper.batchInsert(listUser);
            //新建学员角色
            //insertOrderUserRole(user.getId());

    }

    private void insertOrderUserRole(String id) {
        //获取学员角色id
        SysRole role = sysRoleService.selectByCode(SecConstant.ORDER_USER_ROLECODE);
        if (BlankUtil.isNotEmpty(role)){
            //构建关联关系
            SysUserRole ur = new SysUserRole();
            ur.setUserId(id);
            ur.setRoleId(role.getId());
            //获取是否已存在该角色
            List<SysUserRole> isExist = sysUserRoleMapper.selectByCondition(ur);
            if (BlankUtil.isEmpty(isExist)){
                //新增学员角色
                sysUserRoleMapper.insertSelective(ur);
            }
        }else {
            throw new ServiceException("获取角色编码为【"+SecConstant.ORDER_USER_ROLECODE+"】的生效角色失败！");
        }
    }

    private void checkUserAccount(String mobile) {
        Integer num = sysUserMapper.checkUserAccount(mobile);
        if (BlankUtil.isNotEmpty(num) && num != 0 ){

            throw new ServiceException("该用户对应的手机号【"+mobile+"】已存在账号！");
        }
    }

    private SysUser findUserByIdTypeAndNum(String idType, String idNum) {
        //检查用户表，若存在则返回对象
        List<SysUser> userList = sysUserMapper.findUserByIdTypeAndNum(idType,idNum);
        if (BlankUtil.isNotEmpty(userList)){
            if (userList.size() == 1){
                return userList.get(0);
            }else {
                throw new ServiceException("系统异常，该证件号码【"+idNum+"】对应多个有效用户！");
            }
        }
        //若类型为0-身份证，检查员工表，若存在则组装user，并返回对象
        if (idType.equals("0")){
            List<SysUser> empList = sysUserMapper.findEmpByIdTypeAndNum(idType,idNum);
            if (BlankUtil.isNotEmpty(empList)){
                if (empList.size() == 1){
                    return empList.get(0);
                }else {
                    throw new ServiceException("系统异常，该证件号码【"+idNum+"】对应多个有效用户！");
                }
            }
        }
        //不存在则返回空
        return null;
    }

    @Override
    public void insertUserWithEmployees(List<SysEmployee> employeeList) {
        try {
            if (BlankUtil.isNotEmpty(employeeList)){
                insertWithEmployeesThread(employeeList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertWithEmployeesThread(final List<SysEmployee> employeeList){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    //构建
                    insertUserWithEmployeesMethod(employeeList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        cachedThreadPool.shutdown();
    }

    private void insertUserWithEmployeesMethod(List<SysEmployee> employeeList) {
        if (BlankUtil.isNotEmpty(employeeList)){
            List<String> ids = Lists.newArrayList();
            //1.遍历获取empId集合
            for (SysEmployee emp : employeeList){
                if (BlankUtil.isNotEmpty(emp.getId())){
                    ids.add(emp.getId());
                }
            }
            //2.根据ids获取<id，SysUser>集合
            Map<String,SysUser> userMap = getUserMapByIds(ids);
            List<SysEmployee> insertList = Lists.newArrayList();
            List<SysEmployee> updateList = Lists.newArrayList();
            List<SysUser> uInsertList = Lists.newArrayList();
            List<SysUser> uUpdateList = Lists.newArrayList();
            //3.遍历组装本次新增的员工 与更新的员工 集合
            List<String> checkAccount = Lists.newArrayList();
            List<String> checkIdNum = Lists.newArrayList();
            for (SysEmployee emp : employeeList){
                if (userMap.containsKey(emp.getId())){
                    updateList.add(emp);
                }else {
                    insertList.add(emp);
                    if (BlankUtil.isNotEmpty(emp.getMobile())){
                        checkAccount.add(emp.getMobile());
                    }
                    if (BlankUtil.isNotEmpty(emp.getIdcardNumber())){
                        checkIdNum.add(emp.getIdcardNumber());
                    }
                }
            }
            //4.校验更新员工集合
            //4.1 仅更新尚未维护过的历史“默认创建”用户
            if (BlankUtil.isNotEmpty(updateList)){
                for (SysEmployee update : updateList){
                    SysUser uUpdate = buildDefaultUser(update,1);
                    uUpdateList.add(uUpdate);
                }
                updateDefaultCreatedUser(uUpdateList);
            }
            //4.2 新增本次同步的新员工
            Map<String,SysUser> accountMap = checkAccount(checkAccount);
            Map<String,SysUser> idNumMap = checkIdNum(checkIdNum);
            if (BlankUtil.isNotEmpty(insertList)){
                for (SysEmployee insert : insertList){
                    if (BlankUtil.isNotEmpty(insert.getMobile()) && accountMap.containsKey(insert.getMobile())){
                        continue;
                    }
                    if (BlankUtil.isNotEmpty(insert.getIdcardNumber()) && idNumMap.containsKey(insert.getIdcardNumber())){
                        continue;
                    }
                    SysUser uInsert = buildDefaultUser(insert,0);
                    uInsertList.add(uInsert);
                }
                if (BlankUtil.isNotEmpty(uInsertList)){
                    sysUserMapper.batchInsert(uInsertList);
                    //获取默认角色id
                    SysRole role = sysRoleService.selectByCode(SecConstant.DEFAULT_USER_ROLECODE);
                    if (BlankUtil.isNotEmpty(role)){
                        //构建关联关系
                        for (SysUser rela : uInsertList){
                            SysUserRole ur = new SysUserRole();
                            ur.setUserId(rela.getId());
                            ur.setRoleId(role.getId());
                            //获取是否已存在该角色
                            List<SysUserRole> isExist = sysUserRoleMapper.selectByCondition(ur);
                            if (BlankUtil.isEmpty(isExist)){
                                //新增默认角色
                                sysUserRoleMapper.insertSelective(ur);
                            }
                        }
                    }else {
                        logger.error("获取默认角色角色编码失败：尝试获取角色对象失败，角色编码为【"+SecConstant.DEFAULT_USER_ROLECODE+"】");
                    }
                }
            }
        }
    }

    private void updateDefaultCreatedUser(List<SysUser> userList) {
        if (BlankUtil.isNotEmpty(userList)){
            for (SysUser user : userList){
                try {
                    sysUserMapper.updateDefaultCreatedUser(user);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String, SysUser> checkIdNum(List<String> idNumList) {
        Map<String, SysUser> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(idNumList)){
            List<SysUser> userList = sysUserMapper.checkIdNum(idNumList);
            if (BlankUtil.isNotEmpty(userList)){
                for (SysUser user : userList){
                    map.put(user.getIdNum(),user);
                }
            }
        }
        return map;
    }

    private Map<String, SysUser> checkAccount(List<String> accountList) {
        Map<String, SysUser> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(accountList)){
            List<SysUser> userList = sysUserMapper.checkAccount(accountList);
            if (BlankUtil.isNotEmpty(userList)){
                for (SysUser user : userList){
                    map.put(user.getAccount(),user);
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, SysUser> getUserMapByIds(List<String> ids) {
        Map<String,SysUser> map = new HashMap<>();
        List<SysUser> list = sysUserMapper.getUserMapByIds(ids);
        if (BlankUtil.isNotEmpty(list)){
            for (SysUser user : list){
                map.put(user.getId(),user);
            }
        }
        return map;
    }

    /**
     * 构建默认用户
     * @param employee 员工对象
     * @param action 0-新增 1-更新
     * @return
     */
    private SysUser buildDefaultUser(SysEmployee employee,Integer action) {
        SysUser user = new SysUser();
        //设置默认字段
        BeanUtils.copyProperties(employee,user);
        //设置是否默认创建标识
        user.setDefaultCreated("1");
        user.setAccount(employee.getMobile());
        user.setPassword(MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD));
        user.setName(employee.getName());
        user.setCode(employee.getWorkCard());
        user.setAlias(null);
        user.setEmail(null);
        //若是离职员工，默认停用账户
        if (employee.getStatus() != null && SecConstant.EMP_STATUS_2.equals(employee.getStatus())){
            user.setStatus(true);
        }
        user.setMobile(employee.getMobile());
        user.setNationality("0");
        user.setIdType("0");
        user.setIdNum(employee.getIdcardNumber());
        user.setSex(employee.getSex());
        user.setBirthday(employee.getBirthday());
        user.setTel(employee.getPhone());
        //生效时间
        if (BlankUtil.isNotEmpty(action) && action.equals(1)){
            user.setValidDate(null);
            user.setLocked(null);
            user.setDescription(null);
            user.setQq(null);
            user.setWechat(null);
        }else {
            user.setValidDate(new Date());
            user.setLocked(false);
            user.setDescription(null);
            user.setQq(null);
            user.setWechat(null);
        }
        return user;
    }

    @Override
    public List<SysUser> selectNamesByCondition(SysUser condition) {
        return sysUserMapper.selectNamesByCondition(condition);
    }

    @Override
    public SysUser selectByIdTypeAndNumber(String idType, String idNum) {
        List<SysUser> list = sysUserMapper.selectByIdTypeAndNumber(idType,idNum);
        if (BlankUtil.isNotEmpty(list) && list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findHistoryStudent(String query, String orgId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<SysUser> list = sysUserMapper.findHistoryStudent(query,orgId,SecConstant.ORDER_USER_ROLECODE);
        if (BlankUtil.isNotEmpty(list)){
            for (SysUser user : list){
                Map<String,Object> map = new HashMap<>();
                map.put("value",user.getId());
                map.put("label",user.getName());
                map.put("idNum",user.getIdNum());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public void batchInsert(List<SysUser> userList) {
        if (BlankUtil.isNotEmpty(userList)){
            sysUserMapper.batchInsert(userList);
        }
    }

    @Override
    public void insertWithEmployee() {
        sysUserMapper.insertWithEmployee();
    }

    @Override
    public Map<String, SysUser> findMapByIds(List<String> poIdList) {
        Map<String, SysUser> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(poIdList)){
            List<SysUser> poList = sysUserMapper.findByIds(poIdList);
            if (BlankUtil.isNotEmpty(poList)){
                for (SysUser po : poList){
                    map.put(po.getId(),po);
                }
            }
        }
        return map;
    }

    @Override
    public void insertWithEmployees(List<SysEmployee> list) {
        if (BlankUtil.isNotEmpty(list)){
            sysUserMapper.insertWithEmployees(list);
        }
    }

    @Override
    public void updateWithEmployees(List<SysEmployee> list) {
        if (BlankUtil.isNotEmpty(list)){
            sysUserMapper.updateWithEmployees(list);
        }
    }

    @Override
    public boolean checkAccountAndIdNumber(String account, String idType, String idNum) {
        if (BlankUtil.isEmpty(account) && BlankUtil.isEmpty(idType) && BlankUtil.isEmpty(idNum)){
            return true;
        }
        SysUser user = new SysUser();
        if (BlankUtil.isNotEmpty(account)){
            user.setAccount(account);
        }
        if (BlankUtil.isNotEmpty(idType) && BlankUtil.isNotEmpty(idNum)){
            user.setIdType(idType);
            user.setIdNum(idNum);
        }
        List<SysUser> list = sysUserMapper.findUsersByCustomerCondition(user);
        if (BlankUtil.isNotEmpty(list)){
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        sysUserMapper.removeAll();
    }

    @Override
    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        //获取用户
        if (BlankUtil.isNotEmpty(userId)){
            SysUser user = sysUserMapper.selectByPrimaryKey(userId);
            if (BlankUtil.isNotEmpty(user)){
                SysUser update = new SysUser();

                update.setId(user.getId());
                PoDefaultValueGenerator.putDefaultValue(update);

                //若原密码不为空，则需要校验原密码正确性
                if (BlankUtil.isNotEmpty(oldPassword)){
                    if (BlankUtil.isNotEmpty(user.getPassword()) && !MD5Util.MD5PWD(oldPassword).equals(user.getPassword())){
                        throw new ServiceException("修改密码出错：原密码校验失败！");
                    }
                }

                if (BlankUtil.isNotEmpty(newPassword)){
                    update.setPassword(MD5Util.MD5PWD(newPassword));
                    sysUserMapper.updateByPrimaryKeySelective(update);
                }
            }
        }
    }

    @Override
    public AppUserInfoDTO getUserInfoForApp(String userId) {
        AppUserInfoDTO userInfo = new AppUserInfoDTO();
        if (BlankUtil.isNotEmpty(userId)){
            userInfo.setId(userId);
            SysUser user = sysUserMapper.selectByPrimaryKey(userId);
            if (BlankUtil.isNotEmpty(user)){
                userInfo.setName(user.getName());
                userInfo.setEmail(user.getEmail());
                userInfo.setMobile(user.getMobile());
                userInfo.setTel(user.getTel());
                userInfo.setSex((user.getSex()==null||user.getSex().equals(""))?"":(user.getSex().equals("0")?"男":"女"));
            }

            SysEmployeeVo employee = sysEmployeeMapper.findEmployeeVoById(userId);
            if (BlankUtil.isNotEmpty(employee)){
                if (BlankUtil.isNotEmpty(employee.getPhoto())){
                    FileKeysDTO photo = new FileKeysDTO();
                    photo.setFileKeyStr(employee.getPhoto());
                    photo.setStyle(SecConstant.FILE_STYLE);
                    FileKeysDTO dto = fileService.getDownUrlByKeys(photo);
                    if (BlankUtil.isNotEmpty(dto)){
                        userInfo.setPhoto(dto.getDownUrlStr());
                    }
                }
                userInfo.setWorkCard(employee.getWorkCard());
                userInfo.setCompanyName(employee.getCompanyName());
                userInfo.setDeptName(employee.getDeptName());
                userInfo.setPositionName(employee.getPositionName());
            }
        }
        return userInfo;
    }

    @Override
    public Map<String, SysUser> findExistUser(List<String> accountList) {
        Map<String,SysUser> userMap = new HashMap<>();
        if (BlankUtil.isNotEmpty(accountList)){
            List<SysUser> userList = sysUserMapper.findAccountUserMap(accountList);
            if (BlankUtil.isNotEmpty(userList)){
                for (SysUser po : userList){
                    userMap.put(po.getDeleteFlag()+po.getAccount()+po.getOrgId(),po);
                }
            }
        }
        return userMap;
    }

    @Override
    public SysUser findByAccount(String account, String orgId) {
        if (BlankUtil.isEmpty(account)){
            return null;
        }
        SysUser condition = new SysUser();
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        condition.setStatus(false);
        condition.setAccount(account);
        condition.setOrgId(orgId);
        List<SysUser> userList = sysUserMapper.selectByCondition(condition);

        if (BlankUtil.isNotEmpty(userList) && userList.size() == 1){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public SysUser findByAccount(String account) {
        return findByAccount(account,null);
    }

    @Override
    public String remoteRegister(SysUserDTO param) {
        //检验参数
        if (BlankUtil.isEmpty(param.getAccount())
                || BlankUtil.isEmpty(param.getPassword())
                || BlankUtil.isEmpty(param.getAuthCode())){
            throw new ServiceException("必填参数为空！");
        }
        if (BlankUtil.isEmpty(param.getAccount()) || param.getAccount().length() != 11){
            throw new ServiceException("手机号码格式异常！");
        }
        SysUser user = findRemoteUserOne(param.getAccount());
        boolean updateFlag = false;
        if (user != null){
            if (user.getApprovalStatus()==null || "2".equals(user.getApprovalStatus())){//审核状态：0-未审核，1-审核通过，2-审核拒绝
                //更新而不新增
                updateFlag = true;
            }else if ("0".equals(user.getApprovalStatus())){
                throw new ServiceException("用户正在审核中！");
            }else {
                throw new ServiceException("用户已存在！");
            }
        }

        if(!authcodeService.isValid(param.getAuthCode(),param.getAccount(), AuthCodeConstants.REQTYPE_REMOTE_REGISTER_1)){
            throw new ServiceException("短信验证码有误！");
        }

        //校验身份证号码唯一
        if (BlankUtil.isNotEmpty(param.getIdNum())){
            Map<String,SysUser> idNumUserMap = getIdNumUserMap(Collections.singletonList(param.getIdNum()));
            if (idNumUserMap.get(param.getIdNum())!=null){
                throw new ServiceException("已存在该身份证号的用户！");
            }
        }


        SysRole role = null;
        if (BlankUtil.isNotEmpty(param.getRemoteRole())){
            role = sysRoleService.selectByCode(param.getRemoteRole());
        }

        //录入数据库
        SysUser sysUser = new SysUser();
        Date now = new Date();
        if (updateFlag){
            sysUser.setPassword(MD5Util.MD5PWD(param.getPassword()));
            sysUser.setName(param.getName());
            sysUser.setMobile(param.getAccount());
            sysUser.setApprovalStatus("0");
            sysUser.setRemoteRole(param.getRemoteRole());
            if (role != null){
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                try {
                    sysUserRoleMapper.insertSelective(ur);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (BlankUtil.isNotEmpty(param.getIdNum())){
                sysUser.setIdType("0");
                sysUser.setIdNum(param.getIdNum());
            }
            sysUser.setSex(param.getSex());
            sysUser.setId(user.getId());
            sysUser.setLastUpdated(new Date());
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }else {
            sysUser.setId(UUIDGenerate.getNextId());
            sysUser.setCreatedAt(now);
            sysUser.setLastUpdated(now);
            sysUser.setDisable(false);
            sysUser.setDeleteFlag(false);

            sysUser.setAccount(param.getAccount());
            sysUser.setPassword(MD5Util.MD5PWD(param.getPassword()));
            sysUser.setName(param.getAccount());
            sysUser.setMobile(param.getAccount());
            sysUser.setStatus(false);
            sysUser.setLocked(false);
            sysUser.setValidDate(new Date());
            sysUser.setApprovalStatus("1");
            sysUser.setRemoteRole(param.getRemoteRole());
            if (role != null){
                SysUserRole ur = new SysUserRole();
                ur.setUserId(sysUser.getId());
                ur.setRoleId(role.getId());
                sysUserRoleMapper.insertSelective(ur);
            }
            if (BlankUtil.isNotEmpty(param.getIdNum())){
                sysUser.setIdType("0");
                sysUser.setIdNum(param.getIdNum());
            }
            sysUser.setSex(param.getSex());
            sysUserMapper.insertSelective(sysUser);
        }
        return sysUser.getId();
    }

    private Map<String, SysUser> getIdNumUserMap(List<String> list) {
        Map<String, SysUser> idNumUserMap = new HashMap<>();
        if (BlankUtil.isNotEmpty(list)){
            List<SysUser> idNumUserList = sysUserMapper.findUserByIdNums(list);
            if (BlankUtil.isNotEmpty(idNumUserList)){
                for (SysUser user : idNumUserList){
                    idNumUserMap.put(user.getIdNum(),user);
                }
            }
        }
        return idNumUserMap;
    }

    public SysUser findRemoteUserOne(String account) {
        List<SysUser> userList = sysUserMapper.findRemoteUserByAccounts(Collections.singletonList(account));
        if (userList!=null && userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public Map<String, SysUser> findRemoteUsers(List<String> accounts) {
        Map<String,SysUser> map = new HashMap<>();
        List<SysUser> userList = sysUserMapper.findRemoteUserByAccounts(accounts);
        if (BlankUtil.isNotEmpty(userList)){
            for (SysUser user : userList){
                map.put(user.getAccount(),user);
            }
        }
        return map;
    }

    @Override
    public void userApproval(String ids, String status) {
        if (BlankUtil.isEmpty(ids)){
            throw new ServiceException(ErrorCodeEnums.BUSINESS_ERROR.getCode(),"参数为空！");
        }
        if (BlankUtil.isEmpty(status)){
            throw new ServiceException("操作参数为空！");
        }

        List<String> idList = Lists.newArrayList();
        idList.addAll(Arrays.asList(ids.split(",")));
        SysUser po = new SysUser();
        if ("0".equals(status)){//审核拒绝
            po.setApprovalStatus("2");//审核状态：0-未审核，1-审核通过，2-审核拒绝
        }else if ("1".equals(status)){//审核同意
            po.setApprovalStatus("1");
        }else {
            throw new ServiceException("未定义的操作类型！值为【"+status+"】");
        }
        po.setUpdatedUser(LoginManager.getCurrentUserId());
        po.setLastUpdated(new Date());
        sysUserMapper.updateByIdsSelective(po,idList);
    }

    @Override
    public Page<SysUser> remoteUserList(SysUserDTO param) {
        PageHelper.startPage(param.getP(),param.getS());
        List<SysUser> list = sysUserMapper.findRemoteUserList(param);
        return (Page) list;
    }

    @Override
    @Transactional
    public Map<String, String> addRemoteUser(List<SysUserDTO> dtos) {
        //List<SysUser> returnUserList = Lists.newArrayList();
        Map<String,String> map =new  HashMap<>();
        if (BlankUtil.isNotEmpty(dtos)){
            //新增前置校验
            List<String> phoneNums = Lists.newArrayList();
            List<String> roleCodes = Lists.newArrayList();
            List<String> idNums = Lists.newArrayList();
            for (SysUserDTO dto : dtos){
                if (BlankUtil.isNotEmpty(dto.getAccount())){
                    phoneNums.add(dto.getAccount());
                }
                if (BlankUtil.isNotEmpty(dto.getRemoteRole())){
                    roleCodes.add(dto.getRemoteRole());
                }
                if (BlankUtil.isNotEmpty(dto.getIdNum())){
                    idNums.add(dto.getIdNum());
                }

            }
            //已存在的用户集合
            Map<String,SysUser> userMap = findRemoteUsers(phoneNums);

            //身份证号码存在的用户集合
            Map<String,SysUser> idNumUserMap = new HashMap<>();
            if (BlankUtil.isNotEmpty(idNums)){
                idNumUserMap = getIdNumUserMap(idNums);
            }

            //涉及到的角色集合
            Map<String,SysRole> roleMap = sysRoleService.findRoleByCodes(roleCodes);

            Date now = new Date();
            String createdUserId = LoginManager.getCurrentUserId();

            List<SysUser> insertUserList = Lists.newArrayList();
            List<SysUserRole> userRoleList = Lists.newArrayList();
            for (SysUserDTO dto : dtos){
                boolean updateFlag = false;
                if (userMap.get(dto.getAccount()) != null){
                    updateFlag = true;
                }

                if (updateFlag){
                    SysUser sysUser = new SysUser();
                    if (BlankUtil.isNotEmpty(dto.getPassword())){
                        sysUser.setPassword(MD5Util.MD5PWD(dto.getPassword()));
                    }else {
                        sysUser.setPassword(MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD));
                    }
                    sysUser.setName(dto.getName());
                    sysUser.setMobile(dto.getAccount());
                    if (BlankUtil.isNotEmpty(dto.getApprovalStatus())){
                        sysUser.setApprovalStatus(dto.getApprovalStatus());
                    }else {
                        sysUser.setApprovalStatus("1");
                    }
                    //置入角色
                    sysUser.setRemoteRole(dto.getRemoteRole());
                    if (BlankUtil.isNotEmpty(dto.getRemoteRole()) && roleMap.get(dto.getRemoteRole())!=null){
                        SysUserRole ur = new SysUserRole();
                        ur.setUserId(userMap.get(dto.getAccount()).getId());
                        ur.setRoleId(roleMap.get(dto.getRemoteRole()).getId());
                        try{
                            sysUserRoleMapper.insertSelective(ur);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (BlankUtil.isNotEmpty(dto.getIdNum())){
                        if (idNumUserMap.get(dto.getIdNum())!=null){
                            throw new ServiceException("已存在身份证号为【"+dto.getIdNum()+"】的用户！新增失败！");
                        }
                        sysUser.setIdType("0");
                        sysUser.setIdNum(dto.getIdNum());
                    }
                    sysUser.setSex(dto.getSex());
                    sysUser.setId(userMap.get(dto.getAccount()).getId());
                    sysUser.setUpdatedUser(createdUserId);
                    sysUser.setLastUpdated(new Date());
                    sysUserMapper.updateByPrimaryKeySelective(sysUser);
                    //returnUserList.add(sysUser);
                    map.put(sysUser.getAccount(),sysUser.getId());
                }else {
                    SysUser sysUser = new SysUser();

                    sysUser.setId(UUIDGenerate.getNextId());
                    sysUser.setCreatedUser(createdUserId);
                    sysUser.setUpdatedUser(createdUserId);
                    sysUser.setCreatedAt(now);
                    sysUser.setLastUpdated(now);
                    sysUser.setDisable(false);
                    sysUser.setDeleteFlag(false);

                    sysUser.setAccount(dto.getAccount());
                    if (BlankUtil.isNotEmpty(dto.getPassword())){
                        sysUser.setPassword(MD5Util.MD5PWD(dto.getPassword()));
                    }else {
                        sysUser.setPassword(MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD));
                    }
                    sysUser.setName(dto.getAccount());
                    sysUser.setMobile(dto.getAccount());
                    sysUser.setStatus(false);
                    sysUser.setLocked(false);
                    sysUser.setValidDate(new Date());
                    if (BlankUtil.isNotEmpty(dto.getApprovalStatus())){
                        sysUser.setApprovalStatus(dto.getApprovalStatus());
                    }else {
                        sysUser.setApprovalStatus("1");
                    }
                    sysUser.setRemoteRole(dto.getRemoteRole());
                    if (BlankUtil.isNotEmpty(dto.getRemoteRole()) && roleMap.get(dto.getRemoteRole())!=null){
                        SysUserRole ur = new SysUserRole();
                        ur.setUserId(sysUser.getId());
                        ur.setRoleId(roleMap.get(dto.getRemoteRole()).getId());
                        userRoleList.add(ur);
                    }
                    if (BlankUtil.isNotEmpty(dto.getIdNum())){
                        if (idNumUserMap.get(dto.getIdNum())!=null){
                            throw new ServiceException("已存在身份证号为【"+dto.getIdNum()+"】的用户！新增失败！");
                        }
                        sysUser.setIdType("0");
                        sysUser.setIdNum(dto.getIdNum());
                    }
                    sysUser.setSex(dto.getSex());
                    insertUserList.add(sysUser);
                    //returnUserList.add(sysUser);
                    map.put(sysUser.getAccount(),sysUser.getId());
                }
            }
            //批量新增
            sysUserMapper.batchInsert(insertUserList);
            //用户角色关系
            sysUserRoleMapper.batchInsert(userRoleList);
        }
        return map;
    }

    @Override
    public int selectUserCount() {
        return sysUserMapper.selectUserCount();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int saveOrUpdateDto(Map<String, String> map) {
        AtomicInteger ai = new AtomicInteger(sysUserMapper.insertDto(map));
        if(ai.get() == 0) {
            Optional.ofNullable(sysUserMapper.selectByPrimaryKey(map.get("id"))).ifPresent(user -> {
                BeanUtils.copyProperties(map, user);
                ai.set(sysUserMapper.updateByPrimaryKey(user));
            });
        }
        return ai.get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int updateMobile(Map<String, String> map) {
        return sysUserMapper.updateMobile(map);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int updateUserInfo(Map<String, String> map) {
        return sysUserMapper.updateUserInfo(map);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int updateUserPassword(Map<String, String> map) {
        return sysUserMapper.updateUserPassword(map);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int bindThirdPartAccount(Map<String, String> map) {
        return sysUserMapper.bindThirdPartAccount(map);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int unbindThirdPartAccount(String loginFrom, String mobile) {
        return sysUserMapper.unbindThirdPartAccount(loginFrom, mobile);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @TxTransaction
    @Override
    public int testDistributeTransaction(String id, String remark) {
        return sysUserMapper.testDistributeTransaction(id, remark);
    }
}