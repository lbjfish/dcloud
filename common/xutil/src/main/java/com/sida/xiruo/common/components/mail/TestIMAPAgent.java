package com.sida.xiruo.common.components.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>测试IMAP邮件接收</p>
 * @author Lucky
 * 1:43:32 PM Jul 22, 2009 
 */
public class TestIMAPAgent {


	private static final Log logger = LogFactory.getLog(TestIMAPAgent.class);
	
	public static void main(String[] args) {
		ISetting setting = SettingFactory.getSetting("imap");
		setting.setHostAddress("imap.gmail.com");
		setting.setUserName("pengzhoushuo@gmail.com");
		setting.setPwd("123456");
		setting.setPort(993);
		setting.setNeedSSL(true);
		IMailAgent agent = MailAgentFactory.getAgent("imap");
		agent.setReciverSetting(setting);
		try {
			agent.login("inbox");
			logger.debug("test case1");
			logger.debug(agent.fetchMailMessage());
			logger.debug("test case2-1");
			logger.debug(agent.fetchMailMessage(1 ,3));
			logger.debug("test case2-2");
			logger.debug(agent.fetchMailMessage( -100 ,300));
			logger.debug("test case2-3");
			logger.debug(agent.fetchMailMessage( -100 , -300));
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