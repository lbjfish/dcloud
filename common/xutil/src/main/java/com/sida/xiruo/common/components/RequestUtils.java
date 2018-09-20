package com.sida.xiruo.common.components;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>HttpServletRequest Operation</p>
 * @author Lucky
 */
public class RequestUtils {
	
	/**
	* <p>If request.getParameter(param) return null, return defaultValue</p>
	* @param request HttpServletRequest
	* @param param 
	* @param defaultValue
	* @return
	*/
	public static String getString(HttpServletRequest request, String param, String defaultValue){
		String value = request.getParameter(param);		
		return value != null ? value : defaultValue;
	}
	
	/**
	* <p>If request.getParameter(param) return null, return blank String</p>
	* @param request HttpServletRequest
	* @param param 
	* @return
	*/
	public static String getString(HttpServletRequest request, String param){
		return getString(request, param, StringUtils.EMPTY);
	}
	
	/**
	* <p>Return Decoder(request.getParameter(param)) use "UTF-8" charSet, if fails return blank string</p>
	* @param request HttpServletRequest
	* @param param
	* @return
	*/
	public static String getDecoderString(HttpServletRequest request, String param){
		return URLUtils.decode(RequestUtils.getString(request,param));
	}


	/**
	* <p>Convert the request.getParameter(param) to Int and return, if fails return defaultValue</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static int getInt(HttpServletRequest request, String param, int defaultValue){
		return NumberUtils.toInt(request.getParameter(param) , defaultValue);		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Int and return, if fails return ZERO</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static int getInt(HttpServletRequest request, String param){
		return NumberUtils.toInt(request.getParameter(param));		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Long and return, if fails return defaultValue</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static long getLong(HttpServletRequest request, String param, long defaultValue){
		return NumberUtils.toLong(request.getParameter(param) , defaultValue);		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Long and return, if fails return ZERO</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static long getLong(HttpServletRequest request, String param){
		return NumberUtils.toLong(request.getParameter(param));		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Double and return, if fails return defaultValue</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static double getDouble(HttpServletRequest request, String param, double defaultValue){
		return NumberUtils.toDouble(request.getParameter(param) , defaultValue);		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Double and return, if fails return ZERO</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static double getDouble(HttpServletRequest request, String param){
		return NumberUtils.toDouble(request.getParameter(param));		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Float and return, if fails return defaultValue</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static float getFloat(HttpServletRequest request, String param, float defaultValue){
		return NumberUtils.toFloat(request.getParameter(param) , defaultValue);		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Float and return, if fails return ZERO</p>
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return
	*/
	public static float getFloat(HttpServletRequest request, String param){
		return NumberUtils.toFloat(request.getParameter(param));		
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Boolean and return, if fails return defaultValue</p>
	* "true"/"TRUE" for true "false"/"FALSE" for false
	* @param request HttpServletRequest
	* @param param
	* @param defaultValue
	* @return 
	*/
	public static boolean getBoolean(HttpServletRequest request, String param, boolean defaultValue){
		return BooleanUtils.toBoolean(request.getParameter("param"), defaultValue);
	}
	
	/**
	* <p>Convert the request.getParameter(param) to Boolean and return, if fails return false</p>
	* "true"/"TRUE" for true "false"/"FALSE" for false
	* @param request HttpServletRequest
	* @param param
	* @return
	*/
	public static boolean getBoolean(HttpServletRequest request, String param){
		return BooleanUtils.toBoolean(request.getParameter("param"));
	}
	
	public static <T> T getParameter(HttpServletRequest request, String param, Class<T> type, T defaultValue){
		String value = request.getParameter(param);
		if(value != null){
			try{
				return StringConvertUtils.convertStringToObject(value, type);
			}catch(Exception e){
				return defaultValue;
			}
		}else{
			return defaultValue;
		}
	}
	
	public static String getISO8859_1String(HttpServletRequest request, String param, boolean decoded){
		String value = RequestUtils.getString(request, param);
		if(decoded){
			value = URLUtils.decode(value);
		}
		if(value.length() > 0){
			try{
				return new String(value.getBytes("ISO8859-1"), "UTF-8");
			}catch(Exception e){
				return value;
			}
		}else{
			return value;
		}
	}
	
	public static String getISO8859_1String(HttpServletRequest request, String param){
		return getISO8859_1String(request, param, false);
	}
	
	public static String getISO8859_1StringDecoded(HttpServletRequest request, String param){
		return getISO8859_1String(request, param, true);
	}
	
	public static <T> T getObject(HttpServletRequest request, Class<T> c){
		try{
			T target = c.newInstance();
			Field[] fieldArray = ClassUtils.getNoStaticNorFinalFieldArray(target);
			for(Field f : fieldArray){
				Class<?> paramType = f.getType();
				String parameterValue = RequestUtils.getDecoderString(request, f.getName());
				if(parameterValue == null || StringUtils.isBlank(parameterValue)){
					continue;
				}
				Object value = StringConvertUtils.convertStringToObject(parameterValue, paramType); 
				BeanUtils.injectField(target, f, value);
			}
			return target;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static Object getObject(HttpServletRequest request, String className) throws ClassNotFoundException{
		try{
			Object target = Class.forName(className).newInstance();
			Field[] fieldArray = ClassUtils.getNoStaticNorFinalFieldArray(target);
			for(Field f : fieldArray){
				Class<?> paramType = f.getType();
				String parameterValue = RequestUtils.getDecoderString(request, f.getName());
				if(parameterValue == null || StringUtils.isBlank(parameterValue)){
					continue;
				}
				Object value = StringConvertUtils.convertStringToObject(parameterValue, paramType); 
				BeanUtils.injectField(target, f, value);
			}
			return target;
		}catch(ClassNotFoundException cnfe){
			throw cnfe;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	* <p>Get parameter value from request first, if null, get Attribute from request and convert it to string</p>
	* @param request
	* @param key
	* @return
	*/
	public static String getStringParamOrAttibute(HttpServletRequest request, String key){
		String value = request.getParameter(key);
		if(value != null){
			return value;
		}else{
			return getStringAttribute(request, key);
		}
	}
	
	/**
	* <p>Get String attribute, if fails return blank string ""</p>
	* @param request
	* @param param
	* @return
	*/
	public static String getStringAttribute(HttpServletRequest request, String param){
		return getStringAttribute(request, param, StringUtils.EMPTY);
	}
	
	/**
	* <p>Get String attribute, if fails return defaultValue</p>
	* @param request
	* @param param
	* @param defaultValue
	* @return
	*/
	public static String getStringAttribute(HttpServletRequest request, String param, String defaultValue){
		Object obj = request.getAttribute(param);
		if(obj == null){
			return defaultValue;
		}
		try{
			return obj.toString();
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	/**
	* <p>Get integer attribute, if fails return 0</p>
	* @param request
	* @param param
	* @return
	*/
	public static int getIntAttibute(HttpServletRequest request, String param){
		return getIntAttibute(request, param, 0);
	}
	
	/**
	* <p>Get integer attribute, if fails return defaultValue</p>
	* @param request
	* @param param
	* @param defaultValue
	* @return
	*/
	public static int getIntAttibute(HttpServletRequest request, String param, int defaultValue){
		Object obj = request.getAttribute(param);
		try{
			return (Integer)obj;
		}catch(Exception e){
			return defaultValue;
		}
	}


	@SuppressWarnings("unchecked")
	public static <T> T getAttibute(HttpServletRequest request, String param, Class<T> type, T defaultValue){
		Object obj = request.getAttribute(param);
		if(obj != null){
			try{
				return (T)obj;
			}catch(Exception e){
				throw new IllegalArgumentException(String.format("Convert failure from %s to %s", obj.getClass().getName(), type.getName()));
			}
		}else{
			return defaultValue;
		}
	}
	
	public static String getIPAddress(HttpServletRequest request){
		return request.getRemoteAddr();
	}
	
	public static String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}
}