package com.sida.xiruo.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * json-lib 转换 Date类型时的处理
 * @author Administrator
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor
{
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private DateFormat dateFormat;

	public DateJsonValueProcessor(String datePattern)
	{
		dateFormat = new SimpleDateFormat(datePattern);
	}
	public DateJsonValueProcessor()
	{
		dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
	}
	public Object processArrayValue(Object value, JsonConfig jsonConfig)
	{
		return process(value);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig)
	{
		return process(value);
	}

	private Object process(Object value)
	{
		if(null==value)
		{
			return "";
		}
		return dateFormat.format((Date) value);
	}
}