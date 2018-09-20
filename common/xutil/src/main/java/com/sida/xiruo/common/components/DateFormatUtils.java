package com.sida.xiruo.common.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	/** default DateTime format* */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** default Date format* */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	private static ThreadLocal<SimpleDateFormat> dateTimeThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_TIME_FORMAT);
		}
	};

	private static ThreadLocal<SimpleDateFormat> dateThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		}
	};

	public static DateFormat getDefaultTimeFormat() {
		return dateTimeThreadLocal.get();
	}

	public static DateFormat getDefaultDateFormat() {
		return dateThreadLocal.get();
	}

	public static String format(DateFormat formatter, Date date,
			String defaultNullValue) {
		if (date == null) {
			return defaultNullValue;
		} else {
			return formatter.format(date);
		}
	}

	public static String format(DateFormat formatter, Date date) {
		return format(formatter, date, "null");
	}

	public static String defaultFormat(Date date) {
		return getDefaultTimeFormat().format(date);
	}

	public static Date parse(DateFormat formatter, String str)
			throws ParseException {
		return formatter.parse(str);
	}

	public static Date parse(DateFormat formatter, String str, Date defaultDate) {
		try {
			return parse(formatter, str);
		} catch (ParseException e) {
			return defaultDate;
		}
	}
}
