package com.sida.xiruo.xframework.util;

import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.controller.LoginManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成po某些公共字段的默认值
 * 2016-7-14 hbd
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PoDefaultValueGenerator{

    public static final String ID="id";
    public static final String OI="orgId";
    public static final String CA="createdAt";
    public static final String LU="lastUpdated";
    public static final String CU="createdUser";
    public static final String CUN="createdUserName";
    public static final String UU="updatedUser";
    public static final String DI="disable";
    public static final String DF="deleteFlag";


    private static Map<String,EntityDefaultValueConfig> defaultFieldMap = new HashMap<>();

    static  {
        defaultFieldMap.put(ID, new EntityDefaultValueConfig(new Class[]{String.class}, "", 1));
        defaultFieldMap.put(OI,new EntityDefaultValueConfig(new Class[]{String.class}, "",1));
        defaultFieldMap.put(CA,new EntityDefaultValueConfig(new Class[]{Date.class}, new Date(), 1));
        defaultFieldMap.put(LU,new EntityDefaultValueConfig(new Class[]{Date.class}, new Date(),1));
        defaultFieldMap.put(CU,new EntityDefaultValueConfig(new Class[]{String.class}, "",1));
        defaultFieldMap.put(CUN,new EntityDefaultValueConfig(new Class[]{String.class}, "",1));
        defaultFieldMap.put(UU,new EntityDefaultValueConfig(new Class[]{String.class}, "",1));
        defaultFieldMap.put(DI,new EntityDefaultValueConfig(new Class[]{Boolean.class}, false,1));
        defaultFieldMap.put(DF,new EntityDefaultValueConfig(new Class[]{Boolean.class}, false,1));
    }

//    public static <T> void setDefaultValue (T t) {
//        if(t != null) {
//            Class tClass = t.getClass();
//            Method setMethod;
//            for (String key : defaultFieldMap.keySet()) {
//                EntityDefaultValueConfig config = (EntityDefaultValueConfig) defaultFieldMap.get(key);
//                try {
//                    setMethod = tClass.getMethod("set" + StringUtil.upperCaseFirstLetter(key), config.getParamClass());
//                    if (setMethod != null) {
//                        if ("java.lang.Integer".equals(config.getParamClass().getName())) {
//                            setMethod.invoke(t, config.getParamDefaultValue());
//                        } else if ("java.util.Date".equals(config.getParamClass().getName())) {
//                            setMethod.invoke(t, new Date());
//                        } else {
//                            setMethod.invoke(t, config.getParamDefaultValue());
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    //System.out.println("PoDefaultValueGenerator NoSuchMethodException:" + e.getMessage());
//                    continue;
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//    public static <T> void setDefaultValueWithSource(T targetObj, Object fromObj) {
//        if(targetObj != null && fromObj != null) {
//            Class tClass = targetObj.getClass();
//            Class sClass = fromObj.getClass();
//            Method setMethod;
//            Method getMethod;
//            for (String key : defaultFieldMap.keySet()) {
//                EntityDefaultValueConfig config = (EntityDefaultValueConfig) defaultFieldMap.get(key);
//                try {
//                    setMethod = tClass.getMethod("set" + StringUtil.upperCaseFirstLetter(key), config.getParamClass());
//                    if (1 == config.getParamSource()) {
//                        if ("java.lang.Integer".equals(config.getParamClass().getName())) {
//                            setMethod.invoke(targetObj, config.getParamDefaultValue());
//                        } else if ("java.util.Date".equals(config.getParamClass().getName())) {
//                            setMethod.invoke(targetObj, new Date());
//                        } else {
//                            setMethod.invoke(targetObj, config.getParamDefaultValue());
//                        }
//                    } else {
//                        getMethod = sClass.getMethod("get" + StringUtil.upperCaseFirstLetter(key));
//                        setMethod.invoke(targetObj, getMethod.invoke(fromObj));
//                    }
//                } catch (NoSuchMethodException e) {
//                    System.out.println("PoDefaultValueGenerator NoSuchMethodException:" + e.getMessage());
//                    continue;
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    /**
     * 默认字段信息配置类
     */
    static class EntityDefaultValueConfig {

        //set方法的参数类型
        private Class paramClass = null;
        private Class[] paramClasses = null;
        //set参数值来源(1:默认,  2:外部类同名get方法获取)
        private Integer paramSource = 1;
        //set方法的默认值
        private Object paramDefaultValue = null;

        public EntityDefaultValueConfig() {
        }

        public EntityDefaultValueConfig(Class[] paramClasses, Object paramDefaultValue) {
            this.paramSource = 1;
//            this.paramClass = paramClass;
            this.paramClasses = paramClasses;
            this.paramDefaultValue = paramDefaultValue;
        }

        public EntityDefaultValueConfig(Class[] paramClasses, Object paramDefaultValue, Integer paramSource) {
            this.paramSource = paramSource;
            this.paramClasses = paramClasses;
            this.paramDefaultValue = paramDefaultValue;
        }

        public Class getParamClass() {
            return paramClass;
        }

        public void setParamClass(Class paramClass) {
            this.paramClass = paramClass;
        }

        public Object getParamDefaultValue() {
            return paramDefaultValue;
        }

        public void setParamDefaultValue(Object paramDefaultValue) {
            this.paramDefaultValue = paramDefaultValue;
        }

        public Integer getParamSource() {
            return paramSource;
        }

        public void setParamSource(Integer paramSource) {
            this.paramSource = paramSource;
        }
    }
    /**
     * 为po类设置默认值
     * @param t po对象
     * @param <T> 泛型
     */
    public static <T> void putDefaultValue(T t){
        if(t==null){
            return;
        }
        SysUserVo user = LoginManager.getUser();
        if(user==null){
            user = new SysUserVo();
        }
        if(isUpdate(t)){
            putUpdateDefault(t,user);
        }else{
            putCreateDefault(t,user);
        }
    }

    /**
     * 为po类设置默认值,原来字段有值优先使用原值，无值则置入默认值
     * @param t po对象
     * @param <T> 泛型
     */
    public static <T> void putDefaultValueWithSource(T t){
        if(t==null){
            return;
        }
        SysUserVo user = LoginManager.getUser();
        if(user==null){
            user = new SysUserVo();
        }
        if(isUpdate(t)){
            putUpdateDefaultWithSource(t,user);
        }else{
            putCreateDefaultWithSource(t,user);
        }
    }

    public static <T> void putDefaultValueNoId(T t){
        if(t==null){
            return;
        }
        SysUserVo user = LoginManager.getUser();
        if(user==null){
            user = new SysUserVo();
        }
        if(isUpdate(t)){
            putCreateDefaultNoId(t,user);
        }else{
            putCreateDefaultNoId(t,user);
        }
    }

    public static <T> void putCreateDefault(T t,SysUserVo user){
        //Long userId = LoginManager.getCurrentUserId();
        Date date=new Date();

        putDefaultValue(t,ID, UUIDGenerate.getNextId());
        putDefaultValue(t,OI,StringUtils.isBlank(user.getOrgId())?"0":user.getOrgId());
        putDefaultValue(t,CA,date);
        putDefaultValue(t,LU,date);
        putDefaultValue(t,CU,StringUtils.isBlank(user.getId())?"0":user.getId());
        putDefaultValue(t,CUN,StringUtils.isBlank(user.getName())?"0":user.getName());
        putDefaultValue(t,UU,StringUtils.isBlank(user.getId())?"0":user.getId());
        putDefaultValue(t,DI,false);
        putDefaultValue(t,DF,false);
    }

    public static <T> void putCreateDefaultWithSource(T t,SysUserVo user){
        Date date=new Date();
        if (getValue(t,ID) == null || BlankUtil.isEmpty(getValue(t,ID).toString())){
            putDefaultValue(t, ID, UUIDGenerate.getNextId());
        }
        if (getValue(t,OI) == null || BlankUtil.isEmpty(getValue(t,OI).toString())){
            putDefaultValue(t, OI,StringUtils.isBlank(user.getOrgId())?"0":user.getOrgId());
        }
        if (getValue(t,CA) == null) {
            putDefaultValue(t, CA, date);
        }
        if (getValue(t,LU) == null) {
            putDefaultValue(t, LU, date);
        }
        if (getValue(t,CU) == null || BlankUtil.isEmpty(getValue(t,CU).toString())) {
            putDefaultValue(t, CU, StringUtils.isBlank(user.getId()) ? "0" : user.getId());
        }
        if (getValue(t,CUN) == null || BlankUtil.isEmpty(getValue(t,CUN).toString())) {
            putDefaultValue(t, CUN, StringUtils.isBlank(user.getName()) ? "0" : user.getName());
        }
        if (getValue(t,UU) == null || BlankUtil.isEmpty(getValue(t,UU).toString())) {
            putDefaultValue(t, UU, StringUtils.isBlank(user.getId()) ? "0" : user.getId());
        }
        if (getValue(t,DI) == null) {
            putDefaultValue(t, DI, false);
        }
        if (getValue(t,DF) == null) {
            putDefaultValue(t, DF, false);
        }
    }

    public static <T> void putCreateDefaultNoId(T t,SysUserVo user){
        //Long userId = LoginManager.getCurrentUserId();
        Date date=new Date();

        putDefaultValue(t,OI,StringUtils.isBlank(user.getOrgId())?"0":user.getOrgId());
        putDefaultValue(t,CA,date);
        putDefaultValue(t,LU,date);
        putDefaultValue(t,CU,StringUtils.isBlank(user.getId())?"0":user.getId());
        putDefaultValue(t,CUN,StringUtils.isBlank(user.getName())?"0":user.getName());
        putDefaultValue(t,UU,StringUtils.isBlank(user.getId())?"0":user.getId());
        putDefaultValue(t,DI,false);
        putDefaultValue(t,DF,false);
    }

    public static <T> void putUpdateDefault(T t){
        SysUser user = LoginManager.getUser();
        putDefaultValue(t, LU, new Date());
        putDefaultValue(t, UU, StringUtils.isBlank(user.getId())?"0":user.getId());
    }

    public static <T> void putUpdateDefault(T t,SysUserVo user){
        //Long userId = LoginManager.getCurrentUserId();
        putDefaultValue(t, LU, new Date());
        putDefaultValue(t, UU, StringUtils.isBlank(user.getId())?"0":user.getId());
    }

    public static <T> void putUpdateDefaultWithSource(T t,SysUserVo user){
        Date date=new Date();
        if (getValue(t,OI) == null || BlankUtil.isEmpty(getValue(t,OI).toString())){
            putDefaultValue(t, OI,StringUtils.isBlank(user.getOrgId())?"0":user.getOrgId());
        }
        if (getValue(t,CA) == null) {
            putDefaultValue(t, CA, date);
        }
        if (getValue(t,LU) == null) {
            putDefaultValue(t, LU, date);
        }
        if (getValue(t,CU) == null || BlankUtil.isEmpty(getValue(t,CU).toString())) {
            putDefaultValue(t, CU, StringUtils.isBlank(user.getId()) ? "0" : user.getId());
        }
        if (getValue(t,CUN) == null || BlankUtil.isEmpty(getValue(t,CUN).toString())) {
            putDefaultValue(t, CUN, StringUtils.isBlank(user.getName()) ? "0" : user.getName());
        }
        if (getValue(t,UU) == null || BlankUtil.isEmpty(getValue(t,UU).toString())) {
            putDefaultValue(t, UU, StringUtils.isBlank(user.getId()) ? "0" : user.getId());
        }
        if (getValue(t,DI) == null) {
            putDefaultValue(t, DI, false);
        }
        if (getValue(t,DF) == null) {
            putDefaultValue(t, DF, false);
        }
    }

    public static <T> void putDefaultValue(T t,String field,Object value){
        try{
            Method getMethod=BeanUtils.findMethod(t.getClass(),"get"+StringUtil.upperCaseFirstLetter(field));
            if(getMethod==null){
                return;
            }
            Class returnType=getMethod.getReturnType();
            value=convertValue(value,returnType);


            Method setMethod=BeanUtils.findMethod(t.getClass(),"set"+StringUtil.upperCaseFirstLetter(field),returnType);
            if(setMethod==null){
                return;
            }
            setMethod.invoke(t,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static <T> Object getValue(T t,String field){
        try{
            Method getMethod=BeanUtils.findMethod(t.getClass(),"get"+StringUtil.upperCaseFirstLetter(field));
            if(getMethod==null){
                return null;
            }
            return getMethod.invoke(t);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static Object convertValue(Object value,Class returnType){
        if(value==null){
            return null;
        }
        if(returnType==Boolean.class){
            return "1".equals(value.toString());
        }else if(returnType==Integer.class){
            return Integer.valueOf(value.toString());
        }
        return value;
    }
    public static <T> boolean isUpdate(T t){
        try{
            Method idMethod=BeanUtils.findMethod(t.getClass(),"getId");
            Object id=idMethod.invoke(t);
            if(id!=null && StringUtils.isNotBlank(id.toString())){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }



    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        CstCustomerVo cstCustomerVo = new CstCustomerVo();
//        CstCustomer cstCustomer = new CstCustomer();
//        AdapterEntity entity = new AdapterEntity();
//        UserSession session = new UserSession();
//        session.setDeptId(300);
//        session.setOrgId(101);
//        com.shj.framework.xframework.po.PoDefaultValueGenerator.setDefaultValueWithSource(cstCustomerVo,session);
//        System.out.println(cstCustomerVo.getCreatedBy());
//        System.out.println(cstCustomerVo.getCreatedDate());
//        System.out.println(cstCustomerVo.getDeptId());
//        System.out.println(cstCustomerVo.getOrgId());
    }
}
