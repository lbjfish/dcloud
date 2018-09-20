/**
 * 
 */
package com.sida.xiruo.common.util;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimeType;
import org.hibernate.type.TimestampType;

/**
 * @author xjiang
 * 
 */
public class MySQLCustomDialect extends MySQLDialect {
	public MySQLCustomDialect() {
		super();
		registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using ?2)"));
		registerFunction("convert_gbk", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using gbk)"));
		registerFunction("date_add", new SQLFunctionTemplate(DateType.INSTANCE, "date_add(?1, INTERVAL ?2 ?3)"));
		registerFunction("adddate", new SQLFunctionTemplate(DateType.INSTANCE, "ADDDATE(?1, INTERVAL ?2 ?3)"));
		registerFunction("addtime", new SQLFunctionTemplate(TimestampType.INSTANCE, "ADDTIME(?1,?2)"));
		registerFunction("date_sub", new SQLFunctionTemplate(DateType.INSTANCE, "DATE_SUB(?1, INTERVAL ?2 ?3)"));
		registerFunction("group_concat", new SQLFunctionTemplate(StringType.INSTANCE, "group_concat(?1)"));
		registerFunction("group_concat_distinct", new SQLFunctionTemplate(StringType.INSTANCE, "group_concat(distinct ?1)"));
		registerFunction("lpad", new SQLFunctionTemplate(StringType.INSTANCE, "LPAD(?1, ?2, ?3)"));
		registerFunction("replace", new SQLFunctionTemplate(StringType.INSTANCE, "REPLACE(?1, ?2, ?3)"));
		registerFunction("variance", new StandardSQLFunction("variance", DoubleType.INSTANCE) ); 
		registerFunction("stddev", new StandardSQLFunction("stddev", DoubleType.INSTANCE) );
		registerFunction("distinct", new SQLFunctionTemplate(StringType.INSTANCE, "distinct(?1)"));
		registerFunction("count_distinct_concat_ws", new VarArgsSQLFunction(IntegerType.INSTANCE, "count(distinct(concat_ws(", ",", ")))"));
		
        registerFunction("datediff", new StandardSQLFunction("datediff", IntegerType.INSTANCE) );
        registerFunction("timediff", new StandardSQLFunction("timediff", TimeType.INSTANCE) );
        registerFunction("date_format", new StandardSQLFunction("date_format", StringType.INSTANCE) );
        registerFunction("curdate", new NoArgSQLFunction("curdate", DateType.INSTANCE) );
        registerFunction("curtime", new NoArgSQLFunction("curtime", TimeType.INSTANCE) );
        registerFunction("current_date", new NoArgSQLFunction("current_date", DateType.INSTANCE, false) );
        registerFunction("current_time", new NoArgSQLFunction("current_time", TimeType.INSTANCE, false) );
        registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", TimestampType.INSTANCE, false) );
        registerFunction("date", new StandardSQLFunction("date", DateType.INSTANCE) );
        registerFunction("day", new StandardSQLFunction("day", IntegerType.INSTANCE) );
        registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", IntegerType.INSTANCE) );
        registerFunction("dayname", new StandardSQLFunction("dayname", StringType.INSTANCE) );
        registerFunction("dayofweek", new StandardSQLFunction("dayofweek", IntegerType.INSTANCE) );
        registerFunction("dayofyear", new StandardSQLFunction("dayofyear", IntegerType.INSTANCE) );
        registerFunction("from_days", new StandardSQLFunction("from_days", DateType.INSTANCE) );
        registerFunction("from_unixtime", new StandardSQLFunction("from_unixtime", TimestampType.INSTANCE) );
        registerFunction("hour", new StandardSQLFunction("hour", IntegerType.INSTANCE) );
        registerFunction("last_day", new StandardSQLFunction("last_day", DateType.INSTANCE) );
        registerFunction("localtime", new NoArgSQLFunction("localtime", TimestampType.INSTANCE) );
        registerFunction("localtimestamp", new NoArgSQLFunction("localtimestamp", TimestampType.INSTANCE) );
        registerFunction("microseconds", new StandardSQLFunction("microseconds", IntegerType.INSTANCE) );
        registerFunction("minute", new StandardSQLFunction("minute", IntegerType.INSTANCE) );
        registerFunction("month", new StandardSQLFunction("month", IntegerType.INSTANCE) );
        registerFunction("monthname", new StandardSQLFunction("monthname", StringType.INSTANCE) );
        registerFunction("now", new NoArgSQLFunction("now", TimestampType.INSTANCE) );
        registerFunction("quarter", new StandardSQLFunction("quarter", IntegerType.INSTANCE) );
        registerFunction("second", new StandardSQLFunction("second", IntegerType.INSTANCE) );
        registerFunction("sec_to_time", new StandardSQLFunction("sec_to_time", TimeType.INSTANCE) );
        registerFunction("sysdate", new NoArgSQLFunction("sysdate", TimestampType.INSTANCE) );
        registerFunction("time", new StandardSQLFunction("time", TimeType.INSTANCE) );
        registerFunction("timestamp", new StandardSQLFunction("timestamp", TimestampType.INSTANCE) );
        registerFunction("time_to_sec", new StandardSQLFunction("time_to_sec", IntegerType.INSTANCE) );
        registerFunction("to_days", new StandardSQLFunction("to_days", LongType.INSTANCE) );
        registerFunction("unix_timestamp", new StandardSQLFunction("unix_timestamp", LongType.INSTANCE) );
        registerFunction("utc_date", new NoArgSQLFunction("utc_date", StringType.INSTANCE) );
        registerFunction("utc_time", new NoArgSQLFunction("utc_time", StringType.INSTANCE) );
        registerFunction("utc_timestamp", new NoArgSQLFunction("utc_timestamp", StringType.INSTANCE) );
        registerFunction("week", new StandardSQLFunction("week", IntegerType.INSTANCE) );
        registerFunction("weekday", new StandardSQLFunction("weekday", IntegerType.INSTANCE) );
        registerFunction("weekofyear", new StandardSQLFunction("weekofyear", IntegerType.INSTANCE) );
        registerFunction("year", new StandardSQLFunction("year", IntegerType.INSTANCE) );
        registerFunction("yearweek", new StandardSQLFunction("yearweek", IntegerType.INSTANCE) );
	}
}
