package com.sida.xiruo.common.components.mail;

import org.apache.commons.lang.StringUtils;


/**
 * <p>邮件抓取代理工厂类，负责产生不同的邮件抓取代理</p>
 * @author Lucky
 * 6:06:04 PM Jul 21, 2009 
 */
public class MailAgentFactory {


	/**
	* <p>按协议名获取对应的MailAgent，etc. pop3 imap</p>
	* @param type protocol name
	* @return
	*/
	public static IMailAgent getAgent(String type){
		if(StringUtils.isBlank(type)){
			throw new IllegalArgumentException("type cannot be blank");
		}else if("pop3".equalsIgnoreCase(type)){
			return new POP3Agent();
		}else if("imap".equalsIgnoreCase(type)){
			return new IMAPAgent();
		}else if("yahooimap".equalsIgnoreCase(type)){
			return new YahooIMAPAgent();
		}else{ 
			throw new IllegalArgumentException(String.format("type %s is not exists", type));
		}
	}
}