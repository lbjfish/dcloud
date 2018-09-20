package com.sida.xiruo.common.components.mail;

/**
 * <p>接收设置</p>
 * @author Lucky
 * 1:31:42 PM Jul 21, 2009 
 */
public interface ISetting {


	/**
	* <p>获取用户名</p>
	* @return
	*/
	public String getUserName();
	
	/**
	* <p>设置用户名</p>
	* @param userName 用户名
	*/
	public void setUserName(String userName);
	
	/**
	* <p>获取密码</p>
	* @return
	*/
	public String getPwd();
	
	/**
	* <p>设置密码</p>
	* @param pwd
	*/
	public void setPwd(String pwd);
	
	/**
	* <p>获取服务器地址</p>
	* @return
	*/
	public String getHostAddress();
	
	/**
	* <p>设置服务器地址</p>
	* @param hostAddress
	*/
	public void setHostAddress(String hostAddress);
	
	/**
	* <p>设置服务器端口</p>
	* @param port 服务器端口
	*/
	public void setPort(int port);
	
	/**
	* <p>获取服务器端口</p>
	* @return
	*/
	public int getPort();
	
	/**
	* <p>获取是否支持SSL</p>
	* @return
	*/
	public boolean isNeedSSL();
	
	/**
	* <p>设置是否提供对SSL的支持</p>
	* @param needSSL
	*/
	public void setNeedSSL(boolean needSSL);
	
	/**
	* <p>获取协议名</p>
	* @return
	*/
	public String getProtocolName();
}
