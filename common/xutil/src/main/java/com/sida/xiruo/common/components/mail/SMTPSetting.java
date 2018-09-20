package com.sida.xiruo.common.components.mail;

/**
 * SMTP设置
 * 
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 24, 2011 2:31:40 PM
 */
public class SMTPSetting {

	/** 加密协议* */
	public enum SMTPEncrypt {
		NORMAL, SSL, TLS;
	}

	// 用户名
	private String userName = "";
	// 密码
	private String pwd = "";
	// SMTP地址
	private String smtpHost;
	// 端口
	private int port = 25;
	// 加密协议
	private SMTPEncrypt enc = SMTPEncrypt.NORMAL;

	/**
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param smtpHost
	 *            SMTP地址
	 * @param port
	 *            端口
	 * @param enc
	 *            加密协议
	 */
	public SMTPSetting(String userName, String pwd, String smtpHost, int port,
			SMTPEncrypt enc) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.port = port;
		this.enc = enc;
	}

	/**
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param smtpHost
	 *            SMTP地址
	 * @param port
	 *            端口
	 */
	public SMTPSetting(String userName, String pwd, String smtpHost, int port) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.port = port;
	}

	/**
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param smtpHost
	 *            SMTP地址
	 * @param enc
	 *            加密协议
	 */
	public SMTPSetting(String userName, String pwd, String smtpHost,
			SMTPEncrypt enc) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.enc = enc;
	}

	/**
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param smtpHost
	 *            SMTP地址
	 */
	public SMTPSetting(String userName, String pwd, String smtpHost) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
	}

	public SMTPSetting() {
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the smtpHost
	 */
	public String getSmtpHost() {
		return smtpHost;
	}

	/**
	 * @param smtpHost
	 *            the smtpHost to set
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the enc
	 */
	public SMTPEncrypt getEnc() {
		return enc;
	}

	/**
	 * @param enc
	 *            the enc to set
	 */
	public void setEnc(SMTPEncrypt enc) {
		this.enc = enc;
	}
}
