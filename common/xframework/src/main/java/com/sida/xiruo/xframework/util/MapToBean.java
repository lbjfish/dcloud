package com.sida.xiruo.xframework.util;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToBean {
    /**
     * 将 Map对象转化为JavaBean   此方法已经测试通过
     * @param map
     * @return Object对象
     */
    public static <T> T convertMap2Bean(Map map, Class T){
        T bean = null;
        try {
            bean = (T) T.newInstance();
        if(map==null || map.size()==0){
            return bean;
        }
        BeanUtils.copyProperties(bean,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("对象反射出错！");
        }
        return bean;
    }


    /**
     * 将 Map对象转化为JavaBean   此方法已经测试通过
     * @param object
     * @param T
     * @return Object对象
     */
    public static <T> T convertObject2Bean(Object object, Class T){
        T bean = null;
        try {
            bean = (T) T.newInstance();
            if(object==null){
                return bean;
            }
            BeanUtils.copyProperties(bean,objectToMap(object));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("对象反射出错！");
        }
        return bean;
    }


    /**
     * 将 obj对象转化为map
     * @param obj
     * @return Object对象
     */
    public static Map objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    /**
     * 将 JavaBean对象转化为 Map   此方法未测试
     * @param bean 要转化的类型
     * @return Map对象
     */
    public static Map convertBean2Map(Object bean) {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("类型转换异常！");
        }
        return returnMap;
    }

    /**
     * 将 List<Map>对象转化为List<JavaBean> 此方法已通过测试
     * @return Object对象
     */
    public static <T> List<T> convertListMap2ListBean(List<Map<String,Object>> listMap, Class T) {
        List<T> beanList = new ArrayList<T>();
        if(BlankUtil.isNotEmpty(listMap)){
            for(int i=0, n=listMap.size(); i<n; i++){
                Map<String,Object> map = listMap.get(i);
                T bean = convertMap2Bean(map,T);
                beanList.add(bean);
            }
        }

        return beanList;
    }

    /**
     * 将 List<JavaBean>对象转化为List<Map>
     * @return Object对象
     */
    public static List<Map<String,Object>> convertListBean2ListMap(List<Object> beanList){
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for(int i=0, n=beanList.size(); i<n; i++){
            Object bean = beanList.get(i);
            Map map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;
    }
}


