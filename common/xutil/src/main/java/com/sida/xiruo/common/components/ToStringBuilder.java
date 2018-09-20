package com.sida.xiruo.common.components;

import java.lang.reflect.Field;

public class ToStringBuilder {
	//各种固定的字符串
	private static final String FILED_START = "[";
	private static final String FIELD_END = "]";
	private static final String SPLIT_CLASSNAME_HASHCODE = "@";
	private static final String SPLIT_FIELD_FIELD = ",";
	private static final String EQUAL = "=";
	//整个类的核心就是通过一个StringBuilder让其以期望的格式不停append字符串
	private StringBuilder sb;
	//字段个数
	private int fieldNum = 0;


	/**
	* @param obj The object to overwrite toString() method 
	*/
	public ToStringBuilder(Object obj){
		this.sb = new StringBuilder();	
		sb.append(obj.getClass().getSimpleName()).append(SPLIT_CLASSNAME_HASHCODE).append(obj.hashCode());
	}


	/**
	* <p>Append to the toString</p>
	* @param filedName	Field name
	* @param obj the value of field
	* @return ToStringBuilder
	*/
	public ToStringBuilder append(String filedName, Object obj){
		fieldNum++;
		if(fieldNum == 1){
			sb.append(FILED_START).append(filedName).append(EQUAL).append(obj);
		}else{
			sb.append(SPLIT_FIELD_FIELD).append(filedName).append(EQUAL).append(obj);
		}
		return this;
	}
	
	public String toString(){
		if(fieldNum > 0){
			sb.append(FIELD_END);
		}
		return this.sb.toString();
	}
	
	public static String reflectionToString(Object obj , String ... excludeFieldName){
		ToStringBuilder tsb = new ToStringBuilder(obj);
		//取得所有非静态属性
		Field[] fields = ClassUtils.getNoStaticFieldArray(obj);
		try{
			for(Field field : fields){
				field.setAccessible(true);
				
				if(StringUtils.isInList(field.getName(), excludeFieldName)){
					continue;
				}
				tsb.fieldNum ++;
				if(tsb.fieldNum == 1){
					tsb.sb.append(FILED_START);
				}else{
					tsb.sb.append(SPLIT_FIELD_FIELD);
				}
				
				//append field name
				tsb.sb.append(field.getName());						
				tsb.sb.append(EQUAL);
				//append field value
				tsb.sb.append(field.get(obj));
			}
		}catch(Exception e){
			throw new RuntimeException("Error while reflection the class fields" , e);
		}
		return tsb.toString();
	}
}
