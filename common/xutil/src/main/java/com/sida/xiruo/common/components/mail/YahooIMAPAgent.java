package com.sida.xiruo.common.components.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YahooIMAPAgent extends IMAPAgent {


	private static Log logger = LogFactory.getLog(YahooIMAPAgent.class);
	
	private static final Properties defaultProperties = new Properties();
	
	static {
		defaultProperties.setProperty("mail.imap.connectiontimeout", "6000");
		defaultProperties.setProperty("mail.imap.timeout", "60000");
		defaultProperties.setProperty("mail.imap.socketFactory.class", "com.uncle.mymailreciver.agent.YahooImapSocketFactory");
		defaultProperties.setProperty("mail.imap.socketFactory.fallback", "false");
	}
	
	/**
	* <p>登录邮件服务器</p>
	* @param folderName 目录名
	* @return <code>true</code>如果登录成功
	*/
	public boolean login(String folderName) throws Exception{
		super.close();
		if(super.getSetting() == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		Session session = Session.getInstance(defaultProperties);
		Store store = session.getStore(this.getSetting().getProtocolName());
		store.connect(super.getSetting().getHostAddress(), super.getSetting().getPort(), super.getSetting().getUserName(), super.getSetting().getPwd());
		//设置Folder成员
		super.setFolder(store.getFolder(folderName));
		super.getFolder().open(Folder.READ_ONLY);
		logger.trace("login successfuly");
		super.setIsLogin(true);
		return true;
	}
}
