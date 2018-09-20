package com.sida.xiruo.xframework.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转换工具类。转化列表对象为Map对象，从而为前端提供键值信息，而排除其他多余信息
 * @author wuzhenwei
 *
 */
public class BuildKeyValueMapUtil{


	/**
	 * 转换为 键值对 列表
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> List<Map<String,String>> change(List<T> list) {
		return change(list,"id","name","key","value");
	}



	/**
	 * 转换为 键值对 列表
	 * @param list
	 * @param key
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> List<Map<String,String>> change(List<T> list,String key,String value, String returnKey,String returnValue) {
		if(null == list || list.size() == 0){ return null;}
		List<Map<String,String>> result = Lists.newArrayList();
		try{
			T first = list.get(0);
			Method getKey = BeanUtils.findMethod(first.getClass(),"get"+StringUtil.upperCaseFirstLetter(key));
			Method getValue = BeanUtils.findMethod(first.getClass(),"get"+StringUtil.upperCaseFirstLetter(value));

			for(int i=0; i<list.size(); i++) {
				Map<String,String> map = new HashMap<>();
				T source = list.get(i);
				map.put(returnKey,getKey.invoke(source)==null?null:getKey.invoke(source).toString());
				map.put(returnValue,getValue.invoke(source)==null?null:getValue.invoke(source).toString());
				result.add(map);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 构建前端select组件候选值，含默认选中值
	 * @param list 来源数据
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String,Object> buildSelect(List<T> list) {
		return buildSelect(list,"id","name","status","2");
	}

	/**
	 * 构建前端select组件候选值，含默认选中值
	 * @param list 来源数据
	 * @param key  下拉框值字段对应 实体类属性字段 如：id
	 * @param value 下拉框显示值字段对应 实体类属性字段 如：name
	 * @param defaultKey 默认值判断对应的字段 如：status
	 * @param defaultValue 默认值判断标准 如：2，则只有status字段为2的时候，该条记录为默认选中
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String,Object> buildSelect(List<T> list, String key, String value, String defaultKey, String defaultValue) {
		if(null == list || list.size() == 0){ return null;}
		Map<String,Object> resultMap = new HashMap<>();
		List<Map<String,Object>> result = Lists.newArrayList();
		try{
			T first = list.get(0);
			Method getKey = BeanUtils.findMethod(first.getClass(),"get"+StringUtil.upperCaseFirstLetter(key));
			Method getValue = BeanUtils.findMethod(first.getClass(),"get"+StringUtil.upperCaseFirstLetter(value));
			Method getChildren = null;
			try {
				getChildren = BeanUtils.findMethod(first.getClass(),"getChildren");
			}catch (Exception e){

			}
			Method getDefaultKey = null;
			if (BlankUtil.isNotEmpty(defaultKey)){
				getDefaultKey = BeanUtils.findMethod(first.getClass(),"get"+StringUtil.upperCaseFirstLetter(defaultKey));
			}
			Boolean findDefault = false;
			resultMap.put("value","");
			for(int i=0; i<list.size(); i++) {
				Map<String,Object> map = new HashMap<>();
				T source = list.get(i);
				map.put("value",getKey.invoke(source)==null?null:getKey.invoke(source).toString());
				map.put("label",getValue.invoke(source)==null?null:getValue.invoke(source).toString());
				if (!findDefault && BlankUtil.isNotEmpty(getDefaultKey) && getDefaultKey.invoke(source) != null){
					if (BlankUtil.isNotEmpty(defaultValue) && getDefaultKey.invoke(source).toString().equals(defaultValue)){
						resultMap.put("value",map.get("value"));
						findDefault = true;
					}
				}
				if (BlankUtil.isNotEmpty(getChildren)){
					List<T> children = getChildren.invoke(source)==null?null:(List)getChildren.invoke(source);
					if (BlankUtil.isNotEmpty(children)){
						List<Map<String,String>> childrenMapList = change(children,key,value,"value","label");
						map.put("children",childrenMapList);
					}
				}
				result.add(map);
			}
			resultMap.put("options",result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}




}
