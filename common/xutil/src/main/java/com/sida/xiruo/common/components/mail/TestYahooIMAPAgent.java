package com.sida.xiruo.common.components.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>测试IMAP邮件接收</p>
 * @author Lucky
 * 1:43:32 PM Jul 22, 2009 
 */
public class TestYahooIMAPAgent {


	private static final Log logger = LogFactory.getLog(TestYahooIMAPAgent.class);
	
	public static void main(String[] args) {
		ISetting setting = SettingFactory.getSetting("imap");
		setting.setHostAddress("imap.mail.yahoo.com");
		setting.setUserName("pengzhoushuo");
		setting.setPwd("123456");
		//setting.setPort(993);
		//setting.setNeedSSL(true);
		IMailAgent agent = MailAgentFactory.getAgent("yahooimap");
		agent.setReciverSetting(setting);
		try {
			//这里出现了一个问题，Yahoo系统发的第一封欢迎邮件总会导致各种错误，暂未弄清楚
			agent.login("INBOX");
//			logger.debug("test case1");
//			logger.debug(agent.fetchMailMessage());
			logger.debug("test case2-1");
			logger.debug(agent.fetchMailMessage(2 ,4));
//			logger.debug("test case2-2");
//			logger.debug(agent.fetchMailMessage( -100 ,300));
//			logger.debug("test case2-3");
//			logger.debug(agent.fetchMailMessage( -100 , -300));
			logger.debug("test case3");
			List<String> ignoreList = new ArrayList<String>();
			ignoreList.add("1");		
			logger.debug(agent.fetchMailMessage(ignoreList));


			logger.debug("test case4");
			logger.debug(agent.fetchUnSeenMailMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


}