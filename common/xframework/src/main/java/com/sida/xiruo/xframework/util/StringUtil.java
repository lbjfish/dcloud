package com.sida.xiruo.xframework.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类，继承自apache的StringUtils
 * @author
 *
 */
public class StringUtil extends StringUtils {

	static final char SBC_CHAR_END = 65374; // 全角～

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	static final char SBC_SPACE = 12288; // 全角空格 12288

	static final char SBC_CHAR_START = 65281; // 全角！
	static final char DBC_SPACE = ' '; // 半角空格
	/**
	 * 转换为小写
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		
		return str.toLowerCase();
	}

	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstLetter(String str) {
		if(str!=null && str.length()>0) {
			return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		} else {
			return str;
		}
	}

	/**
	 * 判断list是否为空
	 * <li>空：true</li>
	 * <li>非空：false</li>
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List<?> list) {
		if (list == null) {
			return true;
		}
		if (list.size() == 0) {
			return true;
		}
		
		return false;
	}
	/**
	 * 判断list是否为空
	 * <li>非空：true</li>
	 * <li>空：false</li>
	 * @param list
	 * @return
	 */
	public static boolean isListNotEmpty(List<?> list) {
		return !isListEmpty(list);
	}
	
	/**
	 * 将map中取出的string/int转化为int
	 * @param obj
	 * @return
	 */
	public static Integer object2Integer(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Integer.parseInt((String)obj);
		} else if (obj instanceof Integer) {
			return (Integer)obj;
		} else {
			return null;
		}
		
	}

	/**
	 * 替换车牌号中的中文，以便作为map的key值存在
	 * 因为map若用含中文的字段做key，容易产生异常
	 * @param str
	 * @return
	 */
	public static String replaceChinese(String str) {
		if (BlankUtil.isNotEmpty(str)){
			return str.replace("粤","Y").replace("学","X");
		}
		return null;
	}

	/**
	 * 处理字符串
	 * @param str
	 * @return
	 */
	public static String toString(String str) {
		if (BlankUtil.isNotEmpty(str)){
			return str;
		}
		return "";
	}

	/**
	 * 处理字符串
	 * @param i
	 * @return
	 */
	public static String toString(Integer i) {
		if (i==null || String.valueOf(i).trim().equals("")){
			return "0";
		}
		return i.toString();
	}

	/**
	 * 处理字符串
	 * @param b
	 * @return
	 */
	public static String toString(BigDecimal b) {
		if (b==null || String.valueOf(b).trim().equals("")){
			return "0.00";
		}
		return b.toString();
	}
	
	/**
	 * 将map中取出的string/double转化为double
	 * @param obj
	 * @return
	 */
	public static Double object2Double(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Double.parseDouble((String)obj);
		} else if (obj instanceof Double) {
			return (Double)obj;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将map中取出的string/long转化为long
	 * @param obj
	 * @return
	 */
	public static Long object2Long(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Long.parseLong((String) obj);
		} else if (obj instanceof Long) {
			return (Long)obj;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将map中取出的值转化为string
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj) {
		if (obj instanceof String) {
			return (String)obj;
		} else if (obj instanceof Double) {
			return Double.toString((Double)obj);
		} else if (obj instanceof Integer) {
			return Integer.toString((Integer)obj);
		} else if (obj instanceof Long) {
			return Long.toString((Long)obj);
		} else {
			return null;
		}
		
	}
	/***
	 * json字符串数组转化成list
	 * @return
	 */
	public static List<Map> jsonStrToList(String jsonArrayStr){
		String jsonStr="";
		if("".equals(jsonArrayStr)||jsonArrayStr==null){
			return null;
		}
		if(jsonArrayStr.startsWith("")&&jsonArrayStr.endsWith("")){
			jsonStr=jsonArrayStr.substring(1,jsonArrayStr.length()-1);
		}
		if(jsonStr.startsWith("[")&&jsonStr.endsWith("]")){
			return JSON.parseArray(jsonStr, Map.class);
		}else {
			return null;
		}

	}


	/**
	 * 下划线转驼峰
	 * @param str
	 * created by hbd 20160722
	 * @return
	 */
	public static String underlineToHump(String str, String sign) {
		if(sign==null || sign.length()<=0) {
			return null;
		}
		if(str != null && str.length()>0) {
			String[] strings = str.split(sign);
			StringBuffer hump = new StringBuffer();
			for(int i=0; i<strings.length; i++) {
				hump.append(upperCaseFirstLetter(strings[i]));
			}
			return hump.toString();
		}
		return null;
	}

	/**
	 * 驼峰转下划线
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append("_");
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰转下划线
	 * @param humpString
	 * created by hbd 20160722
	 * @return
	 */
	public static String humpToUnderline(String humpString) {
		if(BlankUtil.isEmpty(humpString)) return "";
		String regexStr = "[A-Z]";
		Matcher matcher = Pattern.compile(regexStr).matcher(humpString);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String g = matcher.group();
			matcher.appendReplacement(sb, "_" + g.toLowerCase());
		}
		matcher.appendTail(sb);
		if (sb.charAt(0) == '_') {
			sb.delete(0, 1);
		}
		return sb.toString();
	}
	/***************************************
	 *
	 * 全角转换成半角
	 * @param原始字符串
	 * @return 转换后的字符串
	 *
	 ***************************************/
	public static String QtoB(String src) {

		if (src == null||src.equals("")) {
			return src;
		}
		StringBuilder buf = new StringBuilder(src.length());
		char[] ca = src.toCharArray();
		for (int i = 0; i < src.length(); i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // 如果是全角空格
				buf.append(DBC_SPACE);
			} else { // 不处理全角空格，全角！到全角～区间外的字符
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}
	public static  void main(String [] args){
      System.out.println(QtoB("下围村１９号５０２"));
	}

}
