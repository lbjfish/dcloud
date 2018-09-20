package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.service.SysCommonService;
import com.sida.dcloud.system.service.SysEmployeeOrgService;
import com.sida.dcloud.system.service.SysOrgService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sysOrg")
@Api(description = "组织管理api")
public class SysOrgController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysOrgController.class);

    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysEmployeeOrgService sysEmployeeOrgService;
    @Autowired
    private SysCommonService sysCommonService;

    @RequestMapping(value = "/redisInit", method = RequestMethod.POST)
    @ApiOperation(value = "将组织表加入redis缓存")
    public Object redisInit() {
        sysOrgService.loadOrgsToRedis();
        return toResult();
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织树")
    public Object tree() {
        Object object = sysOrgService.findTree();
        return toResult(object);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "根据组织id获取对应组织信息")
    public Object selectTreeMesById(@PathVariable("id") @ApiParam("组织ID") String id) {
        Object result = sysOrgService.selectOrgMesById(id);

        return toResult(result);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新组织")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysOrg param) {
        sysOrgService.saveOrUpdateOrg(param);
        return toResult();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除组织信息")
    public Object delete(@PathVariable("id") String id) {
        sysOrgService.deleteById(id);
        return toResult();
    }

    @RequestMapping(value = "/orgTreeFrom/{orgId}", method = RequestMethod.POST)
    @ApiOperation(value = "从某个组织往下追溯组织树")
    public Object orgTreeFrom(@PathVariable("orgId") String orgId) {
        Object object = sysOrgService.findTreeFrom(orgId);
        return toResult(object);
    }

    @RequestMapping(value = "/orgEmps/{orgId}", method = RequestMethod.POST)
    @ApiOperation(value = "获取该组织下所有的员工信息及其岗位")
    public Object orgEmps(@PathVariable("orgId") String orgId) {
        List<EmpDTO> object = sysOrgService.orgEmps(orgId);
//        sysCommonService.putTechType(object);
        return toResult(object);
    }

    @RequestMapping(value = "/orgEmpsForCar", method = RequestMethod.POST)
    @ApiOperation(value = "获取该组织下所有的员工信息及其岗位")
    public Object orgEmps(@RequestBody Map<String,String> map) {
        List<EmpDTO> object = sysOrgService.orgEmpsForCar(map.get("orgId"),map.get("plateColor"));
//        sysCommonService.putTechType(object);
        return toResult(object);
    }

    @RequestMapping(value = "/updateStoreByMarketing", method = RequestMethod.POST)
    @ApiOperation(value = "营销修改门店信息")
    public void updateStoreByMarketing(@RequestBody SysOrg sysOrg) {
        //目前只支持修改营业状态
        sysOrgService.updateStoreByMarketing(sysOrg);
    }

    /*************************************新提供接口 *******************************/

    @RequestMapping(value = "/initArea", method = RequestMethod.POST)
    @ApiOperation(value = "初始化片区")
    public Object initArea() {
        sysOrgService.initArea();
        return toResult();
    }

    @RequestMapping(value = "/initStore", method = RequestMethod.POST)
    @ApiOperation(value = "初始化门店")
    public Object initStore() {
        sysOrgService.initStore();
        return toResult();
    }

    @RequestMapping(value = "/orgTreeFromWithoutStore/{orgId}", method = RequestMethod.POST)
    @ApiOperation(value = "从某个组织往下追溯组织树-排除门店级别")
    public Object orgTreeFromWithoutStore(@PathVariable("orgId") String orgId) {
        Object object = sysOrgService.findTreeFromWithoutStore(orgId);
        return toResult(object);
    }

    @RequestMapping(value = "/orgEmpsLeaderFirst/{employeeId}", method = RequestMethod.POST)
    @ApiOperation(value = "车务事故理赔专用-根据责任人员工id获取部门负责人列表（部门|片区经理）优先")
    public Object findLeaderFirstByEmployeeId(@PathVariable("employeeId") String employeeId) {
        Map<String,Object> map = sysOrgService.findLeaderFirstByEmployeeId(employeeId);
        if (map!=null && map.get("data")!=null){
//            sysCommonService.putTechType((List<EmpDTO>) map.get("data"));
        }
        return toResult(map);
    }

    /*************************************通用接口 ********************************/
    @RequestMapping(value = "/findAllAreas", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有片区数据")
    public Object findAllAreas() {
        return toResult(sysOrgService.findAllAreas());
    }

    @RequestMapping(value = "/findAllAreasForFeign", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有片区数据")
    public List<Map<String,Object>> findAllAreasForFeign() {
        return sysOrgService.findAllAreas();
    }

    @RequestMapping(value = "/findAllStores", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有门店数据")
    public Object findAllStores() {
        return toResult(sysOrgService.findAllStores());
    }

    @RequestMapping(value = "/findAreas", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区数据")
    public Object findAreas() {
        List<SysOrg> list = sysOrgService.findAreasByUserId(null);
        return toResult(list);
    }

    @RequestMapping(value = "/findDeptsByOrgId", method = RequestMethod.GET)
    @ApiOperation(value = "根据驾校id查询该驾校下的所有部门")
    public Object findDeptsByOrgId() {
        List<SysOrg> list = sysOrgService.findDeptsByOrgId(LoginManager.getCurrentOrgId());
        return toResult(list);
    }

    @RequestMapping(value = "/findAreaNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区数据:精简数据")
    public Object findAreaNames() {
        return findAreaNamesByUserId(null);
    }

    @RequestMapping(value = "/findAreasByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在驾校的片区数据")
    public Object findAreasByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findAreasByUserId(userId);
        return toResult(list);
    }

    @RequestMapping(value = "/findAreaNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在驾校的片区数据:精简数据")
    public Object findAreaNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findAreasByUserId(userId);
        List<AreaDTO> dtoList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg po : list){
                AreaDTO dto = new AreaDTO();
                dto.setAreaId(po.getId());
                dto.setAreaName(po.getName());
                dto.setOrgPath(po.getIdPath());
                dtoList.add(dto);
            }
        }
        return toResult(dtoList);
    }

    @RequestMapping(value = "/findStores", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在片区的门店数据")
    public Object findStores() {
        List<SysOrg> list = sysOrgService.findStoresByUserId(null);
        return toResult(list);
    }

    @RequestMapping(value = "/findStoreNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在片区的门店数据:精简数据")
    public Object findStoreNames() {
        return findStoreNamesByUserId(null);
    }

    @RequestMapping(value = "/findStoresByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据")
    public Object findStoresByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findStoresByUserId(userId);
        return toResult(list);
    }

    @RequestMapping(value = "/findStoreNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据:精简数据")
    public Object findStoreNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findStoresByUserId(userId);
        List<StoreDTO> dtoList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg po : list){
                StoreDTO dto = new StoreDTO();
                dto.setStoreId(po.getId());
                dto.setStoreName(po.getName());
                dto.setOrgPath(po.getIdPath());
                dtoList.add(dto);
            }
        }
        return toResult(dtoList);
    }

    @RequestMapping(value = "/findStoresByAreaIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区集合获取门店集合数据")
    public Object findStoresByAreaIds(@RequestBody @ApiParam("JSON参数") List<String> areaIds) {
        List<SysOrg> list = sysOrgService.findStoresByAreaIds(areaIds);
        return toResult(list);
    }

    @RequestMapping(value = "/findStoreNamesByAreaIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区集合获取门店集合数据:精简数据")
    public Object findStoreNamesByAreaIds(@RequestBody @ApiParam("JSON参数") List<String> areaIds) {
        List<SysOrg> list = sysOrgService.findStoresByAreaIds(areaIds);
        List<StoreDTO> dtoList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg po : list){
                StoreDTO dto = new StoreDTO();
                dto.setStoreId(po.getId());
                dto.setStoreName(po.getName());
                dto.setOrgPath(po.getIdPath());
                dtoList.add(dto);
            }
        }
        return toResult(dtoList);
    }

    @RequestMapping(value = "/findAreasAndStores", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区门店数据")
    public Object findAreasAndStores() {
        return findAreasAndStoresByUserId(null);
    }

    @RequestMapping(value = "/findAreaAndStoreNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区门店数据:精简数据")
    public Object findAreaAndStoreNames() {
        return findAreaAndStoreNamesByUserId(null);
    }

    @RequestMapping(value = "/findAreasAndStoresByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据")
    public Object findAreasAndStoresByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findAreasAndStoresByUserId(userId);
        return toResult(list);
    }

    @RequestMapping(value = "/findDeptsByEmpId", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定用户所属部门")
    public List<SysOrg> findDeptsByEmpId(@RequestParam("empId") String empId) {
        List<SysOrg> list = sysEmployeeOrgService.findDeptsByEmpId(empId);
        return list;
    }

    @RequestMapping(value = "/findAreaAndStoreNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据:精简数据")
    public Object findAreaAndStoreNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId) {
        List<SysOrg> list = sysOrgService.findAreasAndStoresByUserId(userId);
        List<AreaDTO> dtoList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg po : list){
                AreaDTO dto = new AreaDTO();
                dto.setAreaId(po.getId());
                dto.setAreaName(po.getName());
                dto.setOrgPath(po.getIdPath());
                if (BlankUtil.isNotEmpty(po.getChildren())){
                    List<StoreDTO> sList = Lists.newArrayList();
                    for (SysOrg org : po.getChildren()){
                        StoreDTO sDto = new StoreDTO();
                        sDto.setStoreId(org.getId());
                        sDto.setStoreName(org.getName());
                        sDto.setOrgPath(org.getIdPath());
                        sList.add(sDto);
                    }
                    dto.setChildren(sList);
                }
                dtoList.add(dto);
            }
        }
        return toResult(dtoList);
    }

    @RequestMapping(value = "/findAreaAndStoreNamesCascade", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据:精简数据-联动")
    public Object findAreaAndStoreNamesCascade() {
        List<SysOrg> list = sysOrgService.findAreasAndStoresByUserId(null);
        List<AreaDTO> dtoList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(list)){
            for (SysOrg po : list){
                AreaDTO dto = new AreaDTO();
                dto.setAreaId(po.getId());
                dto.setAreaName(po.getName());
                dto.setOrgPath(po.getIdPath());
                if (BlankUtil.isNotEmpty(po.getChildren())){
                    List<AreaDTO> sList = Lists.newArrayList();
                    for (SysOrg org : po.getChildren()){
                        AreaDTO sDto = new AreaDTO();
                        sDto.setAreaId(org.getId());
                        sDto.setAreaName(org.getName());
                        sDto.setOrgPath(org.getIdPath());
                        sList.add(sDto);
                    }
                    dto.setChildrenStore(sList);
                }
                dtoList.add(dto);
            }
        }
        return toResult(dtoList);
    }


    @RequestMapping(value = "/findOrgsByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgsByIds(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        Map<String,SysOrg> map = new HashMap<>();
        SysOrgVo condition = new SysOrgVo();
        condition.setIdIn(ids);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysOrg> orgList = sysOrgService.findOrgsByIds(condition);
        if (BlankUtil.isNotEmpty(orgList)){
            for (SysOrg org : orgList){
                map.put(org.getId(),org);
            }
        }
        return toResult(map);
    }

    @RequestMapping(value = "/findOrgNamesByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgNamesByIds(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        Map<String,String> map = new HashMap<>();
        SysOrgVo condition = new SysOrgVo();
        condition.setIdIn(ids);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysOrg> orgList = sysOrgService.findOrgsByIds(condition);
        if (BlankUtil.isNotEmpty(orgList)){
            for (SysOrg org : orgList){
                map.put(org.getId(),org.getName());
            }
        }
        return toResult(map);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 对象")
    public Object findOne(@RequestBody @ApiParam("JSON参数")String id) {
        return toResult(sysOrgService.selectByPrimaryKey(id));
    }

    @RequestMapping(value = "/findOneVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 拓展对象")
    public Object findOneVo(@RequestBody @ApiParam("JSON参数")String id) {
        SysOrgVo condition = new SysOrgVo();
        condition.setId(id);
        List<SysOrgVo> list = sysOrgService.findVoByCondition(condition);
        if(BlankUtil.isNotEmpty(list)){
            SysOrgVo vo = list.get(0);
            if(BlankUtil.isNotEmpty(vo)){
                if(BlankUtil.isNotEmpty(vo.getStoreCode())) {
                    vo.setName(vo.getName() + "(" + vo.getStoreCode() + ")");
                }
            }
            return toResult(list.get(0));
        }
        return toResult();
    }

    @RequestMapping(value = "/findAllVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 拓展对象")
    public Object findOneVo(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        SysOrgVo condition = new SysOrgVo();
        condition.setIdIn(ids);
        List<SysOrgVo> list = sysOrgService.findVoByCondition(condition);
        if(BlankUtil.isNotEmpty(list)){
            for(SysOrgVo vo:list){
                if(BlankUtil.isNotEmpty(vo.getStoreCode())){
                    vo.setName(vo.getName() + "(" + vo.getStoreCode() + ")");
                }
            }
        }
        return toResult(list);
    }

    @RequestMapping(value = "/findUserOrgs", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录用户片区候选值：车务模块列表查询使用，片区人员仅返回该片区，非片区人员返回该驾校下所有的组织")
    public Object findUserOrgsList(@RequestBody(required = false) SysBaseDTO param) {
        Object object = sysOrgService.findUserOrgsList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findUserOrgsWithInsert", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录用户片区候选值：车务模块列表新增编辑录入片区使用，返回登录人驾校下所有的组织")
    public Object findUserOrgsWithInsert() {
        Object object = sysOrgService.findUserOrgsWithInsert();
        return toResult(object);
    }
    @RequestMapping(value = "/batchUpdateCache", method = RequestMethod.POST)
    @ApiOperation(value = " 批量更新机构缓存和缓存版本")
    public Object batchUpdateCache(@RequestBody List<SysOrg> data) {
        sysOrgService.batchUpdateCache(data);
        return toResult();
    }
    @RequestMapping(value = "/findSysOrgByThirdPartyIds", method = RequestMethod.POST)
    @ApiOperation(value = " 根据第三方ids获取组织")
    public Object findSysOrgByThirdPartyIds(@RequestBody List<String> thirdPartyIds) {

        return toResult(sysOrgService.findSysOrgByThirdPartyIds(thirdPartyIds));
    }

    @RequestMapping(value = "/findOrgIdByName", method = RequestMethod.GET)
    @ApiOperation(value = "根据名称获取组织ID：若找出一条则返回名称，无或多条返回空字符串")
    public Object findOrgIdByName(@RequestParam(value = "name") String name) {
        return toResult(sysOrgService.findOrgIdByName(name));
    }
}