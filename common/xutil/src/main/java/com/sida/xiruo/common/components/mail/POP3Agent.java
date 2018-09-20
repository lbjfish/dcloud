package com.sida.xiruo.common.components.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>POP3邮件收取类</p>
 * @author Lucky
 * 2:04:32 PM Jul 21, 2009 
 */
public class POP3Agent implements IMailAgent{


	private static Log logger = LogFactory.getLog(POP3Agent.class);
	 
	private static final Properties defaultProperties = new Properties();
	
	static {
		defaultProperties.setProperty("mail.pop3.connectiontimeout", "6000");
		defaultProperties.setProperty("mail.pop3.timeout", "60000");
		defaultProperties.setProperty("mail.pop3.rsetbeforequit", "true");
		defaultProperties.setProperty("mail.pop3s.connectiontimeout", "6000");
		defaultProperties.setProperty("mail.pop3s.timeout", "60000");
		defaultProperties.setProperty("mail.pop3s.rsetbeforequit", "true");
	}
	
	//接收服务器设置
	private ISetting setting = null;
	//是否已登录
	private boolean isLogin = false;
	//JavaMail POP3目录
	private Folder folder = null;


	/**
	* <p>收取邮件所有的邮件</p>
	* @return
	*/
	public List<MailMessage> fetchMailMessage() throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		if(!this.isLogin){
			throw new RuntimeException("you should login first");
		}
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		logger.trace(String.format("total mail count: %d" , folder.getMessageCount()));
		
		//收取每一封邮件
		long startTime = System.currentTimeMillis();
		Message[] msgs = folder.getMessages();		
		FetchProfile fp = new FetchProfile();
		fp.add(UIDFolder.FetchProfileItem.UID);
		folder.fetch(msgs, fp);
		//遍历并转换每一封JavaMail格式的邮件为自定义邮件格式
		List<MailMessage> mailList = new ArrayList<MailMessage>();
		for(Message msg : msgs){
			mailList.add(new MailMessage(msg));
		}
		long endTime = System.currentTimeMillis();
		logger.trace(String.format("fetch mail finished , total time [%d] millionseconds" , (endTime - startTime)));
		return mailList;
	}


	/**
	* <p>按开始及结束位置收取邮件</p>
	* @return
	*/
	public List<MailMessage> fetchMailMessage(int start , int end) throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}	
		if(!this.isLogin){
			throw new RuntimeException("you should login first");
		}
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		int totalCount = folder.getMessageCount();
		start = (start > 1)? start : 1; 
		end = totalCount < end ? totalCount : end;
		end = end > 0 ? end : 1;
		logger.trace(String.format("total mail count: %d" , totalCount));
		
		//收取每一封邮件
		long startTime = System.currentTimeMillis();
		Message[] msgs = folder.getMessages(start , end);
		FetchProfile fp = new FetchProfile();
		fp.add(UIDFolder.FetchProfileItem.UID);
		folder.fetch(msgs, fp);
		//遍历并转换每一封JavaMail格式的邮件为自定义邮件格式
		List<MailMessage> mailList = new ArrayList<MailMessage>();
		for(Message msg : msgs){
			mailList.add(new MailMessage(msg));
		}
		long endTime = System.currentTimeMillis();	
		logger.trace(String.format("fetch mail finished , total time [%d] millionseconds" , (endTime - startTime)));
		return mailList;
	}
	
	/**
	* <p>登录邮件服务器</p>
	* @param folderName 目录名，在POP3协议中只能为"INBOX"
	* @return <code>true</code>如果登录成功
	*/
	public boolean login(String folderName) throws Exception{
		this.close();
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		if(StringUtils.isBlank(folderName) || (!"INBOX".equalsIgnoreCase(folderName))){
			throw new java.lang.IllegalArgumentException("POP3 can only support folder name INBOX");
		}
		Session session = Session.getInstance(defaultProperties);
		Store store = session.getStore(this.setting.getProtocolName());
		store.connect(this.setting.getHostAddress(), this.setting.getPort(), this.setting.getUserName(), this.setting.getPwd());
		//设置Folder成员
		this.folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		logger.trace("login successfuly");
		this.isLogin = true;
		return true;
	}


	/**
	* <p>设置接收服务器配置</p>
	* @param setting
	*/
	public void setReciverSetting(ISetting setting) {
		if(setting == null || (!(setting instanceof POP3Setting))){
			throw new IllegalArgumentException("the setting format is wrong");
		}
		this.setting = setting;
	}


	/**
	* <p>按folder名收取邮件，忽略ID列表</p>
	* @param folderName 目录名etc. INBOX 
	* @param ignoreIdList 忽略ID列表
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchMailMessage(List<String> ignoreIdList) throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		if(!this.isLogin){
			throw new RuntimeException("you should login first");
		}
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		logger.trace(String.format("total mail count: %d" , folder.getMessageCount()));
		
		//收取每一封邮件
		long startTime = System.currentTimeMillis();
		Message[] msgs = folder.getMessages();	
		FetchProfile fp = new FetchProfile();
		fp.add(UIDFolder.FetchProfileItem.UID);
		folder.fetch(msgs, fp);
		//遍历并转换每一封JavaMail格式的邮件为自定义邮件格式
		List<MailMessage> mailList = new ArrayList<MailMessage>();
		for(Message msg : msgs){
			//是否忽略本邮件
			boolean ignoreThisMsg = false;
			for(String ignoreId : ignoreIdList){//遍历忽略的ID列表
				if(ignoreId.equalsIgnoreCase(JavaMailUtil.getUID(msg))){//属于忽略ID范围内的邮件
					ignoreThisMsg = true;
				}
			}
			if(!ignoreThisMsg){
				mailList.add(new MailMessage(msg));
			}
		}
		long endTime = System.currentTimeMillis();
		logger.trace(String.format("fetch mail finished , total time [%d] millionseconds" , (endTime - startTime)));
		return mailList;
	}


	/**
	* <p>获取未读邮件，在POP3的协议实现中不支持该方法</p>
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchUnSeenMailMessage() throws Exception {
		throw new java.lang.NoSuchMethodException("protocol POP3 dos not support this method");
	}
	
	/**
	* 关闭资源
	*/
	public void close() {
		
		if (folder != null) {
			try {
				if (folder.isOpen()){
					this.folder.close(false);
				}
			}  
			catch (Exception e) {
			}
			
			try {
				this.folder.getStore().close();
			} 
			catch (Exception e) {
			}
			this.folder = null;
		}
		this.isLogin = false;
	}


}