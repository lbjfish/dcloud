package com.sida.xiruo.xframework.controller;

import com.alibaba.fastjson.JSON;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.po.common.BaseDTO;
import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import com.sida.xiruo.xframework.common.Contants;
import com.sida.xiruo.xframework.exception.ControllerException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.UUID;
import com.sida.xiruo.xframework.vo.JsonResult;
import com.github.pagehelper.Page;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

;

/**
 * Created by Xiruo on 2017/7/20.
 */
public class BaseController {
    protected final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpSession session;

    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public static class ClzDateEditor extends PropertyEditorSupport {
        private String[] formatStringArr = new String[]{
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd"
        };

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = null;
            Date date = null;
            if (BlankUtil.isEmpty(text)) {
                return;
            }
            for (String f : formatStringArr) {
                format = new SimpleDateFormat(f);
                try {
                    if ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'".equals(f)) {
                        format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    }
                    date = format.parse(text);
                    setValue(date);
                    return;
                } catch (ParseException e) {
//                    do nothing , continue
                }
            }
            throw new IllegalArgumentException("日期解析错误: " + text);
        }
    }

        @InitBinder
    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, new ClzDateEditor());
    }

    protected JsonResult toResult() {
        return toResult(null, "操作成功", true, true);
    }

    protected JsonResult toResult(boolean encode) {
        return toResult(null, "操作成功", encode, true);
    }

    protected JsonResult toErrorResult(String message) {
//		return toResult(data, "操作成功");  //换成base64编码返回数据
        return toResult(null, message, true, false);
    }

    protected JsonResult toResult(Object data) {
//		return toResult(data, "操作成功");  //换成base64编码返回数据
        return toResult(data,"操作成功",true, true);
    }

    /**
     * 构建成功的DTO返回值
     * @param data
     * @param <T>
     * @return
     */
    protected <T> BaseDTO<List<T>> buildBaseListDTO(Object data, Class<T> clazz) {
        return buildBaseListDTO(data,true, clazz);
    }

    /**
     * 构建基础的DTO返回值
     * @param data
     * @param status
     * @param <T>
     * @return
     */
    protected <T> BaseDTO<List<T>> buildBaseListDTO(Object data, boolean status, Class<T> clazz) {
        BaseDTO<List<T>> result = new BaseDTO<>(status);
        //如果是分页需要设置总条数total
        if(data instanceof Page) {
            Page<T> page = (Page<T>) data;
            result.setTotal(page.getTotal());
            result.setData(page.getResult());
        } else if(data instanceof Collection) {
            Collection<T> list = (Collection<T>)data;
            result.setTotal((long) list.size());
            result.setData((List<T>)list);
        } else {
            result.setStatus(false);
            result.setCode(400);
            result.setMessage("返回类型异常！请与管理员联系");
        }
        return result;
    }


    /**
     * 构建成功的DTO返回值
     * @param data
     * @param <T>
     * @return
     */
    protected <T> BaseDTO<T> buildBaseDTO(T data) {
        return buildBaseDTO(data,true);
    }

    /**
     * 构建基础的DTO返回值
     * @param data
     * @param status
     * @param <T>
     * @return
     */
    protected <T> BaseDTO<T> buildBaseDTO(T data, boolean status) {
        BaseDTO<T> result = new BaseDTO<>(status);
        //如果是分页需要设置总条数total
        if(data instanceof Page) {
            result.setStatus(false);
            result.setCode(400);
            result.setMessage("返回类型异常！请与管理员联系");
        } else if(data instanceof Collection) {
            result.setStatus(false);
            result.setCode(400);
            result.setMessage("返回类型异常！请与管理员联系");
        } else {
            result.setTotal(1L);
            result.setData(data);
        }
        return result;
    }

    /**
     *
     * @param data
     * @param message
     * @return
     */
    protected JsonResult toResult(Object data, String message) {
        return toResult(data, message, true, true);
    }

    protected JsonResult toResult(Object data, String message, boolean encode) {
        return toResult(data, message, encode, true);
    }

    /**
     *
     * @param data
     * @param message
     * @return
     */
    protected JsonResult toResult(Object data, String message, boolean encode, boolean status) {
        Map<String, Object> result = new HashMap<String, Object>();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setEncode(encode);
        jsonResult.setStatus(status);
        //如果是分页需要设置总条数total
        if(data instanceof Page) {
            Page page = (Page) data;
            jsonResult.setTotal(page.getTotal());
            jsonResult.setData(page.getResult());
        } else if(data instanceof Collection) {
            Collection list = (Collection)data;
            jsonResult.setTotal(list.size());
            jsonResult.setData(list);
        } else if(data instanceof Page) {
            Page page = (Page)data;
            jsonResult.setTotal(page.getTotal());
            jsonResult.setData(page.getResult());
        } else {
            jsonResult.setTotal(1);
            jsonResult.setData(data);
        }
        jsonResult.setMessage(message);
        return jsonResult;
    }

    /**
     *
     * @param data
     * @param message
     * @return
     */
    protected Object toResult(Object data, String message, String code, boolean encode, boolean status) {
        Map<String, Object> result = new HashMap<String, Object>();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setEncode(encode);
        jsonResult.setCode(code);
        jsonResult.setStatus(status);
        //如果是分页需要设置总条数total
        if(data instanceof Page) {
            Page page = (Page) data;
            jsonResult.setTotal(page.getTotal());
            jsonResult.setData(page.getResult());
        } else if(data instanceof Collection) {
            Collection list = (Collection)data;
            jsonResult.setTotal(list.size());
            jsonResult.setData(list);
        } else if(data instanceof Page) {
            Page page = (Page)data;
            jsonResult.setTotal(page.getTotal());
            jsonResult.setData(page.getResult());
        } else {
            jsonResult.setTotal(1);
            jsonResult.setData(data);
        }
        jsonResult.setMessage(message);
        return jsonResult;
    }


    /**
     * 返回base64位数据
     * @param data
     * @param message
     * @return
     */
    protected JsonResult toJsonBase64Result(Object data, String message){
        Map<String, Object> result = new HashMap<String, Object>();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(true);
        Object resultData=new Object();
        //如果是分页需要设置总条数total
        //如果是分页需要设置总条数total
        if(data instanceof Page) {
            Page page = (Page) data;
            jsonResult.setTotal(page.getTotal());
            resultData=page.getResult();
        } else if(data instanceof Collection) {
            Collection list = (Collection)data;
            jsonResult.setTotal(list.size());
            resultData=list;
        } else {
            jsonResult.setTotal(1);
            resultData=data;
        }
        try{
            String baseStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(JSON.toJSONString(resultData).getBytes("UTF-8")));
            jsonResult.setData(baseStr);
        }catch (Exception e){
            throw new ControllerException("0001","数据转换异常");
        }
        jsonResult.setMessage(message);
        return jsonResult;
    }




    /**
     * 说明：将实体bean转成Map,并在key前增加prefix对象名前缀
     * {
     * 	"org.id":"100",
     * 	"org.name":"分公司1"
     * }
     * created by huangbaidong 20160718
     * @param prefix （字符串,如：“org.”）
     * @param bean	实体Bean, 如：Org
     * @return
     */
    public Map<String, Object> beanToMap(String prefix, Object bean) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        if(bean != null) {
            if(prefix == null) prefix = "";
            try {
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean);
                for (int i = 0; i < descriptors.length; i++) {
                    String name = descriptors[i].getName();
                    if (!StringUtils.equals(name, "class")) {
                        Object o = propertyUtilsBean.getNestedProperty(bean, name);
                        if (descriptors[i].getPropertyType().getName().equals("java.util.Date")) {
                            if (o != null) {
                                //默认日期类型多转换成yyyy-MM-dd, 如果需要不同格式, 在controller中覆盖key-value
                                params.put(prefix + name, Xiruo.dateToString(((Date) o), "yyyy-MM-dd"));
                            }
                        } else {
                            params.put(prefix + name, o);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    //将javabean转为map类型，然后返回一个map类型的值
    public Map<String, Object> beanToMap(Object bean) {
        return beanToMap("",bean);

    }

    public SysUser getUser(HttpServletRequest request){
        SysUser user = new SysUser();
        try{
            user.setId("0");
            JsonParser jsonParser = JsonParserFactory.create();
            String  token = request.getParameter("access_token");
            if (token !=null){
                Map<String, Object> map = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
                user = new SysUser();
                user.setId((String) map.get("user_id"));
                user.setOrgId((String) map.get("org_id"));
                user.setName((String) map.get("user_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public SysUser getUser(){
        SysUser user = new SysUser();
        try{
            user.setId("0");
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String token = details.getTokenValue();
            JsonParser jsonParser = JsonParserFactory.create();
            if (token !=null){
                Map<String, Object> map = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
                user.setId((String) map.get("user_id"));
                user.setOrgId((String) map.get("org_id"));
                user.setName((String) map.get("user_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    protected <T extends BaseEntity> void fillDefaultFields(T po, int event) {
        SysUserVo user = LoginManager.getUser();
        po.setUpdatedUser(user.getId());
        po.setLastUpdated(new Date());
        if(event == Contants.EVENT_INSERT) {
            po.setId(UUID.create().toString());
            po.setCreatedAt(po.getLastUpdated());
            po.setCreatedUser(user.getId());
            po.setDisable(false);
            po.setDeleteFlag(false);
        }

        po.setOrgId(user.getOrgId());
    }

    protected <T extends BaseEntity> void checkIdEmpty(T po, int event) {
        if(Contants.EVENT_UPDATE == event) {
            Optional.ofNullable(po.getId()).orElseThrow(() -> new RuntimeException("更新操作时主键不能空"));
        }
    }
}
