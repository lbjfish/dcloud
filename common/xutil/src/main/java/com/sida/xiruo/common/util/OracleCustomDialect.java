package com.sida.xiruo.common.util;

import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

/**     
 * 
 * @author jianglfa       
 * @version 1.0     
 * @created Nov 7, 2011 10:04:56 PM    
 */

public class OracleCustomDialect extends Oracle12cDialect {
	public OracleCustomDialect() {
		super();
		registerFunction("group_concat", new SQLFunctionTemplate(StringType.INSTANCE, "wmsys.wm_concat(?1)"));
		registerFunction("wm_concat", new SQLFunctionTemplate(StringType.INSTANCE, "wmsys.wm_concat(?1)"));
		registerFunction("group_concat_distinct", new SQLFunctionTemplate(StringType.INSTANCE, "wmsys.wm_concat(distinct ?1)"));
		registerFunction("wm_concat_distinct", new SQLFunctionTemplate(StringType.INSTANCE, "wmsys.wm_concat(distinct ?1)"));
		registerFunction("trim", new SQLFunctionTemplate(StringType.INSTANCE, "trim(?1)"));
		registerFunction("regexp_like", new SQLFunctionTemplate(StringType.INSTANCE, "regexp_like(?1,?2)"));
		registerFunction("regexp_replace", new SQLFunctionTemplate(StringType.INSTANCE, "regexp_replace(?1,?2,?3)"));
	}
}
