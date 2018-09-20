package com.sida.xiruo.common.components.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import com.sida.xiruo.common.components.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>邮件发送类</p>
 * @author Lucky
 * 4:33:54 PM Jul 17, 2009 
 */
public class MailSender {


	private static final Log logger = LogFactory.getLog(MailSender.class);
	
	//普通邮件发送session
	private static final Session normalSMTPSession = Session.getInstance(new Properties());;
	//SSL加密方式的邮件发送session
	private static final Session sslSMTPSession = Session.getInstance(new Properties());
	//TLS加密方式的邮件发送session
	private static final Session tlsSMTPSession = Session.getInstance(new Properties());
	
	static {
		normalSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		normalSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		
		sslSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		sslSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		
		tlsSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtp.starttls.enable", "true");
	}
	
	/**
	* <p>发送邮件</p>
	* @param setting SMTP设置
	* @param message 邮件
	* @throws Exception
	*/
	public static void sendMail(SMTPSetting setting , MailMessage message) throws Exception{
		if(StringUtils.isBlank(setting.getSmtpHost())){
			throw new java.lang.IllegalArgumentException("SMTP host can't be blank!");
		}
		//默认用普通的session
		Session mailSenderSession = normalSMTPSession;
		//协议名
		String protocolName = "smtp";
		
		if(setting.getEnc().equals(SMTPSetting.SMTPEncrypt.SSL)){//SSL
			mailSenderSession = sslSMTPSession;
			protocolName = "smtps";
			logger.trace("use SSL session");
		}else if(setting.getEnc().equals(SMTPSetting.SMTPEncrypt.TLS)){//TLS
			mailSenderSession = tlsSMTPSession;
			logger.trace("user TLS session");
		}else{
			logger.trace("user NORMAL session");
		}
		logger.trace(String.format("protocol name is %s", protocolName));
		
		//将MailMessage对象转换成JavaMail的Message对象
		Message javaMailMessage = message.toJavaMailMessage();
		
		long starttime = System.currentTimeMillis();
		//连接服务器并发送邮件
		Transport transport = mailSenderSession.getTransport(protocolName);
		transport.connect(setting.getSmtpHost(), setting.getPort(), setting.getUserName() , setting.getPwd());
		transport.sendMessage(javaMailMessage, javaMailMessage.getAllRecipients());
		transport.close();
		long endTime = System.currentTimeMillis();
		logger.trace(String.format("send mail from %s to %s successfuly [%d milliseconds]", message.getForm(), message.getTo().get(0), endTime - (starttime)));
	}


	/**
	* @return the normalSMTPSession
	*/
	public static Session getNormalSMTPSession() {
		return normalSMTPSession;
	}


	/**
	* @return the sslSMTPSession
	*/
	public static Session getSslSMTPSession() {
		return sslSMTPSession;
	}


	/**
	* @return the tlsSMTPSession
	*/
	public static Session getTlsSMTPSession() {
		return tlsSMTPSession;
	}


}
