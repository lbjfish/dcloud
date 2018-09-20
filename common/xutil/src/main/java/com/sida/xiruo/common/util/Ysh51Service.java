/**
 * 
 */
package com.sida.xiruo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author Xiruo.Jiang
 *
 */
public final class Ysh51Service extends Thread {
	private static final boolean SERVICE_FLAG = false;
	private static final String HOST = "www.ysh51.com";
	private static final int PORT = 80;
	private static final String SIGN_PAGE = "http://" + HOST + "/plugin.php";//签到页
	private static final String LOGIN_PAGE = "http://" + HOST + "/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=";//登录页
	private static final String LOGOUT_PAGE = "http://" + HOST + "/member.php?mod=logging&action=logout&formhash=";//注销
	private static final String DISPLAY_PAGE = "http://" + HOST + "/forum.php?formhash=";//刷新页
	private static final String MAIN_PAGE = "http://" + HOST + "/forum.php?formhash=";//主页
	private static final int DEFAULT_SLEEP_MILLISECONDS = 30000;
	private static final Pattern SERVER_TIME_PATTERN = Pattern.compile("GMT\\+8, ([0-9\\-\\:\\s]+?)<span id\\=\"debuginfo\">", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern SCORE_PATTERN = Pattern.compile("积分 \\d+?, 距离下一级还需 \\d+? 积分", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern FORMHASH_PATTERN = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\"([^\"]+?)\" />", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static enum LogLevel {
		DEBUG(1),
		INFO(2),
		STATUS(3),
		WARN(4),
		ERROR(5),
		FATAL(6),
		ADVICE(7),
		NOTICE(8);
		private LogLevel(int level) {
			this.level = level;
		}
		public int getLevel() {
			return this.level;
		}
		private int level;
	}
	private static void printCookies(HttpClient client) {
		log(LogLevel.INFO, "【打印Cookies开始】");
		Cookie[] cookies = client.getState().getCookies();
		for(Cookie cookie : cookies) {
			log(cookie.getName() + "=" + cookie.getValue());
		}
		log(LogLevel.INFO, "【打印Cookies完成】");
	}
	
	private static void printHeaders(HttpMethod method) {
		log(LogLevel.INFO, "【打印Headers开始】");
		Header[] headers = method.getRequestHeaders();
		for(Header header : headers) {
			log(header.getName() + "=" + header.getValue());
		}
		log(LogLevel.INFO, "【打印Headers完成】");
	}
	
	private static void log(String msg) {
		log(LogLevel.INFO, msg);
	}
	
	private static void log(LogLevel logLevel, String msg) {
		System.out.println("[" + Xiruo.getNow() + "] [" + logLevel.toString() + "] " + msg);
	}
	
	public static String getStringByStream(InputStream resStream) throws IOException {
		return getStringByStream(resStream, true);
	}
	
	public static String getStringByStream(InputStream resStream, boolean ignoreViewstate) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "GBK"));
		StringBuffer resBuffer = new StringBuffer();
		String resTemp = "";
		while((resTemp = br.readLine()) != null){
			resBuffer.append(resTemp).append("\r\n");
		}
		return resBuffer.toString();
	}
	
	private boolean threadRunStatus = true;
	private HttpClient client;
	private Calendar serverCalendar;
	private String formHash;
	/**
	 * @author Xiruo.Jiang
	 * @date 2012-11-29
	 * @param args
	 */
	public static void main(String[] args) {
		Ysh51Service instance = new Ysh51Service();
		instance.initHttpClient();
		instance.startService();
	}
	
	private void initHttpClient() {
		if(client == null) {
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			client = new HttpClient(connectionManager);
			client.getHostConfiguration().setHost(HOST, PORT, "http"); //配置服务器参量 
			client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); //配置Cookie策略为浏览器默认策略
			client.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
			client.getParams().setParameter("http.protocol.single-cookie-header", true); 
			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getParams().setParameter("http.protocol.content-charset","UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);
		}
	}
	
	private void setHeaders(HttpMethod method) {
		method.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.NETSCAPE);
		method.setRequestHeader("accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		method.setRequestHeader("referer", MAIN_PAGE);
		method.setRequestHeader("accept-encoding", "UTF-8,GBK,GB2312,deflate");
		method.setRequestHeader("accept-language", "zh-CN");
		method.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		method.setRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; staticlogin:product=cboxf2010&act=login&info=ZmlsZW5hbWU9UG93ZXJXb3JkMjAxME94Zl9VbHRpbWF0ZS5leGUmbWFjPUY4RDgxQUY2MTgwQjQ3REQ5MUI5QkM4NjU3MzAwRkU3JnBhc3Nwb3J0PSZ2ZXJzaW9uPTIwMTAuNi4zLjYuMiZjcmFzaHR5cGU9MQ==&verify=b226e2d473bb8dbbb37568db14fee0e4; staticlogin:product=cboxf2010&act=login&info=ZmlsZW5hbWU9UG93ZXJXb3JkMjAxME94Zl9TcGVjaWFsLmV4ZSZtYWM9RjhEODFBRjYxODBCNDdERDkxQjlCQzg2NTczMDBGRTcmcGFzc3BvcnQ9JnZlcnNpb249MjAxMC42LjMuNS4yJmNyYXNodHlwZT0x&verify=94c65611a34cfb4964638346b072d76e; staticlogin:product=cboxf09&act=login&info=ZmlsZW5hbWU9UG93ZXJ3b3JkMjAwOU94Zi4yNTI2OS40MDExLmV4ZSZtYWM9RjhEODFBRjYxODBCNDdERDkxQjlCQzg2NTczMDBGRTcmcGFzc3BvcnQ9JnZlcnNpb249MjAwOS4wNS4yNS4zLjI3MiZjcmFzaHR5cGU9MQ==&verify=32e210faa8ce04f90dfc4225f98e80ac; aff-kingsoft-ciba; TheWorld)");
		method.setRequestHeader("host", HOST);
		method.setRequestHeader("connection", "Keep-Alive");
		method.setRequestHeader("cache-control", "no-cache");
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
		start();
	}
	
	public void stopService() {
		threadRunStatus = false;
	}
	
	public void run() {
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				startMonitor();
//			}
//		});
//		try {
//			requestLogin();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		requestLogin();
		requestSign();
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
					requestSign();
					signed = true;
					log(LogLevel.INFO, "签到成功，等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
					Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
					if(hour == 0) {
						requestLogout();
						log(LogLevel.INFO, "注销成功，等待5秒...");
						Thread.sleep(5000);
						requestLogin();
						log(LogLevel.INFO, "重新登录成功，等待" + (DEFAULT_SLEEP_MILLISECONDS / 1000) + "秒...");
						Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
					}
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
			get = new GetMethod(LOGOUT_PAGE + formHash);
			setHeaders(get);
			statusCode = client.executeMethod(get);
			if(statusCode != HttpStatus.SC_OK) {
				log(LogLevel.WARN, "注销操作异常...");
			}
			log(LogLevel.INFO, "【注销完成】");
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + LOGOUT_PAGE + "发生异常: " + e.toString());
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
	}
	
	private boolean requestLogin() {
		PostMethod post = null;
		try {
			//登录页面    
			post = new PostMethod(LOGIN_PAGE);
			setHeaders(post);
			post.setRequestBody(new NameValuePair[] {
					new NameValuePair("referer", "http://" + HOST + "/./"),
					new NameValuePair("action", "login"),
					new NameValuePair("loginfield", "username"),
					new NameValuePair("loginsubmit", "yes"),
					new NameValuePair("mod", "logging"),
//					new NameValuePair("username", "xiruo"),
//					new NameValuePair("password", "xiruojiang812611"),
					new NameValuePair("username", "wsgzxl"),
					new NameValuePair("password", "12345678"),
					new NameValuePair("questionid", "0"),
					new NameValuePair("formhash", formHash),
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
			log(LogLevel.FATAL, "登录" + LOGIN_PAGE + "发生异常: " + e.toString());
			return false;
		} finally {
			if(null != post) {
				post.releaseConnection();
				post = null;
			}
		}
	}
	
	private void requestSignInput() {
		GetMethod get = null;
		int statusCode = 0;
		try {
			log(LogLevel.INFO, "【获取formHash开始】");
			get = new GetMethod(SIGN_PAGE + "?id=dsu_paulsign:sign");
			setHeaders(get);
			statusCode = client.executeMethod(get);
			if(statusCode != HttpStatus.SC_OK) {
				log(LogLevel.WARN, "获取formHash异常...");
			}
			log(LogLevel.INFO, "【获取formHash完成】");
			String content = getStringByStream(get.getResponseBodyAsStream());
			Matcher matcher = FORMHASH_PATTERN.matcher(content);
			if(matcher.find()) {
				formHash = matcher.group(1);
//				log(LogLevel.INFO, "获得formhash: " + formHash);
			} else {
				log(LogLevel.WARN, "获取formhash失败");
			}
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + SIGN_PAGE + "发生异常: " + e.toString());
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
	}
	
	private void requestSign() {
		requestSignInput();
		PostMethod post = null;
		int statusCode = 0;
		try {
			post = new PostMethod(SIGN_PAGE);
			setHeaders(post);
			post.setRequestHeader(new Header("referer", "http://" + HOST + "/plugin.php?id=dsu_paulsign:sign"));
			post.setRequestHeader(new Header("origin", "http://" + HOST));
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
				String content = getStringByStream(post.getResponseBodyAsStream());
				if(content.indexOf("您今日已经签到，请明天再来！") >= 0) {
					log(LogLevel.WARN, "【今天已经签过到了】");
				} else {
					log(LogLevel.INFO, "【恭喜你，签到成功：）........................】");
				}
			} else if(statusCode == HttpStatus.SC_MOVED_TEMPORARILY
					|| statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
				while(statusCode == HttpStatus.SC_MOVED_TEMPORARILY
						|| statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
				// 从头中取出转向的地址 
					Header locationHeader = post.getResponseHeader("location");
					if (locationHeader != null) { 
						String location = "";
						location = locationHeader.getValue();
						GetMethod get = null;
						try {
							get = new GetMethod(location);
							setHeaders(get);
							statusCode = client.executeMethod(get);
						} catch(Exception e) {
							log(LogLevel.FATAL, "请求" + location + "异常: " + e.toString());
							return;
						} finally {
							if(null != get) {
								get.releaseConnection();
								get = null;
							}
						}
					}
				}
			} else {
				log(LogLevel.INFO, statusCode + "");
				log(LogLevel.ERROR, getStringByStream(post.getResponseBodyAsStream()));
			}
			log(LogLevel.INFO, "【签到完成】");
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + SIGN_PAGE + "发生异常: " + e.toString());
		} finally {
			if(null != post) {
				post.releaseConnection();
				post = null;
			}
		}
	}
	
	private void requestRefresh() {
		GetMethod get = null;
		int statusCode = 0;
		Matcher matcher = null;
		try {
			get = new GetMethod(DISPLAY_PAGE + formHash);
			setHeaders(get);
			statusCode = client.executeMethod(get);
			if(statusCode == HttpStatus.SC_OK) {
				String content = getStringByStream(get.getResponseBodyAsStream());
				refreshServerTime(content);
				if(content.indexOf("积分 0, 距离下一级还需  积分") >= 0) {
					requestLogin();
				} else {
					matcher = SCORE_PATTERN.matcher(content);
					if(matcher.find()) {
						log(LogLevel.INFO, "【" + matcher.group(0) + "】");
					}
				}
			}
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + DISPLAY_PAGE + "发生异常: " + e.toString());
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
	}
}
