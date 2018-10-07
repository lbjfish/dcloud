package com.sida.xiruo.common.util;

import org.omg.CORBA.TIMEOUT;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xiruo {
	private final static String DEFAULT_ENCODING = "UTF-8";

	public static String replace(String sourceString, String oldString,
			String newString) {
		int i = sourceString.indexOf(oldString);
		StringBuffer stringbuffer = new StringBuffer();
		if (i == -1)
			return sourceString;
		stringbuffer.append(sourceString.substring(0, i) + newString);
		if (i + oldString.length() < sourceString.length())
			stringbuffer.append(replace(sourceString.substring(i
					+ oldString.length(), sourceString.length()), oldString,
					newString));
		return stringbuffer.toString();
	}

	public static String[] split(String sourceString, String splitString) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(
				sourceString, splitString);
		String[] s = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			s[i] = st.nextToken();
			i++;
		}
		return s;
	}

	public static int nullToInt(Object obj) {
		return nullToInt(obj + "");
	}

	public static int nullToInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static Object nullToObject(String s, Class cls) {
		try {
			Constructor cons = cls.getConstructor(new Class[] { String.class });
			return cons.newInstance(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long nullToLong(Object obj) {
		return nullToLong(obj + "");
	}

	public static long nullToLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double nullToDouble(Object obj) {
		return nullToDouble(obj + "");
	}

	public static double nullToDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean nullToBoolean(String s) {
		return s != null;
	}

	public static String nullToEmpty(Object obj) {
		return obj == null ? "" : String.valueOf(obj).trim();
	}

	public static String toGBString(String s) {
		try {
			if (s == null)
				return "";
			return new String(s.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String toEncodeString(String s, String enc) {
		try {
			return java.net.URLEncoder.encode(s, enc);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String toEncodeString(String s) {
		return toEncodeString(s, DEFAULT_ENCODING);
	}

	public static String toDecodeString(String s, String enc) {
		try {
			return java.net.URLDecoder.decode(s, enc);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String toDecodeString(String s) {
		return toDecodeString(s, DEFAULT_ENCODING);
	}

	public static String filterString(String oldString) {
		return replace(oldString, "'", "''");
	}

	public static String getSubString(String oldString, int start) {
		int ender;
		String newString;
		ender = oldString.length();
		newString = oldString.substring(start, ender);
		return newString;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().equals("");
	}

	public static boolean isEmpty(Object object) {
		return isEmpty((String) object);
	}
	
	public static Date stringToDate(String s) {
		return stringToDate(s, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date stringToDate(String s, String formatString) {
		try {
			return new java.text.SimpleDateFormat(formatString).parse(s);
		} catch (Exception e) {
			System.out.println("source date string: " + s);
			return null;
		}
	}

	public static Calendar stringToCalendar(String s, String formatString) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(s, formatString));
		return cal;
	}

	public static Map<String, java.lang.reflect.Method> refClassMethod(
			Class refClass) {
		Map<String, java.lang.reflect.Method> methodMap = new HashMap<String, java.lang.reflect.Method>();
		java.lang.reflect.Method[] methods = refClass.getMethods();
		for (int i = 0; i < methods.length; i++)
			methodMap.put(methods[i].getName(), methods[i]);
		return methodMap;
	}

	public static String formatDate(String dateString, String formatString) {
		int len = dateString.length();
		if (formatString.equals("year")) {
			if (len >= 4)
				return dateString.substring(0, 4) + "年";
		}
		if (formatString.equals("month")) {
			if (len >= 6)
				return dateString.substring(0, 4) + "年"
						+ dateString.substring(4, 6) + "月";
		}
		if (formatString.equals("day")) {
			if (len >= 8)
				return dateString.substring(0, 4) + "年"
						+ dateString.substring(4, 6) + "月"
						+ dateString.substring(6, 8) + "日";
		}
		return dateString;
	}

	public static String formatDate(Object dateStr) {
		String dateString = dateStr.toString();
		String s = "";
		switch (dateString.length()) {
		case 4:
			s = formatDate(dateString, "year");
			break;
		case 6:
			s = formatDate(dateString, "month");
			break;
		case 8:
			s = formatDate(dateString, "day");
			break;
		}
		return s;
	}

	public static String dateToString(long m) {
		return dateToString(m, "yyyy-MM-dd HH:mm:ss");
	}

	public static String dateToString(long m, String formatString) {
		return new java.text.SimpleDateFormat(formatString)
				.format(new java.util.Date(m));
	}
	
	public static String dateToString(Date d) {
		if(d == null) {
			return "";
		}
		return dateToString(d.getTime());
	}

	public static String dateToString(Date d, String formatString) {
		if(d == null) {
			return "";
		}
		return dateToString(d.getTime(), formatString);
	}

	public static String nullToDate(Date d, String formatString) {
		String date = "";
		if (d == null)
			return "";
		try {
			date = new java.text.SimpleDateFormat(formatString).format(d);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return date;
	}

	public static double transferStringToDouble(String sourceString,
			String splitString) {
		if (sourceString.indexOf(splitString) == -1)
			return nullToDouble(sourceString);
		String[] s = split(sourceString, splitString);
		return nullToDouble(s[0]);
	}

	public static String formatNumber(String srcStr, int nAfterDot) {
		if (srcStr == null || srcStr.equals(""))
			srcStr = "0";
		double d = nullToDouble(srcStr);
		char c[] = new char[nAfterDot];
		java.util.Arrays.fill(c, '0');
		java.text.DecimalFormat dformat = new java.text.DecimalFormat("0."
				+ new String(c));
		return dformat.format(d);
	}

	public static String formatNumber(double srcStr, int nAfterDot) {
		double d = srcStr;
		char c[] = new char[nAfterDot];
		java.util.Arrays.fill(c, '0');
		java.text.DecimalFormat dformat = new java.text.DecimalFormat("0."
				+ new String(c));
		return dformat.format(d);
	}

	public static String insertSingleQuot(String sourceString) {
		String regString = "([^,]+)";
		return sourceString.replaceAll(regString, "'$1'");
	}

	public static List arrayToList(Object[] objArr) {
		if (objArr == null) {
			return null;
		}

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < objArr.length; i++) {
			list.add(objArr[i]);
		}
		return list;
	}

	public static String arrayToString(Object[] objArr) {
		if (objArr == null)
			return "";
		StringBuffer buffer = new StringBuffer("''");
		for (int i = 0; i < objArr.length; i++)
			buffer.append(",'" + objArr[i] + "'");
		return buffer.toString();
	}
	
	public static String arrayToString(Collection collection) {
		if(collection == null) {
			return "";
		}
		return arrayToString(collection.toArray());
	}
	
	public static String arrayToStringNoQuote(Collection collection) {
		if(collection == null) {
			return "";
		}
		return arrayToStringNoQuote(collection.toArray());
	}

	public static String arrayToStringNoQuote(Object[] objArr) {
		if (objArr == null)
			return "";
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < objArr.length; i++) {
			if(i > 0) {
				buffer.append(",");
			}
			buffer.append(objArr[i]);
		}
		return buffer.toString();
	}

	public static String listToString(List list) {
		if (list == null)
			return "";
		StringBuffer buffer = new StringBuffer("''");
		for (int i = 0; i < list.size(); i++)
			buffer.append(",'" + list.get(i) + "'");
		return buffer.toString();
	}

	public static String arrayToString(int[] intArr) {
		if (intArr == null)
			return "0";
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < intArr.length; i++) {
			if(i > 0) {
				buffer.append(",");
			}
			buffer.append(intArr[i] + "");
		}
		return buffer.toString();
	}

	public static java.sql.Timestamp getSqlNow() {
		return new java.sql.Timestamp(new Date().getTime());
	}

	public static String getNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getDay() {
		return new SimpleDateFormat("E").format(new Date());
	}

	public static java.sql.Date getSqlToday() {
		return new java.sql.Date(new Date().getTime());
	}

	public static String getToday() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getTomonth() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}

	public static String getToyear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		try {
			for (int i = 0; i < b.length; i++) {
				// ��ΪtoHexString()���������b[i]�ȱ�ת��Ϊ32λ�����ͣ�
				// ����������Ҫһ�������㣬
				// ����ת��32λ����Ϊ8λ��1���ֽڣ����޷�����͡�
				stmp = Integer.toHexString(b[i] & 0XFF);
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
		} catch (Exception e) {
			System.err.println("HandleString.byte2hex Error��" + e.getMessage());
		}
		return hs;
	}

	public static byte[] hex2byte(String str) {
		int iLen = str.length();
		byte[] b = new byte[iLen / 2];
		String stmp = "";
		try {
			for (int i = 0; i < b.length; i++) {
				stmp = str.substring(i * 2, i * 2 + 2);
				b[i] = Integer.valueOf(stmp, 16).byteValue();
			}
		} catch (Exception e) {
			System.err.println("HandleString.hex2byte Error��" + e.getMessage());
		}
		return b;
	}

	public static String htmlEncode(String sourceString) {
		String s = sourceString;
		s = s.replaceAll("[&]", "&amp;");
		s = s.replaceAll("[<]", "&lt;");
		s = s.replaceAll("[>]", "&gt;");
		s = s.replaceAll("[\\\"]", "&quot;");

		return s;
	}

	public static String htmlDecode(String sourceString) {
		String s = sourceString;
		s = s.replaceAll("\r\n", "<br/>");
		s = s.replaceAll("[\n\r]", "<br/>");
		s = s.replaceAll(" ", "&nbsp;");

		return s;
	}

	public static String killSpaceBR(String source) {
		String temp = Xiruo.nullToEmpty(source);
		temp = temp.trim().replaceAll("[\\s]|[\\\r]|[\\\n]|[\\\r\\\n]", "");
		return temp;
	}

	public static String getContent(String s, int len) {
		if (s == null) {
			return "";
		}
		String temp = s.replaceAll("<[^>]*?>", "");
		if (temp.getBytes().length >= len) {
			temp = substring(temp, len - 3) + "...";
		}

		return temp;
	}

	public static long getCalendarField(long l, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(l);
		return calendar.get(field);
	}

	public static boolean isChiness(String s) {
		String pattern = "[u4e00-u9fa5]+";
		Pattern p = Pattern.compile(pattern);
		Matcher result = p.matcher(s);
		return result.find();
	}

	public static boolean hasInvalidChar(String inStr) {
		if (inStr.getBytes().length != inStr.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2008-6-17
	 * @param source
	 * @param len
	 * @return
	 * @see
	 */
	public static String substring(final String source, final int len) {
		if (source == null) {
			return "";
		}

		final byte[] temp = source.getBytes();
		if (len >= temp.length) {
			return source;
		}

		final byte[] byteArray = new byte[len];
		int ii = 0;
		for (int i = 0; i < len; i++) {
			byteArray[i] = temp[i];

			if (temp[i] < 0) {
				ii++;
			}
		}

		if (ii % 2 == 1) {
			byteArray[len - 1] = ' ';
		}

		return new String(byteArray).trim();
	}

	public static boolean checkEmail(final String email) {
		if(email == null) {
			return false;
		}
		final Pattern pattern = Pattern.compile(
				"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
						| Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	public static boolean checkMobile(final String mobile) {
		final Pattern pattern = Pattern.compile("^1\\d{10}$",
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
						| Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(mobile);
		return matcher.find();
	}
	
	public static boolean checkAccount(final String account) {
		final Pattern pattern = Pattern.compile("^[^\\d]+.*$",
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
						| Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(account);
		return matcher.find();
	}

	/**
	 * 支持手机号码,电子邮箱作为账号
	 * @param account
	 * @return
	 */
	public static boolean checkMulAccount(final String account) {
		return checkMobile(account) || checkEmail(account) || checkAccount(account);
	}

	public static String killAngleBrackets(String source) {
		return nullToEmpty(source).replaceAll("<[^>]*?>", "");
	}

	public static String toUTFString(String s) {
		try {
			if (s == null)
				return "";
			return new String(s.getBytes("ISO8859_1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static double rand(double min, double max) {
		double randNum = Math.floor(Math.random() * (max - min + 1)) + min;
		return randNum;
	}
	
	public static String utfToGBString(String s) {
		try {
			if (s == null)
				return "";
			return new String(s.getBytes("UTF-8"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String transferSolrChar(String source) {
//		+ - && || ! ( ) { } [ ] ^ ” ~ * ? : \
		source = source.replaceAll("\\\\", "\\\\\\\\");
		source = source.replaceAll("-", "\\-");
		source = source.replaceAll("\\&\\&", "\\\\&\\\\&");
		source = source.replaceAll("\\|\\|", "\\\\|\\\\|");
		source = source.replaceAll("\\!", "\\\\!");
		source = source.replaceAll("\\(", "\\\\(");
		source = source.replaceAll("\\)", "\\\\)");
		source = source.replaceAll("\\{", "\\\\{");
		source = source.replaceAll("\\}", "\\\\}");
		source = source.replaceAll("\\[", "\\\\[");
		source = source.replaceAll("\\]", "\\\\]");
		source = source.replaceAll("\\^", "\\\\^");
		source = source.replaceAll("\"", "\\\"");
		source = source.replaceAll("\\~", "\\\\~");
		source = source.replaceAll("\\*", "\\\\*");
		source = source.replaceAll("\\?", "\\\\?");
		source = source.replaceAll("\\:", "\\\\:");
		
		
		return source;
	}
	
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
    
    public static String generateRandomCode(int len) {
    	StringBuilder builder = new StringBuilder();
    	Random rand = new Random();
    	for(int i = 0; i < len; i++) {
    		builder.append(rand.nextInt(10));
    	}
    	return builder.toString();
    }

	public static String getFormatDateString(String format) {
		return dateToString(new Date(), format);
	}
    
    public static void main(String args[]) {
    	System.out.println(generateRandomCode(4));
    }

    public static String secondToDesc(long seconds) {
		long temp = 0;
		StringBuffer sb = new StringBuffer();
		temp = seconds / 3600;
		sb.append(temp < 10 ? "0" : "").append(temp).append("时");

		temp = seconds % 3600 / 60;
		sb.append(temp < 10 ? "0" : "").append(temp).append("分");

		temp = seconds % 3600 % 60;
		sb.append(temp < 10 ? "0" : "").append(temp).append("秒");

		return sb.toString();
	}

	public static <T> T createInstance(T entity) {
		try {
			Type superClass = entity.getClass().getGenericSuperclass();
			Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
			Class<?> clazz = getRawType(type);
			return (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// type不能直接实例化对象，通过type获取class的类型，然后实例化对象
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type rawType = parameterizedType.getRawType();
			return (Class) rawType;
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			return Array.newInstance(getRawType(componentType), 0).getClass();
		} else if (type instanceof TypeVariable) {
			return Object.class;
		} else if (type instanceof WildcardType) {
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		} else {
			String className = type == null ? "null" : type.getClass().getName();
			throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
		}
	}

	/**
	 * a,b,c -> "a","b","c"
	 * @param source
	 * @return
	 */
	public static String insertDoubleQuoteToString(String source) {
		return insertChToString(source, "\"");
	}

	/**
	 * a,b,c -> 'a','b','c'
	 * @param source
	 * @return
	 */
	public static String insertSingleQuoteToString(String source) {
		return insertChToString(source, "'");
	}

	/**
	 *
	 * @param source
	 * @param ch
	 * @return
	 */
	public static String insertChToString(String source, String ch) {
    	return source.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", ch);
	}

	/**
	 * 到达某个时间点还有？秒
	 * 1. 到达下一分钟
	 * 2. 到达下一小时
	 * 。。。
	 */
	public static enum TIMEPOINT {
		MINUTE,
		HOUR,
		DAY,
		WEEK,
		MONTH,
		YEAR;

		public static String concatAll() {
			return Arrays.stream(values()).map(value -> value.name()).reduce((value1, value2) -> String.format("%s,%s", value1, value2)).get();
		}
	}

	/**
	 *
	 * @param timepoint
	 * @return
	 */
	public static long getLiveTime(TIMEPOINT timepoint) {
		LocalDateTime datetime = LocalDateTime.now();
		//天数
		int dayCount = 0;
		long liveTime = 0;
		switch (timepoint) {
			case YEAR:
				if(dayCount == 0) {
					int lastDayInYear = datetime.with(TemporalAdjusters.lastDayOfYear()).getDayOfYear();
					dayCount = lastDayInYear - datetime.getDayOfYear();
				}
			case MONTH:
				if(dayCount == 0) {
					int lastDayInMonth = datetime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
					dayCount = lastDayInMonth - datetime.getDayOfMonth();
				}
			case WEEK:
				if(dayCount == 0) {
					dayCount = 7 - datetime.getDayOfWeek().getValue();
				}
			case DAY:
				liveTime += (dayCount * 24 + (23 - datetime.getHour())) * 3600;
			case HOUR:
				liveTime += (59 - datetime.getMinute()) * 60;
			case MINUTE:
				liveTime += 59 - datetime.getSecond();
				break;
			default:
				throw new RuntimeException(String.format("timepoint must be one of enum TIMEPOINT [%s]", TIMEPOINT.concatAll()));
		}
		return liveTime;
	}
}
