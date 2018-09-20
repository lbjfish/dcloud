package com.sida.xiruo.common.components.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IMAPAgent implements IMailAgent{


	private static Log logger = LogFactory.getLog(IMAPAgent.class);
	
	private static final Properties defaultProperties = new Properties();
	
	static {
		defaultProperties.setProperty("mail.imap.connectiontimeout", "6000");
		defaultProperties.setProperty("mail.imap.timeout", "60000");
		defaultProperties.setProperty("mail.imaps.connectiontimeout", "6000");
		defaultProperties.setProperty("mail.imaps.timeout", "60000");
	}
	
	//接收服务器设置
	private ISetting setting = null;
	//是否已登录
	private boolean isLogin = false;
	//JavaMail POP3目录
	private Folder folder = null;


	protected ISetting getSetting(){
		return this.setting;
	}
	
	protected boolean getIsLogin(){
		return this.isLogin;
	}
	
	protected void setIsLogin(boolean isLogin){
		this.isLogin = isLogin;
	}
	
	protected Folder getFolder(){
		return this.folder;
	}
	
	protected void setFolder(Folder folder){
		this.folder = folder;
	}
	
	/**
	* <p>收取所有的邮件</p>
	* @return
	*/
	public List<MailMessage> fetchMailMessage() throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
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
			logger.trace(JavaMailUtil.getUID(msg));
			mailList.add(new MailMessage(msg));
		}
		long endTime = System.currentTimeMillis();
		logger.trace(String.format("fetch mail finished , total time [%d]" , (endTime - startTime)));
		return mailList;
	}


	/**
	* <p>收取开始到结束位置之间的邮件，包括开始和结束位置</p>
	* @param start 开始位置
	* @param end 结束位置
	* @return
	*/
	public List<MailMessage> fetchMailMessage(int start , int end) throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		start = (start > 1)? start : 1; 
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		int totalCount = folder.getMessageCount();
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
		logger.trace(String.format("fetch mail finished , total time [%d]" , (endTime - startTime)));
		return mailList;
	}
	
	/**
	* <p>登录邮件服务器</p>
	* @param folderName 目录名
	* @return <code>true</code>如果登录成功
	*/
	public boolean login(String folderName) throws Exception{
		this.close();
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		Session session = Session.getInstance(defaultProperties);
		Store store = session.getStore(this.setting.getProtocolName());
		store.connect(this.setting.getHostAddress(), this.setting.getPort(), this.setting.getUserName(), this.setting.getPwd());
		//设置Folder成员
		this.folder = store.getFolder(folderName);
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
		if(setting == null || (!(setting instanceof IMAPSetting))){
			throw new IllegalArgumentException("the setting format is wrong");
		}
		this.setting = setting;
	}


	/**
	* <p>收取邮件，如果邮件ID号在忽略ID列表里，则不收取该邮件</p>
	* @param ignoreIdList 忽略ID列表
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchMailMessage(List<String> ignoreIdList) throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		logger.trace(String.format("total mail count: [%d]" , folder.getMessageCount()));
		
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
		logger.trace(String.format("fetch mail finished , total time [%d]" , (endTime - startTime)));
		return mailList;
	}
	
	/**
	* <p>获取未读邮件</p>
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchUnSeenMailMessage() throws Exception {
		if(this.setting == null){
			throw new IllegalArgumentException("setting can not be null");
		}
		//登录超时
		if(this.folder == null || !folder.isOpen()){
			throw new RuntimeException("may be it's timeout , login again please");
		}
		logger.trace(String.format("total mail count: %d" , folder.getMessageCount()));
		//查找未读邮件列表
		Message[] msgs = folder.search( new AndTerm(new SearchTerm[]{
				new FlagTerm(new Flags(Flags.Flag.SEEN), false)
		}
		));
		FetchProfile fp = new FetchProfile();
		fp.add(UIDFolder.FetchProfileItem.UID);
		folder.fetch(msgs, fp);
		List<MailMessage> newMsgs = new ArrayList<MailMessage>();
		for(Message msg : msgs){
			newMsgs.add(new MailMessage(msg));
		}
		return newMsgs;
	}


	/**
	* <p>关闭资源</p>
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
