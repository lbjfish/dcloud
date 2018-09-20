package com.sida.xiruo.common.components.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>POP3收取邮件测试</p>
 * @author Lucky
 * 6:04:12 PM Jul 21, 2009 
 */
public class TestPop3Agent {


	private static final Log logger = LogFactory.getLog(TestPop3Agent.class);
	public static void main(String[] args) {
		
		ISetting set = SettingFactory.getSetting("pop3");
		set.setUserName("lucky.peng@mozat.com.cn");
		set.setPwd("123456");//password
		set.setHostAddress("mail.mozat.com.cn");
		
		IMailAgent agent = MailAgentFactory.getAgent("pop3");
		agent.setReciverSetting(set);
		try {
			agent.login("inbox");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
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
			ignoreList.add("1tbitAZ+2EX9cfkcCwAAsz");
			ignoreList.add("1tbiqg9x2Ee9J-aehQAAs8");
			ignoreList.add("1tbiqg5w2Ee9J-IbqAAAsQ");
			ignoreList.add("1tbi4w512EkZmdaQ0QAAse");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}