package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.vo.PageResourceDTO;
import com.sida.dcloud.auth.vo.ResourceItemDTO;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.system.common.CollectorConstants;
import com.sida.dcloud.system.common.dto.CollectorDirectory;
import com.sida.dcloud.system.dao.*;
import com.sida.dcloud.system.service.SysResourceService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.common.util.PinYinUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.security.MessageDigest;
import java.util.*;

@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {
    private static final Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysButtonMapper sysButtonMapper;
    @Autowired
    private SysFieldMapper sysFieldMapper;
    @Autowired
    private SysUserHiddenFieldMapper sysUserHiddenFieldMapper;

    private Map<String, String> codes = Maps.newConcurrentMap();

    @Override
    public IMybatisDao<SysResource> getBaseDao() {
        return sysResourceMapper;
    }

    @Override
    public List<SysResourceVo> findVoListByRoleId(String roleId) {
        List<SysResourceVo> voList = Lists.newArrayList();

        //获取所有资源列表（type='page' 和 type='button' ）
        List<String> typeList = Lists.newArrayList();
        typeList.add(SysEnums.ResourceTypeEnums.PAGE.getName());
        typeList.add(SysEnums.ResourceTypeEnums.BUTTON.getName());
        //id字段使用path值
        List<SysResource> resList = sysResourceMapper.selectByTypeIn(typeList);

        Map<String,SysResource> resMap = new HashMap<>();
        for (SysResource res : resList){
            resMap.put(res.getId(),res);
        }

        //根据角色id获取已配置的资源
        SysRoleResource condition = new SysRoleResource();
        condition.setRoleId(roleId);
        List<SysRoleResource> roleResourceList = sysRoleResourceMapper.selectByCondition(condition);

        Map<String,Boolean> isExistChildren = new HashMap<>();

        //遍历获取已配置的资源id Map
        Map<String,SysResource> roleResourceMap = new HashMap<>();
        for (SysRoleResource roleResource : roleResourceList){
            roleResourceMap.put(roleResource.getResourceId(),resMap.get(roleResource.getResourceId()));
            SysResource temp = resMap.get(roleResource.getResourceId());
            if ( temp != null && StringUtils.isNotBlank(temp.getParentId())
                    && !isExistChildren.containsKey(temp.getParentId())){
                isExistChildren.put(temp.getParentId(),true);
            }
        }

        Map<String,String> parentIdMap = new HashMap<>();
        /*手动屏蔽一些查询按钮*/
        List<String> codeList = new ArrayList<String>(){{
            add("201806131951454d8835d7232a40e290435e83bda72084");
            add("20180613202256c2179ecbf4634e69b0a6da901741feb2");
            add("20180613202629b524382e4bb84850acc031958bc601eb");
            add("20180614101635b8d15c0558c74b1c97c95948966dbc66");
            add("20180614102538e90c5f9045484be584b2e09bcf21629d");
            add("2018061411153191f4bb9aec804e039fe27d98446976cc");
            add("20180614115028655b2af92d08467ea4bb3be7e85462d0");
            add("20180614115350f549cfa99fa84db4a35b455801693d3e");
            add("201806141331323f8f026f388b424c83102e22a1dfa383");
            add("2018061413371856b202bd9cc2467687c2992c8860f058");
            add("201806141345210db40999e51c4259925379f6f7a6d2bf");
            add("20180614135617773c2dd228934d97858409af5691da64");
            add("201806141015530b4da5ff0ba14103857951e2684881ac");
        }};
        for (SysResource res : resList){
            //如果不是顶层菜单 && 属于按钮层级 && parentId未创建过默认节点

            if (StringUtils.isNotBlank(res.getParentId()) && SysEnums.ResourceTypeEnums.BUTTON.getName().equals(res.getType())
                    && !parentIdMap.containsKey(res.getParentId())){
                /*手动屏蔽一些查询按钮*/
                SysResourceVo defaultVo = new SysResourceVo();
                defaultVo.setId(SecConstant.DEFAULT_+res.getParentId());
                defaultVo.setName("查询");
                defaultVo.setCode("disableAll");
                defaultVo.setParentId(res.getParentId());
                defaultVo.setSort(0);
                defaultVo.setValue("value");
                defaultVo.setType(SysEnums.ResourceTypeEnums.BUTTON.getName());
                defaultVo.setIdentifier(SecConstant.NO);
                if (roleResourceMap.containsKey(res.getParentId())){
                    defaultVo.setIdentifier(SecConstant.YES);
                }
                parentIdMap.put(res.getParentId(),res.getName());
                if (!codeList.contains(res.getParentId())){
                    voList.add(defaultVo);
                }
            }

            SysResourceVo vo = new SysResourceVo();
            BeanUtils.copyProperties(res,vo);
            if (roleResourceMap.containsKey(res.getId())){
                vo.setIdentifier(SecConstant.YES);
            }else {
                vo.setIdentifier(SecConstant.NO);
            }
            voList.add(vo);
        }
        return voList;
    }

    @Override
    @Transactional
    public int saveRoleResource(String roleId, List<String> resourceIds) {
        if (resourceIds!=null){
            //逻辑删除所有菜单、按钮权限
            List<String> typeList = Lists.newArrayList();
            typeList.add(SysEnums.ResourceTypeEnums.PAGE.getName());
            typeList.add(SysEnums.ResourceTypeEnums.BUTTON.getName());
            sysRoleResourceMapper.deleteByRoleIdAndType(roleId,typeList);

            List<String> parentIdList = Lists.newArrayList();
            //根据前端返回ids(前端只返回根节点id，只有当一个父节点下全部子节点被选中该父节点才会有id返回到后端，所以要后端默认给选中的节点添加上父级id)
            //添加上菜单、按钮权限

            Iterator<String> it = resourceIds.iterator();
            while(it.hasNext()){
                String resourceId = it.next();
                if (resourceId.startsWith(SecConstant.DEFAULT_)){
                    parentIdList.add(resourceId.replace(SecConstant.DEFAULT_,""));
                    it.remove();
                }
            }

            resourceIds.addAll(parentIdList);

            if (BlankUtil.isNotEmpty(resourceIds) && resourceIds.size() > 0){
                List<SysRoleResource> rrList = Lists.newArrayList();
                List<String> resIdList = Lists.newArrayList();
                List<SysResource> resList = sysResourceMapper.selectByIds(resourceIds);
                for (SysResource res : resList){
                    String[] str = res.getPath().split("\\.");
                    for(int i = 0; i < str.length; i++){
                        if (!resIdList.contains(str[i])){
                            SysRoleResource rr = new SysRoleResource();
                            resIdList.add(str[i]);
                            rr.setRoleId(roleId);
                            rr.setResourceId(str[i]);
                            rrList.add(rr);
                        }
                    }
                }
                if (rrList.size()>0){
                    sysRoleResourceMapper.addManyRoleResource(rrList);
                }
            }
        }
        return 1;
    }

    private void getRoleResList(List<SysRoleResource> roleResList, String roleId, List<SysResourceVo> resources) {
        if (resources==null || resources.size()<=0){
            return;
        }
        for (SysResourceVo vo : resources){
            if (SecConstant.YES.equals(vo.getIdentifier())){
                SysRoleResource rr = new SysRoleResource();
                rr.setRoleId(roleId);
                rr.setResourceId(vo.getId());
                roleResList.add(rr);
            }
            getRoleResList(roleResList,roleId,vo.getChildren());
        }
    }


    @Override
    @Transactional
    public int insertAnotherInfoByType(SysResourceVo resource, String type) {
        //保存菜单
        if (SysEnums.ResourceTypeEnums.PAGE.getName().equals(type)){
            SysMenu menu = new SysMenu();
            BeanUtils.copyProperties(resource,menu);
            menu.setSortOrder(resource.getSort());
            return sysMenuMapper.insertSelective(menu);
        }

        //保存按钮
        else if(SysEnums.ResourceTypeEnums.BUTTON.getName().equals(type)){
            SysButton button = new SysButton();
            BeanUtils.copyProperties(resource,button);
            button.setResourceId(resource.getId());
            button.setPageCode(resource.getParentCode());
            button.setSortOrder(resource.getSort());
            return sysButtonMapper.insertSelective(button);
        }

        //保存字段
        else if (SysEnums.ResourceTypeEnums.FIELD.getName().equals(type)){
            SysField field = new SysField();
            BeanUtils.copyProperties(resource,field);
            field.setResourceId(resource.getId());
            field.setPageCode(resource.getParentCode());
            field.setSortOrder(resource.getSort());
            return sysFieldMapper.insertSelective(field);
        }

        else{
            return -1;
        }
    }

    @Override
    public int deleteById(String id) {
        return sysResourceMapper.deleteById(id);
    }


    @Override
    public List<SysResourceVo> findListByRoleCode(String roleId,String roleCode) {
        List<SysResourceVo> list = sysResourceMapper.findListByRoleCode(roleId,roleCode);
        if (list!=null && list.size()>0){
            for (SysResourceVo vo : list){
                if (StringUtils.isNotBlank(vo.getRoleIds())){
                    String[] ids = vo.getRoleIds().split(",");
                    vo.setRoleIdList(Arrays.asList(ids));
                }
                if (StringUtils.isNotBlank(vo.getRoleNames())){
                    String[] names = vo.getRoleNames().split(",");
                    vo.setRoleNameList(Arrays.asList(names));
                }
            }
        }
        return list;
    }

    @Override
    public PageResourceDTO findPageResourceMapByPageCode(String pageCode, String userId) {
        PageResourceDTO result = new PageResourceDTO();

        List<ResourceItemDTO> buttonDTOs = Lists.newArrayList();
        List<ResourceItemDTO> fieldDTOs = Lists.newArrayList();
        List<ResourceItemDTO> userButtonDTOs = Lists.newArrayList();
        List<ResourceItemDTO> userFieldDTOs = Lists.newArrayList();
        Map<String,Boolean> userButtonCodes = new HashMap<>();
        List<String> userFieldNames = Lists.newArrayList();
        Map<String,SysResource> userButtonsMap = new HashMap<>();
        Map<String,SysResource> userFieldsMap = new HashMap<>();

        if (StringUtils.isNotBlank(userId)){
            userButtonsMap = this.selectUserButtons(pageCode,userId);//用户具有权限按钮资源
            userFieldsMap = this.findUserFields(pageCode,userId);//用户具有权限字段资源
        }

        SysResource buttonRes;
        for (Map.Entry<String, SysResource> entry : userButtonsMap.entrySet()) {
            ResourceItemDTO dto = new ResourceItemDTO();
            buttonRes = entry.getValue();
            dto.setName(buttonRes.getName());
            dto.setCode(buttonRes.getCode());
            userButtonDTOs.add(dto);
        }

        List<SysButton> buttons = sysButtonMapper.findByPageCode(pageCode);
        if (buttons!=null && buttons.size()>0){
            for (SysButton btn : buttons){
                ResourceItemDTO dto = new ResourceItemDTO();
                dto.setName(btn.getName());
                dto.setCode(btn.getCode());
                //默认显示。如果没有该用户的权限集里边，需要控制隐藏
                if (!userButtonsMap.containsKey(btn.getId())){
                    dto.setHiddenFlag(true);
                    userButtonCodes.put(btn.getCode(),false);
                }else {
                    userButtonCodes.put(btn.getCode(),true);
                }
                buttonDTOs.add(dto);
            }
        }
        List<SysField> fields = sysFieldMapper.findByPageCode(pageCode);
        if (fields!=null && fields.size()>0){
            for (SysField field : fields){
                ResourceItemDTO dto = new ResourceItemDTO();
                dto.setName(field.getName());
                dto.setCode(field.getCode());
                //默认显示。如果在该用户配置的隐藏列里边有该字段，需要控制隐藏
                if (userFieldsMap.containsKey(field.getId())){
                    dto.setHiddenFlag(true);
                }
                fieldDTOs.add(dto);
            }
        }

        if (fieldDTOs.size() > 0){
            for (ResourceItemDTO res : fieldDTOs){
                if (!res.isHiddenFlag()){
                    ResourceItemDTO dto = new ResourceItemDTO();
                    dto.setName(res.getName());
                    dto.setCode(res.getCode());
                    userFieldNames.add(res.getName());
                    userFieldDTOs.add(dto);
                }
            }
        }

        result.setButtonList(buttonDTOs);
        result.setFieldList(fieldDTOs);
        result.setUserButton(userButtonDTOs);
        result.setUserField(userFieldDTOs);
        result.setUserButtonCodes(userButtonCodes);
        result.setUserFieldNames(userFieldNames);
        return result;
    }

    @Override
    public Map<String, SysResource> selectUserButtons(String pageCode, String userId) {
        Map<String, SysResource> map = new HashMap<>();
        List<SysResource> resList = sysResourceMapper.findUserButtons(pageCode, userId);
        if (resList != null && resList.size() > 0) {
            for (SysResource res : resList) {
                map.put(res.getId(),res);
            }
        }
        return map;
    }

    @Override
    public Map<String, SysResource> findUserFields(String pageCode, String userId) {
        Map<String,SysResource> map = new HashMap<>();
        List<SysResource> resList = sysResourceMapper.findUserFields(pageCode,userId);
        if (resList !=null && resList.size() > 0){
            for (SysResource res : resList){
                map.put(res.getId(),res);
            }
        }
        return map;
    }

    @Override
    @Transactional
    public void init() {
        //获取驱动路径
//        System.setProperty(CollectorConstants.DRIVERPATH_KEY, CollectorConstants.DRIVERPATH_VALUE);
        System.setProperty(CollectorConstants.DRIVERPATH_KEY, "F:/plugins/phantomjs.exe");
        //获取驱动
        WebDriver webDriver = new PhantomJSDriver();
//        DriverPool driverPool;
//        ForkJoinPool forkJoinPool = new ForkJoinPool();

        try {
            //登录系统
            loginSystem(webDriver);

            //获取目录信息
            List<CollectorDirectory> directories = getDirectoryMes(webDriver);

            //定义对象用于存放资源
            List<SysResourceVo> resList = Lists.newArrayList();

            //组装成资源对象
            putResource(resList,directories,null,1927);

            for (SysResourceVo resource : resList){
                this.insertSelective(resource);
                this.insertAnotherInfoByType(resource,resource.getType());
            }


            //新建线程池
//            driverPool = new DriverPool(10);
//
//            Future<Map> mapFuture = forkJoinPool.submit(
//                    new CollectorThread(0,codes.size(),driverPool,codes)
//            );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ErrorCodeEnums.COLLERTOR.getCode(),ErrorCodeEnums.COLLERTOR.getMessage());
        }
    }

    /**
     * 登录系统
     * @param webDriver
     * @throws InterruptedException
     */
    private void loginSystem(WebDriver webDriver) throws InterruptedException {

        //请求登录页
//        webDriver.get(CollectorConstants.LOGIN_PATH);
        webDriver.get("http://localhost:9530/");
//        //取屏幕截图
        webDriver.navigate().refresh();

        addImage("screenshot",webDriver);

        //获取“账号文本框”、“密码文本框”及“登录按钮”
        WebElement account = webDriver.findElement(By.name("username"));
        WebElement password = webDriver.findElement(By.name(CollectorConstants.PASSWORD_NAME));
        WebElement button = webDriver.findElement(By.tagName(CollectorConstants.LOGIN_BUTTON_TAG));
        WebElement imageCode = webDriver.findElement(By.xpath("//*[@id=\"loginBox\"]/div[2]/div[2]/form/div[3]/div/div/div/div/input"));

        //模拟登录行为
        account.clear();
        account.sendKeys("szsgAdmin");
        password.clear();
        password.sendKeys("123456");
        imageCode.clear();
        imageCode.sendKeys("1");
        addImage("login",webDriver);
        button.click();
        Thread.sleep(CollectorConstants.LONGSLEEPTIME);

        addImage("homepage",webDriver);
    }


    private void addImage(String str,WebDriver webDriver){
        try {
            File srcFile = ((TakesScreenshot)webDriver).
                    getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile
                    (srcFile,new File("d://"+str+".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目录信息
     * @param webDriver
     * @return
     * @throws Exception
     */
    private List<CollectorDirectory> getDirectoryMes(WebDriver webDriver) throws Exception{

        List<CollectorDirectory> menus = Lists.newArrayList();

        //获取顶部及侧边栏的菜单项
        //根据class获取顶部一级菜单项
        WebElement headerElement = webDriver.findElement(By.className(CollectorConstants.HEADER_CLASS));
        List<WebElement> menuElements = headerElement.findElements(By.className(CollectorConstants.EL_MENU_ITEM_CLASS));

        //遍历获取子级菜单
        for(int i = 0; i < menuElements.size(); i++) {
            WebElement element = menuElements.get(6);
            CollectorDirectory menu = new CollectorDirectory();
            List<CollectorDirectory> childs = Lists.newLinkedList();
            element.click();
//            Thread.sleep(CollectorConstants.LONGSLEEPTIME);
            List<WebElement> singleElements = webDriver.findElements(By.xpath(CollectorConstants.ASIDE_DIV_A));
            List<WebElement> multiElements = webDriver.findElements(By.xpath(CollectorConstants.ASIDE_DIV_LI));
            //递归获取子级菜单
            addDirectoryMes(childs,singleElements,multiElements,webDriver,null);
            menu.setDirectory(true);
            menu.setName(element.getText());
            menu.setCode(element.getAttribute(CollectorConstants.ATTR_ID));
            menu.setDirectoryList(childs);
            menu.setType(SysEnums.ResourceTypeEnums.PAGE);
            menus.add(menu);
            break;

//            Thread thread = new HandleMenuThread(menuElements.get(i),webDriver,menus);
//            thread.run();
        }

        return menus;
    }

    public class HandleMenuThread extends Thread{

        private WebElement element;
        private WebDriver webDriver;
        private List<CollectorDirectory> menus;

        public HandleMenuThread(WebElement element,WebDriver webDriver,List<CollectorDirectory> menus){
            this.element = element;
            this.webDriver = webDriver;
            this.menus = menus;
        }

        @Override
        public void run()
        {
            try {
                CollectorDirectory menu = new CollectorDirectory();
                List<CollectorDirectory> childs = Lists.newLinkedList();

                element.click();
                Thread.sleep(CollectorConstants.LONGSLEEPTIME);

                List<WebElement> singleElements = webDriver.findElements(By.xpath(CollectorConstants.ASIDE_DIV_A));
                List<WebElement> multiElements = webDriver.findElements(By.xpath(CollectorConstants.ASIDE_DIV_LI));
                //递归获取子级菜单
                addDirectoryMes(childs,singleElements,multiElements,webDriver,null);

                menu.setDirectory(true);
                menu.setName(element.getText());
                menu.setCode(element.getAttribute(CollectorConstants.ATTR_ID));
                menu.setDirectoryList(childs);
                menu.setType(SysEnums.ResourceTypeEnums.PAGE);

                menus.add(menu);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 组装资源对象
     * @param resList
     * @param directories
     * @param parent
     */
    private Integer putResource(List<SysResourceVo> resList,List<CollectorDirectory> directories, SysResource parent,Integer sort) {
        for (CollectorDirectory cd : directories){
            sort = sort + 1;
            //遍历获取子节点数据
            String parentId = parent==null?null:parent.getParentId();
            String parentCode = parent==null?null:parent.getCode();
            SysResourceVo resource = new SysResourceVo(cd.getName(),cd.getCode(),cd.getHref(),parentId,
                                                sort,cd.getUrl(),cd.getType().getName());
            resource.setParentCode(parentCode);
            addDefault(resource,parent);
            if (cd.getDirectoryList()!=null && cd.getDirectoryList().size()>0){
                sort = putResource(resList,cd.getDirectoryList(),resource,resource.getSort());
            }
            resList.add(resource);
        }
        return sort;
    }

    /**
     * 获取侧边栏菜单
     * @param collectorDirectories
     * @param singleElements
     * @param multiElements
     * @throws Exception
     */
    private void addDirectoryMes(List<CollectorDirectory> collectorDirectories,
                                 List<WebElement> singleElements,
                                 List<WebElement> multiElements,
                                 WebDriver webDriver,
                                 Integer testNumber) throws Exception{

        //加密所需
        MessageDigest messageDigest = MessageDigest.getInstance(CollectorConstants.MD5);
        BASE64Encoder base64Encoder = new BASE64Encoder();

        //遍历叶子菜单，添加到上级菜单下
        for(WebElement single : singleElements) {
            CollectorDirectory leafDirectory = new CollectorDirectory();
            List<CollectorDirectory> buttons = Lists.newArrayList();
            List<CollectorDirectory> fields = Lists.newArrayList();

            WebElement singleLi = single.findElement(By.xpath(CollectorConstants.XPATH_CURRENT_LI));
            WebElement singleSpan;
            try {
                singleSpan = single.findElement(By.xpath(CollectorConstants.A_LI_SPAN));

                leafDirectory.setDirectory(false);
                leafDirectory.setHref(single.getAttribute(CollectorConstants.HREF));
                if (StringUtils.isNotBlank(leafDirectory.getHref())){
                    leafDirectory.setHref(leafDirectory.getHref().replaceAll("http://(.*?[^/])/",""));
                }
                leafDirectory.setName(singleSpan.getAttribute(CollectorConstants.INNERHTML));
                leafDirectory.setCode(singleLi.getAttribute(CollectorConstants.ATTR_ID));
                //加密href并生成code
                leafDirectory.setEncode(getDirectoryCode(leafDirectory.getHref(),messageDigest,base64Encoder));
            }catch (Exception e){

                singleSpan = single.findElement(By.xpath("./li"));
                leafDirectory.setDirectory(false);
                leafDirectory.setHref(single.getAttribute(CollectorConstants.HREF));
                if (StringUtils.isNotBlank(leafDirectory.getHref())){
                    leafDirectory.setHref(leafDirectory.getHref().replaceAll("http://(.*?[^/])/",""));
                }
                leafDirectory.setName(singleSpan.getAttribute("innerHTML"));
                leafDirectory.setCode(PinYinUtil.getFirstSpell(leafDirectory.getName()));
                //加密href并生成code
                leafDirectory.setEncode(getDirectoryCode(leafDirectory.getHref(),messageDigest,base64Encoder));
            }


            //模拟点击菜单，获取下属按钮及字段
            System.out.println("============================="+leafDirectory.getName());
            if (ClickLeafA(buttons,fields,singleSpan,webDriver,leafDirectory, null)){
                leafDirectory.setDirectory(true);
            }else {
                buttons.addAll(fields);
                if (BlankUtil.isEmpty(leafDirectory.getDirectoryList())){
                    leafDirectory.setDirectoryList(buttons);
                }
            }
            leafDirectory.setType(SysEnums.ResourceTypeEnums.PAGE);
            collectorDirectories.add(leafDirectory);
        }

        //遍历树菜单，添加到上级菜单中
        List<WebElement> oneElements;
        List<WebElement> manyElements;
        for(WebElement multi : multiElements) {
            if (testNumber!=null){
                multi = multiElements.get(testNumber);
            }
            CollectorDirectory treeDirectory = new CollectorDirectory();

            List<CollectorDirectory> directories = Lists.newArrayList();
            //本身
            WebElement titleElement;
            try{
                titleElement = multi.findElement(By.xpath(CollectorConstants.LI_DIV_SPAN));
                treeDirectory.setDirectory(true);
                treeDirectory.setCode(multi.getAttribute(CollectorConstants.ATTR_ID));
                treeDirectory.setName(titleElement.getAttribute(CollectorConstants.INNERHTML));
            }catch (Exception e){
                titleElement = multi.findElement(By.xpath("./div"));
                treeDirectory.setDirectory(true);
                treeDirectory.setName(titleElement.getText());
            }


            //解析子菜单
            manyElements = Lists.newArrayList();
            oneElements = multi.findElements(By.xpath("./ul/div/a"));
            manyElements = multi.findElements(By.xpath("./ul/div/li"));
//            if (oneElements==null || oneElements.size()<=0){
//                oneElements = multi.findElements(By.xpath(CollectorConstants.LI_UL_DIV_DIV_A));
//                manyElements = multi.findElements(By.xpath(CollectorConstants.LI_UL_DIV_DIV_LI));
//            }
            addDirectoryMes(directories,oneElements,manyElements,webDriver,null);
            treeDirectory.setDirectoryList(directories);
            treeDirectory.setType(SysEnums.ResourceTypeEnums.PAGE);
            collectorDirectories.add(treeDirectory);
            if (testNumber!=null){
                break;
            }
        }
    }

    private Boolean ClickLeafA(List<CollectorDirectory> buttons, List<CollectorDirectory> fields, WebElement single,WebDriver webDriver, CollectorDirectory parent, Integer num) throws Exception{
        //点击a标签
        if (single != null){
            ((JavascriptExecutor)webDriver).executeScript(CollectorConstants.ARGUMENTS_ZERO, single);
//        Thread.sleep(CollectorConstants.LONGSLEEPTIME);
        }

        List<WebElement> buttonList = Lists.newArrayList();
        List<WebElement> fieldList = Lists.newArrayList();
        try {

            //如果存在四级菜单，则先抓取四级菜单
            if (single != null){
                try {
                    //四级菜单
                    List<WebElement> levelFourList = webDriver.findElements(By.className("el-tabs__nav-scroll")).get(1)
                            .findElement(By.className("el-tabs__nav")).findElements(By.xpath("./div"));
                    List<CollectorDirectory> fourObject = Lists.newArrayList();
                    int i = 0;
                    for (WebElement four : levelFourList){
                        four.click();
                        CollectorDirectory leafDirectory = new CollectorDirectory();
                        List<CollectorDirectory> fourbuttons = Lists.newArrayList();
                        List<CollectorDirectory> fourfields = Lists.newArrayList();
                        leafDirectory.setDirectory(false);
                        leafDirectory.setName(four.getText());
                        leafDirectory.setCode(PinYinUtil.getFirstSpell(leafDirectory.getName()));

                        //模拟点击菜单，获取下属按钮及字段
                        System.out.println("============================="+leafDirectory.getName());
                        ClickLeafA(fourbuttons,fourfields,null,webDriver,null,i);
                        i = i+1;
                        fourbuttons.addAll(fourfields);
                        leafDirectory.setDirectoryList(fourbuttons);
                        leafDirectory.setType(SysEnums.ResourceTypeEnums.PAGE);
                        fourObject.add(leafDirectory);
                    }
                    if (parent != null){
                        parent.setDirectoryList(fourObject);
                    }
                }catch (Exception e){

                }
            }

            //获取按钮的form表单
            List<WebElement> selectionList = webDriver.findElement(By.className("columns")).findElement(By.xpath("./div/div[@class='el-tabs__content']"))
                                        .findElements(By.xpath("./div"));
            WebElement lastElement = selectionList.get(selectionList.size() -1);


            //获取按钮字段的另一种方式
            if (num!=null){
                addImage("fourLevel",webDriver);
                WebElement formElement = lastElement.findElement(By.className("el-tabs__content")).findElements(By.className("el-tab-pane")).get(num);
                try{
                    WebElement formElement1 = formElement.findElement(By.className(CollectorConstants.CLASS_BTN_CONTENT));
                    buttonList = formElement1.findElements(By.xpath(CollectorConstants.XPATH_CURRENT_FORM_BUTTON));
                }catch (Exception e) {
                }
                //获取字段的form表单
                WebElement fieldForm = formElement.findElement(By.className("el-table__header-wrapper"));
                fieldList = fieldForm.findElements(By.xpath("./table/thead/tr/th"));

            }else {
                try{
                    WebElement formElement = lastElement.findElement(By.className(CollectorConstants.CLASS_BTN_CONTENT));
                    buttonList = formElement.findElements(By.xpath(CollectorConstants.XPATH_CURRENT_FORM_BUTTON));

                    //获取字段的form表单
                    WebElement fieldForm = formElement.findElement(By.xpath(CollectorConstants.XPATH_CURRENT_FOLLWING_DIV_ONE));
                    fieldList = fieldForm.findElements(By.xpath(CollectorConstants.XPATH_CURRENT_TABLE_TH));
                }catch (Exception e){

                    Thread.sleep(CollectorConstants.LONGSLEEPTIME);
                    addImage("button3",webDriver);
                    //另一种方式
                    WebElement formElement = lastElement.findElement(By.className("btns"));
                    buttonList = formElement.findElements(By.tagName("button"));
                    //获取字段的form表单
                    WebElement fieldForm = lastElement.findElement(By.className("el-table__header-wrapper"));
                    fieldList = fieldForm.findElements(By.xpath("./table/thead/tr/th"));
                }
            }
        }catch (Exception e){
            return false;
        }

        for (WebElement btn : buttonList){
            CollectorDirectory btnCD = new CollectorDirectory();
            btnCD.setDirectory(false);
            //过滤弹出按钮
            if (CollectorConstants.POPOVERBTN.equals(btn.findElement(By.xpath(CollectorConstants.XPATH_PARENT)).getAttribute(CollectorConstants.ATTR_CLASS))){
                continue;
            }
            WebElement btnSpan = null;
            Boolean styleFlag = false;
            try{
                btnSpan = btn.findElement(By.xpath(CollectorConstants.XPATH_CURRENT_SPAN));
            }catch (Exception e){
                styleFlag = true;
            }
            //按钮可能采用新样式
            if (styleFlag){
                btnCD.setCode(btn.getAttribute("id"));
                btnCD.setName(btn.getAttribute("innerHTML"));
            }else {
                btnCD.setCode(btn.getAttribute(CollectorConstants.ATTR_ID));
                btnCD.setName(btnSpan.getAttribute(CollectorConstants.INNERHTML));
            }

            if (StringUtils.isNotBlank(btnCD.getName())){
                btnCD.setName(btnCD.getName().trim());
            }else {
                continue;
            }
            btnCD.setType(SysEnums.ResourceTypeEnums.BUTTON);
            buttons.add(btnCD);
        }

        for (WebElement fld : fieldList){
            CollectorDirectory fldCD = new CollectorDirectory();
            fldCD.setDirectory(false);
            try {
                WebElement filDiv = fld.findElement(By.xpath(CollectorConstants.XPATH_CURRENT_DIV));
                try {
                    //过滤checkbox
                    fld.findElement(By.className(CollectorConstants.CLASS_EL_CHECKBOX));
                    continue;
                }catch (Exception e){

                }
                fldCD.setName(filDiv.getAttribute(CollectorConstants.INNERHTML));
                if (StringUtils.isNotBlank(fldCD.getName())){
                    //替换列头里边的排序控件为空
                    fldCD.setName(fldCD.getName().replaceAll("<span.*</span>","").trim());
                    //过滤序号列
                    if (fldCD.getName().equals("序号")){
                        continue;
                    }
                }else {
                    continue;
                }
                fldCD.setType(SysEnums.ResourceTypeEnums.FIELD);
                fields.add(fldCD);
            }catch (Exception e){
                continue;
            }
        }
        return false;
    }

    /**
     * 获取目录编码
     * @param url
     * @param messageDigest
     * @param base64Encoder
     * @return
     * @throws Exception
     */
    private String getDirectoryCode(String url,MessageDigest messageDigest,BASE64Encoder base64Encoder) throws Exception{

        String host = CollectorConstants.HOST_URL + url;
        return base64Encoder.encode(messageDigest.digest(host.getBytes(CollectorConstants.UTF_8)));
    }

    private void addDefault( SysResource res) {
        addDefault(res,null);
    }

    private void addDefault(SysResource res,SysResource parent) {
        PoDefaultValueGenerator.putDefaultValue(res);
        if (parent!=null){
            res.setParentId(parent.getId());
            res.setPath(parent.getPath() + res.getId() + ".");
        }else {
            res.setParentId(null);
            res.setPath(res.getId() + ".");
        }
    }
}