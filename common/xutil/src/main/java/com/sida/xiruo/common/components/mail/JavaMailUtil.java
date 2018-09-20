package com.sida.xiruo.common.components.mail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.apache.commons.lang.StringUtils;


import com.sun.mail.pop3.POP3Folder;


/**
 * <p>封装了javamail基本操作</p>
 * @author Lucky
 * 10:32:00 AM Jul 22, 2009 
 */
public class JavaMailUtil {


	public static String getUID(Message msg) {
		try {
			if (msg.getFolder() instanceof POP3Folder)
				return ((POP3Folder)msg.getFolder()).getUID(msg);
			if (msg.getFolder() instanceof UIDFolder)
				return String.valueOf(((UIDFolder)msg.getFolder()).getUID(msg));
			return getMessageId(msg);
		} catch (MessagingException e) {
			return "";
		}
	}
	public static String getMessageId(Message msg) throws MessagingException {
		if (msg instanceof MimeMessage)
			return ((MimeMessage)msg).getMessageID();
		return StringUtils.defaultString((String)getOne(msg.getHeader("Message-ID")));
	}
	
	public static InternetAddress toInternetAddress(Address address) {
		if (address == null)
			return null;
		else if (address instanceof InternetAddress)
			return (InternetAddress)address;
		else {
			try {
				return new InternetAddress(address.toString(), false);
			} catch (AddressException e) {
				return null;
			}
		}
	}
	
	public static Object getOne(Object[] objs) {
		if (objs != null && objs.length > 0)
			return objs[0];
		else
			return null;
	}
}
