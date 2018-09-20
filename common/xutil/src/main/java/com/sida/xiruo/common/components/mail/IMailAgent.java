package com.sida.xiruo.common.components.mail;

import java.util.List;




/**
 * <p>邮件收取代理接口</p>
 * @author Lucky
 * 1:50:28 PM Jul 21, 2009 
 */
public interface IMailAgent {


	/**
	* <p>设置接收服务器配置</p>
	* @param setting
	*/
	public void setReciverSetting(ISetting setting);
	
	/**
	* <p>登录邮件服务器</p>
	* @param folderName 目录名etc. INBOX 
	* @return <code>true</code>如果登录成功
	*/
	public boolean login(String folderName) throws Exception;
	
	/**
	* <p>按folder名收取邮件</p>
	* @return
	*/
	public List<MailMessage> fetchMailMessage() throws Exception;
	
	/**
	* <p>收取开始到结束位置之间的邮件，包括开始和结束位置</p>
	* @param start 开始位置
	* @param end 结束位置
	* @return
	*/
	public List<MailMessage> fetchMailMessage(int start , int end) throws Exception;
	
	/**
	* <p>收取邮件，不收取邮件ID在忽略ID列表范围内的邮件</p>
	* @param ignoreIdList 忽略ID列表
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchMailMessage(List<String> ignoreIdList) throws Exception;
	
	/**
	* <p>获取未读邮件</p>
	* @return
	* @throws Exception
	*/
	public List<MailMessage> fetchUnSeenMailMessage() throws Exception;
	
	/**
	* <p>关闭资源</p>
	*/
	public void close();


}