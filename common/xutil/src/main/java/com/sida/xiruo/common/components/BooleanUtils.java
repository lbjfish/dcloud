package com.sida.xiruo.common.components;

public class BooleanUtils { 


	/**0 means false**/
	public static final int NUM_FALSE = 0;
	/**1 means true**/
	public static final int NUM_TRUE = 1;
	/**"false"**/
	public static final String STR_FALSE = "false";
	/**"true"**/
	public static final String STR_TRUE = "true";


	public static boolean toBoolean(int num){
		if(num==0){
			return false;
		}
		return true;
	}
	
	public static boolean toBoolean(String str, boolean defaultValue){
		if(STR_FALSE.equalsIgnoreCase(str)){
			return false;
		}else if(STR_TRUE.equalsIgnoreCase(str)){
			return true;
		}else{
			return defaultValue;
		}
	}
	
	public static boolean toBoolean(String str){
		return	toBoolean(str, false);
	}
	
	public static boolean toBooleanStrict(String str){
		if(STR_FALSE.equalsIgnoreCase(str)){
			return false;
		}else if(STR_TRUE.equalsIgnoreCase(str)){
			return true;
		}else{
			throw new IllegalArgumentException(String.format("String '%s' can not parse to a boolean object", str));
		}
	}
}
