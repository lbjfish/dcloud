package com.sida.xiruo.common.components.mail;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;


/**
 * <p>字符串操作类</p>
 * @author Lucky
 * 4:38:05 PM Jul 21, 2009 
 */
public class StringUtil {


	private static final Log logger = LogFactory.getLog(StringUtil.class);
	
	/**
	* <p>将HTML标签移除</p>
	* @param html HTML源码
	* @param charset 编码
	* @return
	*/
	public static String html2Text(String html, String charset) {
		html=javascript2Blank(html);
		return html2Text(html, new TextExtractingVisitor(), charset);
	}
	
	/**
	* <p>去除HTML中的jS及CSS代码</p>
	* @param html HTML源码
	* @return
	*/
	public static String javascript2Blank(String html){
		if(html==null||html.length()<1){
			return html;
		}
		//封装所有需要给替换成空串的正则表达式
		ArrayList<String> replacePatternStrList=new ArrayList<String>();
		replacePatternStrList.add("<SCRIPT.*?</SCRIPT>");
		replacePatternStrList.add("<script.*?</script>");
		replacePatternStrList.add("<STYLE.*?</STYLE>");
		replacePatternStrList.add("<style.*?</style>");
		
		//循环需替换的字符串列表，把每个都替换成空串
		for(String currentReplacePatternSt : replacePatternStrList){
			try{
				Pattern currentPattern=Pattern.compile(currentReplacePatternSt,Pattern.DOTALL);
				Matcher matcher=currentPattern.matcher(html);
				while(matcher.find()){
					html=html.replace(matcher.group(), "");
				}	
			}catch(Exception e){
				logger.error(e);
			}
		}
		return html;	
	}
	
	//用HtmlParser移除HTML标签
	protected static String html2Text(String html, TextExtractingVisitor visitor, String charset) {
		if (StringUtils.isEmpty(html))
			return "";
		try {
			Parser parser = Parser.createParser(html, charset);
			parser.visitAllNodesWith(visitor);
			String str= visitor.getExtractedText();
			return str;
		} catch (ParserException e) {
			logger.trace("html parse error", e);
			return html;
		}
	}
}
