package com.sida.xiruo.common.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
	/**
	* Check two object class types, if they are same class instance return true, else return false.
	* @param a object a
	* @param b object b
	* @return <code>true</code> if a & b are the same class instance
	*/
	public static boolean isSameType(Object a, Object b){
		validateParameters(a, b);
		return isSameType(a.getClass(), b.getClass());
	}
	
	/**
	* 
	* Check two class types,if they they are same return true, else return false.
	* @param a object a
	* @param b object b
	* @return <code>true</code> if a & b are the same class instance
	*/
	@SuppressWarnings("unchecked")
	public static boolean isSameType(Class a, Class b){
		validateParameters(a,b);
		if(a.getName().equals(b.getName())){
			return true;
		}else{
			return false;
		}
	}
	
	//for private use
	private static void validateParameters(Object a, Object b){
		if(a == null || b == null){
			throw new java.lang.IllegalArgumentException("parameters cannot be null");
		}
	}
	
	/**
	* Get field form an object by appointing field name, it search form all declared fields</p>
	* including private protected public final and static fields
	* @param obj the object to fetch field
	* @param name field name
	* @return destination Field
	* @throws NoSuchFieldException
	*/
	public static Field getField(Object obj, String name){
		try{
			Field field =  obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	* Get no static field nor final field form an object by appointing field name, it search form all declared fields
	* including private protected and public field
	* @param obj the object to fetch fields
	* @return Field array
	*/
	public static Field[] getNoStaticNorFinalFieldArray(Object obj){
		return getNoStaticNorFinalFieldList(obj).toArray(new Field[]{});
	}
	
	/**
	* Get no static field nor final field form an object by appointing field name, it search form all declared fields
	* including private protected and public fields
	* @param obj the object to fetch field
	* @return Field list
	*/
	public static List<Field> getNoStaticNorFinalFieldList(Object obj){
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Field> nostaticFieldList = new ArrayList<Field>();
		for(Field field : fields){
			field.setAccessible(true);
			if(!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())){
				nostaticFieldList.add(field);
			}
		}
		return nostaticFieldList;
	}
	
	
	/**
	* Get no static field form an object by appointing field name, it search form all declared fields
	* including private protected and public field
	* @param obj the object to fetch fields
	* @return Field array
	*/
	public static Field[] getNoStaticFieldArray(Object obj){
		return getNoStaticFieldList(obj).toArray(new Field[]{});
	}
	
	/**
	* Get no static field form an object by appointing field name, it search form all declared fields
	* including private protected and public fields
	* @param obj the object to fetch field
	* @return Field list
	*/
	public static List<Field> getNoStaticFieldList(Object obj){
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Field> nostaticFieldList = new ArrayList<Field>();
		for(Field field : fields){
			field.setAccessible(true);
			if(!Modifier.isStatic(field.getModifiers())){
				nostaticFieldList.add(field);
			}
		}
		return nostaticFieldList;
	}
	
	/**
	* Get field form an object by appointing field name, it search form all declared fields but not static or final
	* including private protected and public fields
	* @param obj the object to fetch field
	* @param name field name
	* @return destination
	* @throws NoSuchFieldException
	*/
	public Field getNoStaticNorFinalField(Object obj, String name)  throws NoSuchFieldException{
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			if(!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())){
				if(field.getName().equals(name)){
					return field;
				}
			}		
		}
		throw new NoSuchFieldException(String.format("No such field: %s", name));
	}
	
	/**
	* <p>Check weather specify class is from JDK </p>
	* @param c
	* @return
	*/
	public static boolean isSystemClass(Class<?> c){
		return c.getName().startsWith("java.");
	}
}
