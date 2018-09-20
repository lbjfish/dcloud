/**
 * 
 */
package com.sida.xiruo.common.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiruo.Jiang
 *
 */
public final class DiscuzService extends AbstractUtil implements Runnable {
	private static final int DEFAULT_SLEEP_MILLISECONDS = 30000;
	private static final String SIGN_PAGE = "/plugin.php";//签到页
	private static final String LOGIN_PAGE = "/member.php?mod=logging&action=login&loginsubmit=yes";//登录页
	private static final String LOGOUT_PAGE = "/member.php?mod=logging&action=logout";//注销
	private static final String DISPLAY_PAGE = "/home.php?mod=task";//刷新页
	private static final String MAIN_PAGE = "/forum.php";//主页
	private static final Pattern SERVER_TIME_PATTERN = Pattern.compile("GMT\\+8, ([0-9\\-\\:\\s]+?)<span id\\=\"debuginfo\">", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern SCORE_PATTERN = Pattern.compile("(积分 \\d+?, 距离下一级还需 \\d+? 积分)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern SCORE_PATTERN_ = Pattern.compile("(积分: \\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern FORMHASH_PATTERN = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\"([^\"]+?)\" />", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern CLOSE_PATTERN = Pattern.compile("<p>论坛关闭.</p>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	private String signPage;
	private String loginPage;
	private String logoutPage;
	private String displayPage;
	private boolean threadRunStatus = true;
	private Calendar serverCalendar;
	private String formHash;
	private String encoding;
	/**
	 * @author Xiruo.Jiang
	 * @date 2012-11-29
	 * @param args
	 */
	public static void main(String[] args) {
		DiscuzService instance = new DiscuzService("www.ysh51.com", 80);
		instance.username = "xiruo";
		instance.password = "xiruojiang812611";
		instance.initHttpClient();
		instance.startService();
		
		instance = new DiscuzService("www.ysh51.com", 80);
		instance.username = "wsgzxl";
		instance.password = "12345678";
		instance.initHttpClient();
		instance.startService();
//		
//		instance = new DiscuzService("www.518getwet.com", 80, "big5");
//		instance.initHttpClient();
//		instance.startService();
	}
	
	private DiscuzService(String host, int port) {
		this(host, port, "GBK");
	}
	
	private DiscuzService(String host, int port, String encoding) {
		this.host = host;
		this.port = port;
		this.encoding = encoding;
		signPage = "http://" + host + SIGN_PAGE;
		loginPage = "http://" + host + LOGIN_PAGE;
		logoutPage = "http://" + host + LOGOUT_PAGE;
		displayPage = "http://" + host + DISPLAY_PAGE;
		mainPage = "http://" + host + MAIN_PAGE;
	}
	
	private void printCookies(HttpClient client) {
		log(LogLevel.INFO, "【打印Cookies开始】");
		Cookie[] cookies = client.getState().getCookies();
		for(Cookie cookie : cookies) {
			log(cookie.getName() + "=" + cookie.getValue());
		}
		log(LogLevel.INFO, "【打印Cookies完成】");
	}
	
	private void printHeaders(HttpMethod method) {
		log(LogLevel.INFO, "【打印Headers开始】");
		Header[] headers = method.getRequestHeaders();
		for(Header header : headers) {
			log(header.getName() + "=" + header.getValue());
		}
		log(LogLevel.INFO, "【打印Headers完成】");
	}
	
	private void refreshServerTime(String content) {
		Matcher matcher = SERVER_TIME_PATTERN.matcher(content);
		if(matcher.find()) {
			String s = matcher.group(1);
			serverCalendar = Xiruo.stringToCalendar(s, "yyy-MM-dd HH:mm");
			log(LogLevel.INFO, "从服务器更新时间: " + s);
		}
	}
	
	public void startService() {
		threadRunStatus = true;
		new Thread(this).start();
	}
	
	public void stopService() {
		threadRunStatus = false;
	}
	
	public void run() {
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
				startMonitor();
//			}
//		});
//		try {
//			requestLogin();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private void startMonitor() {
		int hour = 0;
		int minute = 0;
		boolean signed = false;
		if(serverCalendar == null) {
			serverCalendar = Calendar.getInstance();
			serverCalendar.setTime(new Date());
		}
		while(threadRunStatus) {
			minute = serverCalendar.get(Calendar.MINUTE);
			hour = serverCalendar.get(Calendar.HOUR_OF_DAY);
			
			//23点设置标记为未签到
			if(hour == 23) {
				signed = false;
			}
			
//			if(hour == 23 && minute == 59) {//23:59准备签到
			if(minute == 59) {
				try {
					log(LogLevel.INFO, "时间未到，等待3秒...");
					Thread.sleep(3000);
					signed = false;
					requestRefresh();
				} catch(Exception ex) {}
//			} else if(hour == 0 && minute == 0 && !signed) {//0点开始签到
			} else if(minute == 0 && !signed) {//每个小时签到一次，应付目前论坛不定时开发的状况
				try {
					if(requestSignInput()) {
						requestSign();
						signed = true;
						log(LogLevel.INFO, "签到成功，等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
						if(hour == 0) {
							requestLogout();
							log(LogLevel.INFO, "注销成功，等待5秒...");
							Thread.sleep(5000);
							requestLogin();
							log(LogLevel.INFO, "重新登录成功，等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
							Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
						}
					} else {
						log(LogLevel.WARN, "论坛关闭，等待开启吧...");
					}
					Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
				} catch(Exception e) {
					signed = false;
					log(LogLevel.WARN, "签到操作失败：" + e.toString() + ", 等待3秒...");
					try {
						Thread.sleep(3000);
					} catch(Exception ex) {}
				}
			} else {
				while(true) {
					try {
						requestRefresh();
						break;
					} catch(Exception e) {
						e.printStackTrace();
						try {
							//暂停
							log(LogLevel.WARN, "刷新操作失败: " + e.toString() + ", 等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
							Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
						} catch(Exception ex) {}
					}
				}
				try {
					//暂停
					log(LogLevel.INFO, "扫描完成，等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
					Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
				} catch(Exception ex) {}
			}
		}
	}
	
	private void requestLogout() {
		GetMethod get = null;
		int statusCode = 0;
		try {
			log(LogLevel.INFO, "【注销开始】");
			get = new GetMethod(logoutPage);
			setHeaders(get);
			statusCode = client.executeMethod(get);
			if(statusCode != HttpStatus.SC_OK) {
				log(LogLevel.WARN, "注销操作异常...");
			}
			log(LogLevel.INFO, "【注销完成】");
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + logoutPage + "发生异常: " + e.toString());
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
	}
	
	private boolean requestLogin() {
		PostMethod post = null;
		while(true) {
			try {
				//登录页面    
				post = new PostMethod(loginPage);
				setHeaders(post);
				post.setRequestBody(new NameValuePair[] {
						new NameValuePair("referer", "http://" + host + "/./"),
						new NameValuePair("action", "login"),
						new NameValuePair("loginfield", "username"),
						new NameValuePair("loginsubmit", "yes"),
						new NameValuePair("mod", "logging"),
						new NameValuePair("username", username),
						new NameValuePair("password", password),
						new NameValuePair("questionid", "0"),
						new NameValuePair("answer", "")
				});
				log(LogLevel.INFO, "****************************开始登录************************************");
				int statusCode = client.executeMethod(post);
				if(statusCode == HttpStatus.SC_BAD_GATEWAY) {
					log(LogLevel.WARN, "网关异常，无法登录...");
					return false;
				}
				printCookies(client);
				printHeaders(post);
				log(LogLevel.INFO, "****************************完成登录************************************");
				return true;
			} catch(Exception e) {
				log(LogLevel.FATAL, "登录" + loginPage + "发生异常: " + e.toString());
				return false;
			} finally {
				if(null != post) {
					post.releaseConnection();
					post = null;
				}
			}
		}
	}
	
	private boolean requestSignInput() {
		formHash = "";
		GetMethod get = null;
		int statusCode = 0;
		while(formHash.equals("")) {
			try {
				log(LogLevel.INFO, "【获取formHash开始】");
				get = new GetMethod(signPage + "?id=dsu_paulsign:sign");
				setHeaders(get);
				statusCode = client.executeMethod(get);
				if(statusCode != HttpStatus.SC_OK) {
					log(LogLevel.WARN, "获取formHash异常...");
				}
				log(LogLevel.INFO, "【获取formHash完成】");
				String content = getStringByStream(get.getResponseBodyAsStream(), encoding);
				refreshServerTime(content);
				Matcher matcher = CLOSE_PATTERN.matcher(content);
				if(matcher.find()) {
					return false;
				}
				matcher = FORMHASH_PATTERN.matcher(content);
				if(matcher.find()) {
					formHash = matcher.group(1);
					log(LogLevel.INFO, "获得formhash: " + formHash);
				} else {
					log(LogLevel.WARN, "获取formhash失败");
				}
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + signPage + "发生异常，休息1秒: " + e.toString());
				try {
					Thread.sleep(1000);
				} catch(Exception ex) {}
			} finally {
				if(null != get) {
					get.releaseConnection();
					get = null;
				}
			}
		}
		return true;
	}
	
	private void requestSign() {
		PostMethod post = null;
		int statusCode = 0;
		while(true) {
			try {
				post = new PostMethod(signPage);
				setHeaders(post);
				post.setRequestHeader(new Header("referer", "http://" + host + "/plugin.php?id=dsu_paulsign:sign"));
				post.setRequestHeader(new Header("origin", "http://" + host));
				post.setRequestBody(new NameValuePair[] {
						new NameValuePair("id", "dsu_paulsign:sign"),
						new NameValuePair("operation", "qiandao"),
						new NameValuePair("infloat", "1"),
						new NameValuePair("fastreply", "0"),
						new NameValuePair("inajax", "0"),
						new NameValuePair("todaysay", ""),
						new NameValuePair("qdxq", "kx"),
						new NameValuePair("formhash", formHash),
						new NameValuePair("qdmode", "3")
				});
				log(LogLevel.INFO, "【签到开始】");
				statusCode = client.executeMethod(post);
				if(statusCode == HttpStatus.SC_BAD_GATEWAY) {
					log(LogLevel.WARN, "网关异常, 无法登录...");
				} else if(statusCode == HttpStatus.SC_OK) {
					String content = getStringByStream(post.getResponseBodyAsStream(), encoding);
					if(content.indexOf("您今日已经签到，请明天再来！") >= 0) {
						log(LogLevel.WARN, "【今天已经签过到了】");
					} else {
						log(LogLevel.INFO, "【恭喜你，签到成功：）........................】");
						log(LogLevel.INFO, content);
					}
				} else {
					log(LogLevel.INFO, statusCode + "");
					log(LogLevel.ERROR, getStringByStream(post.getResponseBodyAsStream(), encoding));
				}
				log(LogLevel.INFO, "【签到完成】");
				break;
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + signPage + "发生异常，休息1秒: " + e.toString());
				try {
					Thread.sleep(1000);
				} catch(Exception ex) {}
			} finally {
				if(null != post) {
					post.releaseConnection();
					post = null;
				}
			}
		}
	}
	
	private void requestRefresh() {
		GetMethod get = null;
		int statusCode = 0;
		Matcher matcher = null;
		try {
			get = new GetMethod(displayPage);
			setHeaders(get);
			statusCode = client.executeMethod(get);
			if(statusCode == HttpStatus.SC_OK) {
				String content = getStringByStream(get.getResponseBodyAsStream(), encoding);
				refreshServerTime(content);
				if(content.indexOf("积分 0, 距离下一级还需  积分") >= 0 || content.indexOf("立即註冊") >= 0) {
					requestLogin();
				} else {
					matcher = SCORE_PATTERN.matcher(content);
					if(matcher.find()) {
						log(LogLevel.INFO, "【" + matcher.group(0) + "】");
					} else {
						matcher = SCORE_PATTERN_.matcher(content);
						if(matcher.find()) {
							log(LogLevel.INFO, "【" + matcher.group(0) + "】");
						}
					}
				}
			}
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + displayPage + "发生异常: " + e.toString());
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
	}
}
