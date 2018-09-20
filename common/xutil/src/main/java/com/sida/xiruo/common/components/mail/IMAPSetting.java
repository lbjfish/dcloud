package com.sida.xiruo.common.components.mail;

/**
 * <p>IMAP接收设置</p>
 * @author Lucky
 * 1:40:34 PM Jul 21, 2009 
 */
public class IMAPSetting implements ISetting {


	private static final String NORMAL_PROTOCOL_NAME = "imap";
	
	private static final String SSL_PROTOCOL_NAME = "imaps";
	
	//用户名
	private String userName;
	//密码 
	private String pwd;
	//服务器地址
	private String hostAddress;
	//服务器端口
	private int port = 143;
	
	//是否需要SSL加密
	private boolean needSSL;
	
	/**
	* <p>获取服务器地址</p>
	* @return
	*/
	public String getHostAddress() {
		return this.hostAddress;
	}


	/**
	* <p>获取密码</p>
	* @return
	*/
	public String getPwd() {
		return this.pwd;
	}


	/**
	* <p>获取用户名</p>
	* @return
	*/
	public String getUserName() {
		return this.userName;
	}


	/**
	* <p>设置服务器地址</p>
	* @param hostAddress
	*/
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}


	/**
	* <p>设置密码</p>
	* @param pwd
	*/
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	/**
	* <p>设置用户名</p>
	* @param userName 用户名
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	* <p>获取服务器端口</p>
	* @return
	*/
	public int getPort() {
		return this.port;
	}


	/**
	* <p>设置服务器端口</p>
	* @param port 服务器端口
	*/
	public void setPort(int port) {
		this.port = port;
	}


	/**
	* <p>获取协议名</p>
	* @return
	*/
	public String getProtocolName() {
		if(this.needSSL){
			return IMAPSetting.SSL_PROTOCOL_NAME;
		}else{
			return IMAPSetting.NORMAL_PROTOCOL_NAME;
		}
	}


	/**
	* <p>获取是否支持SSL</p>
	* @return
	*/
	public boolean isNeedSSL() {
		return this.needSSL;
	}


	/**
	* <p>设置是否提供对SSL的支持</p>
	* @param needSSL
	*/
	public void setNeedSSL(boolean needSSL) {
		this.needSSL = needSSL;
		
	}
}
