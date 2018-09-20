package com.sida.xiruo.common.util;

import java.util.Locale;

/**     
 *      
 *     
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 10, 2011 1:02:45 PM    
 */

public final class UtilConstants {
	public final static Locale DEFAULT_LOCALE = new Locale("zh", "CN");
	public final static String DEFAULT_LOCALE_STRING = "zh_CN";
	public final static String DEFAULT_CHARSET = "UTF-8";
	
    /**
     * 分页用的信息
     */
    public final static String PAGINATION_LIST_SIZE_PARAM = "list-size";
    public final static String PAGINATION_LIST_ITEM_SIZE_PARAM = "list-item-size";
    public final static String PAGINATION_PAGE_SIZE_PARAM = "page-size";
    public final static String PAGINATION_PAGE_ITEM_SIZE_PARAM = "page-item-size";
    
    /**
     * solr设置
     */
    public final static String SOLR_SERVER_URL = "solr-server-url";
    
    /**
     * 线程池配置
     */
	public static final int DEFAULT_THREADPOOL_COREPOOLSIZE = 8;
	public static final int DEFAULT_THREADPOOL_MAXPOOLSIZE = 10;
	public static final long DEFAULT_THREADPOOL_KEEPALIVETIME = 5;
	public static final int DEFAULT_THREADPOOL_QUEUESIZE = 30;
//    public static final int DEFAULT_THREADPOOL_COREPOOLSIZE = 1;
//	public static final int DEFAULT_THREADPOOL_MAXPOOLSIZE = 1;
//	public static final long DEFAULT_THREADPOOL_KEEPALIVETIME = 1;
//	public static final int DEFAULT_THREADPOOL_QUEUESIZE = 1;
    public final static String THREADPOOL_KEEPALIVETIME = "threadpool-keepalivetime";
    public final static String THREADPOOL_COREPOOLSIZE = "threadpool-corepoolsize";
    public final static String THREADPOOL_MAXPOOLSIZE = "threadpool-maxpoolsize";
    public final static String THREADPOOL_QUEUESIZE = "threadpool-queuesize";
    
    /**
     * 
     */
    public final static String DEFAULT_DATE_BEGIN = "1900-01";
    public final static String DEFAULT_DATE_END = "2099-12";
    
    /**
     * 
     */
    public final static String COMMAND_INSERT = "insert";
    public final static String COMMAND_MODIFY = "modify";
    public final static String COMMAND_PREPARE = "prepare";
    
    /**
     * 验证码编码
     */
    public final static String CHECK_CODE_ENCODING = "check-code-encoding";
    public static enum checkCodeEncodings {
    	NUMBER("number"), CHARACTER("character"), MIXTURE("mixture"), GB("gb");
    	
    	private String encoding;
    	checkCodeEncodings(String encoding) {
    		this.encoding = encoding;
    	}
    	
    	public boolean equals(String encoding) {
    		if(encoding == null) {
    			return false;
    		}
    		
    		return encoding.equalsIgnoreCase(this.encoding);
    	}
    }
    
    public final static String REQUIRE_LOG = "require-log";
    
    /**
     * 上传文件大小限制，10M
     */
    public final static int UPLOAD_SIZE_LIMIT = 10240 * 1024;
    
    /**
     * 上传文件生成的缩略图的默认宽度和高度
     */
    public final static int UPLOAD_PREVIEW_WIDTH = 150;
    public final static int UPLOAD_PREVIEW_HEIGHT = 150;
    public static final String ALLOW_PREVIEW_TYPE = ",jpg,png,bmp,gif,tif,";
    
    /**
     * CAS参数
     */
    public final static String CONST_CAS_ASSERTION = "_const_cas_assertion_";
}
