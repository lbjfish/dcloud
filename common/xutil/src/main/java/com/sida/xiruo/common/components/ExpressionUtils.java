package com.sida.xiruo.common.components;

import java.beans.Expression;
import java.lang.reflect.Field;

public class ExpressionUtils {
	
	public static Object executeMethod(Object target, String methodName, Object[] arguments){
		Expression exp = new Expression(target, methodName, arguments);
		try {
			return exp.getValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	public static Object getFieldValue(Object obj, String name){
		try{
			return ClassUtils.getField(obj, name).get(obj);
		}catch(Exception e){
			throw new IllegalArgumentException(String.format("Field name %s not found.", name));
		}	
	}
	
	public static void setFieldValue(Object obj, String fieldName, Object value){
		Field field = null;
		try{
			field = ClassUtils.getField(obj, fieldName);
			setFieldValue(obj, field, value);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void setFieldValue(Object obj, Field field, Object value){
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}	
}