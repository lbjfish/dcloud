package com.sida.xiruo.common.components;

import java.beans.Expression;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BeanUtils { 


	public static <E extends Object> E copy(Object source, E dest, String... excludedFieldName){
		if(source == null || dest == null){
			return null;
		}
		Field[] fields = ClassUtils.getNoStaticNorFinalFieldArray(source);		
		try{
			if(ClassUtils.isSameType(source, dest)){//same type object
				for(Field field : fields){
					field.setAccessible(true);			
					if(StringUtils.isInList(field.getName(), excludedFieldName)){//filter 
						continue;
					}			
					field.set(dest, field.get(source));
				}
			}else{
				for(Field field : fields){
					field.setAccessible(true);			
					if(StringUtils.isInList(field.getName(), excludedFieldName)){//filter
						continue;
					}			
					Field destField = null;
					try{
						destField = dest.getClass().getDeclaredField(field.getName());
					}catch(NoSuchFieldException e){
					}
					if(destField != null){
						destField.setAccessible(true);						
						destField.set(dest, field.get(source));
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return dest;
	}
	
	public static String getSetMethodName(String field){
		return  "set" + StringUtils.firstLetterUpper(field);
	}
	
	public static String getgetMethodName(String field){
		return  "get" + StringUtils.firstLetterUpper(field);
	}
	
	public static Object inject(String classFullName, Map<String, Object> fieldNameAndValues) throws InstantiationException, ClassNotFoundException, IllegalAccessException{
		return inject(Class.forName(classFullName), fieldNameAndValues);
		
	}
	
	public static Object inject(Class<?> c, Map<String, Object> fieldNameAndValues) throws InstantiationException, ClassNotFoundException, IllegalAccessException{
		Object target = c.newInstance();
		return inject(target, fieldNameAndValues);
	}
	
	public static Object inject(Object target, Map<String, Object> fieldNameAndValues) throws InstantiationException, ClassNotFoundException, IllegalAccessException{
		Set<Map.Entry<String,Object>> keyValues = fieldNameAndValues.entrySet();
		for(Map.Entry<String, Object> keyValue : keyValues){
			String methodName = getSetMethodName(keyValue.getKey());
			Expression exp = new Expression(target, methodName, new Object[]{keyValue.getValue()});
			try {
				exp.getValue();
			} catch (Exception e) {
				ExpressionUtils.setFieldValue(target, keyValue.getKey(), keyValue.getValue());
			}
		}
		return target;
	}


	public static void injectField(Object target, Field field, Object value){
		String setMethodName = BeanUtils.getSetMethodName(field.getName());
		try {
			ExpressionUtils.executeMethod(target, setMethodName, new Object[]{value});
		} catch (Exception e) {
			ExpressionUtils.setFieldValue(target, field, value);
		}
	}
	
	public static boolean isBasicType(Class<?> c){
		if(String.class.equals(c)){
			return true;
		}else if(Integer.class.equals(c) || int.class.equals(c)){
			return true;
		}else if(Double.class.equals(c) || double.class.equals(c)){
			return true;
		}else if(Float.class.equals(c) || float.class.equals(c)){
			return true;
		}else if(Long.class.equals(c) || long.class.equals(c)){
			return true;
		}else if(Short.class.equals(c) || short.class.equals(c)){
			return true;
		}else if(Boolean.class.equals(c) || boolean.class.equals(c)){
			return true;
		}else if(Byte.class.equals(c) || byte.class.equals(c)){
			return true;
		}else if(Character.class.equals(c) || char.class.equals(c)){
			return true;
		}else if(Date.class.equals(c)){
			return true;
		}else if(List.class.equals(c) || ArrayList.class.equals(c)){
			return true;
		}else if(String[].class.equals(c)){
			return true;
		}else if(int[].class.equals(c) || Integer[].class.equals(c)){
			return true;
		}else if(long[].class.equals(c) || Long[].class.equals(c)){
			return true;
		}else if(short[].class.equals(c) || Short[].class.equals(c)){
			return true;
		}else if(float[].class.equals(c) || Float[].class.equals(c)){
			return true;
		}else if(double[].class.equals(c) || Double[].class.equals(c)){
			return true;
		}else if(byte[].class.equals(c) || Byte[].class.equals(c)){
			return true;
		}else if(boolean[].class.equals(c) || Boolean[].class.equals(c)){
			return true;
		}else if(char[].class.equals(c) || Character[].class.equals(c)){
			return true;
		}else{
			return false;
		}
	}
}