package com.sida.xiruo.xframework.util;

import com.sida.xiruo.common.components.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 基于基础实体类的工具类
 */
public class BaseEntityUtil{


	/**
	 * 置入排序条件（默认为创建时间降序）
	 * @param target
	 * @param source
	 * @param <T>
	 * @param <S>
	 */
	public static <T,S> void putSort(T target, S source) {
		putSort(target,source,"created_at","desc");
	}




	/**
	 * 置入排序条件
	 * @param target
	 * @param source
	 * @param orderField
	 * @param orderString
	 * @param <T>
	 * @param <S>
	 */
	public static <T,S> void putSort(T target, S source, String orderField, String orderString) {
		try{
			if (source==null || target==null){return;}

			Method getOrderField = BeanUtils.findMethod(source.getClass(),"getOrderField");
			Method getOrderString = BeanUtils.findMethod(source.getClass(),"getOrderString");

			PropertyDescriptor of = new PropertyDescriptor("orderField", source.getClass());
			PropertyDescriptor os = new PropertyDescriptor("orderString", source.getClass());

			Method setOrderField = of.getWriteMethod();
			Method setOrderString = os.getWriteMethod();

			if (!Modifier.isPublic(setOrderField.getDeclaringClass().getModifiers())) {
				setOrderField.setAccessible(true);
			}
			if (!Modifier.isPublic(setOrderString.getDeclaringClass().getModifiers())) {
				setOrderString.setAccessible(true);
			}

			Object orderFlag = getOrderField.invoke(source);

			//前端未设置排序参数
			if (orderFlag!=null && StringUtils.isNotBlank(orderFlag.toString())){
				orderField = orderFlag.toString();
				orderString = getOrderString.invoke(source)==null?"asc":("asc".equals(getOrderString.invoke(source).toString())?"asc":"desc");
				setOrderField.invoke(target,orderField);
				setOrderString.invoke(target,orderString);
			}else {
				//否则系统添加默认排序（创建时间 降序）
				setOrderField.invoke(target,StringUtil.camelToUnderline(orderField));
				setOrderString.invoke(target,orderString);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
