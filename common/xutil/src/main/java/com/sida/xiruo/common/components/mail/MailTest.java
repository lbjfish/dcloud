package com.sida.xiruo.common.components.mail;

import java.util.ArrayList;
import java.util.List;

public class MailTest {
	public static void main(String[] args) {
		sendMailUseHotmail();
	}
	
	public static void sendMainUseGmail() {
		//SMTP设置，包括登录用户名、密码、SMTP主机地址和端口等信息
		SMTPSetting setting = new SMTPSetting();
		setting.setSmtpHost("smtp.gmail.com");
		setting.setUserName("jianglingfeng");
		setting.setPwd("xiruojiang");//换成你的密码
		setting.setPort(465);
		setting.setEnc(SMTPSetting.SMTPEncrypt.SSL);
		//邮件内容
		MailMessage message = new MailMessage();
		message.setForm("jianglingfeng:jianglingfeng@gmail.com");//发件人
		List<String> toList = new ArrayList<String>();//收件人列表
		toList.add("jianglfa@digitalchina.com");
		message.setTo(toList);
		message.setSubject("this mail send by mysmtp use gmail");//主题
		message.setBody("hello world!");//正文
		message.setPriority(3);//邮件优先级1为高,3为中,5为低，默认为中


		//发送邮件
		try {
			MailSender.sendMail(setting, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMailUseHotmail() {


		//SMTP设置，包括登录用户名、密码、SMTP主机地址和端口等信息
		SMTPSetting setting = new SMTPSetting();
		setting.setSmtpHost("smtp.live.com");
		setting.setUserName("xiruo@msn.com");//注意用户名必须是全称
		setting.setPwd("jiangling");//换成你的密码
		setting.setEnc(SMTPSetting.SMTPEncrypt.TLS);//需要TLS支持
		
		//邮件内容
		MailMessage message = new MailMessage();
		message.setForm("xiruo@msn.com");//发件人，原来HotMail还可以伪造发件人
		List<String> toList = new ArrayList<String>();//收件人列表
		toList.add("jianglfa@digitalchina.com");
		message.setTo(toList);
		message.setSubject("this mail send by mysmtp use hotmail");//主题
		message.setBody("hello world!");//正文
		message.setPriority(3);//邮件优先级1为高,3为中,5为低，默认为中


		//发送邮件
		try {
			MailSender.sendMail(setting, message);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
