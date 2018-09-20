package com.sida.xiruo.common.util;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * description:主要针对Ext的Json操作
 *
 * @author: zhaocong
 * @version May 25, 2011
 */
public class EasyJson {

	public static final String MSG = "msg";
	public static final String SUCCESS = "success";
	public static final String DATA = "data";
	
	public static JsonConfig JC=null;
	
	static{
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};   
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		DateJsonValueProcessor dateJsonValueProcessor =new DateJsonValueProcessor();//默认日期转换(java-->json),默认为:yyyy-MM-dd HH:mm:ss
		JC = new JsonConfig();
		JC.registerJsonValueProcessor(Date.class,dateJsonValueProcessor);
		JC.registerJsonValueProcessor(Timestamp.class,dateJsonValueProcessor);
	}
	
	/**
	 * 创建并返回一个JSONObject实例
	 * @return JSONObject
	 */
	public static JSONObject getJSONObject(){
		return new JSONObject();
	}
	
	/**
	 * 表单加载Json并写入Writer
	 * @param data
	 * @param writer
	 * @return {success:true,data:{}}
	 */
	public static String result(Object data,Writer writer){
		return result(true,null,data,writer);
	}
	
	/**
	 * 返回信息
	 * @param isSuccess
	 * @param msg
	 * @param data
	 * @param writer
	 * @return {"success":true/false,"msg":'',data:{}}
	 */
	public static String result(boolean isSuccess,String msg,Object data,Writer writer){
		JSONObject jsonObject = getJSONObject();
		jsonObject.put(SUCCESS, isSuccess);
		Map<String,Object> map= new HashMap<String,Object>();
		if(msg != null){
			map.put(MSG, msg);
			//jsonObject.put(MSG, msg);
		}
		if(data != null){
			map.put(DATA, data);
			//jsonObject.put(DATA, data);
		}
		jsonObject.putAll(map, JC);
		if(writer != null){
			jsonObject.write(writer);
		}
		return jsonObject.toString();
	}
	
	/**
	 * 根据object创建并返回一个JSONArray实例
	 * @param object
	 * @return JSONArray
	 */
	public static JSONArray getJSONArray(Object object){
		return JSONArray.fromObject(object);
	}
	
	/**
	 * List转Json并写入Writer中
	 * @param list
	 * @param writer
	 * @return [{},{}]
	 */
	public static String list(List list,Writer writer){
		JSONArray jsonArray = getJSONArray(list);
		jsonArray.write(writer);
		return jsonArray.toString();
	}
	
	/**
	 * 成功/错误 提示并写入Writer
	 * @param isSuccess
	 * @param msg 相关信息
	 * @return {"success":true/false,"msg":""}
	 */
	public static String result(boolean isSuccess,String msg,Writer writer){
		return result(isSuccess,msg,null,writer);
	}
	
	/**
	 * 根据object创建并返回一个JSONObject实例
	 * @param object
	 * @return JSONObject
	 */
	public static JSONObject getJSONObject(Object object){
		return JSONObject.fromObject(object);
	}
}
