package com.sida.xiruo.common.util;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * description:转成ExtJs所需要的json格式
 *
 * @author: zhaocong
 * @version May 25, 2011
 */
public class EXTJSONUtils {
	public static final String ROOT = "root";
	public static final String TOTAL_PROPERTY = "totalProperty";
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
		JC.setExcludes(new String[]{"handler","hibernateLazyInitializer"});  
	}
	
	/**
	 * 转换成ExtJs的json信息，转换后的格式如下：
	 * <dl>
	 * <dt>当obj为布尔类型时：</dt>
	 * <dd>{success:true/false}</dd>
	 * 
	 * <dt>当obj为字符串类型时：</dt>
	 * <dd>{success:false,msg:""}</dd>
	 * 
	 * <dt>当obj为集合类型或数组时：</dt>
	 * <dd>[{},{}..]</dd>
	 * 
	 * <dt>当obj为其他类型时：</dt>
	 * <dd>{success:true,data:{..}}</dd>
	 * </dl>
	 * @param  要转换的对象
	 * @param msg 提示信息，当obj的类型为Collection时无效
	 * @param writer 将json写入到此写入流中，为null时不写入
	 * @return 转换后的json字符串
	 */
	public static String toEXTJson(Object obj ,Writer writer){
		JSON json = toEXTJson(obj);
		if(writer != null){
			json.write(writer);
		}
		return json.toString();
	}
	/**
	 * 转换成ExtJs的JSON
	 * @param obj 要转换的对象
	 * @param msg 提示信息，当obj的类型为Collection时无效
	 * @return JSON
	 */
	public static JSON toEXTJson(Object obj){
		if(JSONUtils.isArray(obj)){
			return JSONArray.fromObject(transferArray(obj), JC);
		}else {
			JSONObject json = new JSONObject();
			if(JSONUtils.isString(obj)){
				json.put(SUCCESS, false);
				json.put(MSG, obj);
			}else if(JSONUtils.isBoolean(obj)){
				json.put(SUCCESS, obj);
			}else{
				json.put(SUCCESS, true);
				if(obj instanceof JSONObjectTransfer){
					obj = ((JSONObjectTransfer)obj).toJSONObject();
				}
				json.put(DATA, obj);
			}
			return json;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> transferArray(Object arr){
		List<Object> list = new ArrayList<Object>();
		//当为数组时
		if(arr instanceof Object[]){
			Object[] arrs = (Object[])arr;
			for(Object obj : arrs){
				if(obj instanceof JSONObjectTransfer){
					JSONObjectTransfer jt = (JSONObjectTransfer)obj;
					obj = jt.toJSONObject();
				}
				list.add(obj);
			}
		}else if(arr instanceof Collection){
			Collection cln = (Collection)arr;
			Iterator<Object> iter = cln.iterator();
			while(iter.hasNext()){
				Object obj = iter.next();
				if(obj instanceof JSONObjectTransfer){
					JSONObjectTransfer jt = (JSONObjectTransfer)obj;
					obj = jt.toJSONObject();
				}
				list.add(obj);
			}
		}
		return list;
	}
	
	/**
	 * 转换成ExtJs的GridPanel的Json数据格式
	 * <p>
	 * {success:true,totalProperty:99,root:[{},{}..]}
	 * </p>
	 * @param collection 数组或集合类型
	 * @param total 总数
	 * @param writer 写入流，为null时不写入
	 * @param config JsonConfig配置
	 * @return 转换后的json字符串
	 */
	public static String toEXTGridJson(Object collection,long total,Writer writer,JsonConfig config){
		List<Object> list = transferArray(collection);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(TOTAL_PROPERTY, total);
		jsonMap.put(ROOT, list);
		JSONObject jsonObject = config != null ? JSONObject.fromObject(jsonMap,
				config) : JSONObject.fromObject(jsonMap,JC);
		if(writer != null){
			jsonObject.write(writer);
		}
		return jsonObject.toString();
	}
}
