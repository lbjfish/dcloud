/**
 * 
 */
package com.sida.xiruo.common.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiruo.Jiang
 *
 */
public final class SystemService extends Thread {
	private static final boolean SERVICE_FLAG = false;
	private static final boolean ONLY_LOGIN = false;
	private static final String MONITOR_URL = "http://www.letyo.com/ajaxGetShopProductList.php";
	private static final String MAIN_PAGE = "http://www.letyo.com/";
	private static final String PRODUCT_PAGE = "http://www.letyo.com/shopProduct.php?id=";
	private static final String LOGIN_PAGE = "http://bbs.letyo.com/serviceLogin.tr?continues=http%3A%2F%2Fwww.letyo.com%2FmyCenter.php&followup=http%3A%2F%2Fwww.letyo.com%2Flogin.php%3Fcontinues%3Dhttp%253A%252F%252Fwww.letyo.com%252FmyCenter.php&partner=tuan123&sign=c61ff8e30a4734c8267a284d97abfe9a";
	private static final String REDIRECT_PAGE = "http://www.letyo.com/login.php?continues=http://www.letyo.com/myCenter.php&followup=http%3A%2F%2Fwww.letyo.com%2Flogin.php%3Fcontinues%3Dhttp%253A%252F%252Fwww.letyo.com%252FmyCenter.php&email=jianglingfeng%40gmail.com&autoLogin=N&userId=136644&userName=xiruo&partner=tuan123&photo=&continues=http%3A%2F%2Fwww.letyo.com%2FmyCenter.php&sign=42800D7D814E5E466062285525DD7257";
	private static final String SIGN_PAGE = "http://www.letyo.com/score/dutyVisit.php";
	private static final String HOST = "www.letyo.com";
	private static final int PORT = 80;
	private static final int DEFAULT_SLEEP_MILLISECONDS = 30000;
	private static final int DEFAULT_SLEEP_MILLISECONDS_ = 10000;
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
	private static final String REGEXP_STRING = "<li[^>]*?>" + 
			"<div class=\"nspCont\">" + 
			"<div class=\"nsp-title\">" + 
			"<a href=\"/shopProduct\\.php\\?id=(\\d+?)\" target=\"_blank\" title=\'[^\']+?\' >" +
			"(.*?)" + 
			"</a>" + 
			"</div>" + 
			"<div class=\"nsp-img\">" + 
			"<a href=\"/shopProduct\\.php\\?id=\\d+?\" target=\"_blank\" title=\'[^\']+?\' >" + 
			"<img src=\"([^\"]+?)\" onerror=\"this\\.src=\'http://filedata\\.oobang\\.com/tuan123/images/no-image\\.jpg\'\" alt=\"[^\']+?\" />" + 
			"</a>" + 
			"</div>" +
			"<div class=\"nsp\\-info\">" + 
			"<div class=\"price\">" + 
			"<img class=\"nshop\\-letyomoney\" src=\"http://bangimg\\.oobang\\.com/images/common/hidden\\.gif\" />" + 
			"<span>(.*?)</span>" + 
			"</div>" + 
			"<div class=\"buyer\">" + 
			"(.+?)" + 
			"</div>" +
			"<div class=\"gosee\">" + 
			"<a href=\"[^\"]+?\" title=\"[^\"]+?\" class=\"[^\"]+?\" target=\"_blank\" >" + 
			"</a>" + 
			"<a href=\"/shopProduct\\.php\\?id=\\d+?\" title=\"查看该兑换产品详情\" target=\"_blank\" class=\"detail\">" + 
			"<span>详情</span>" + 
			"<img src=\"http://bangimg\\.oobang\\.com/images/common/hidden\\.gif\" />" + 
			"</a>" + 
			"</div>" + 
			"</div>" + 
			"</div>" + 
			"</li>";
	private static String readFileNoWrap(File file, String encoding) {
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    		StringBuilder builder = new StringBuilder("");
			String tmp = "";
			while((tmp = reader.readLine()) != null) {
				builder.append(tmp);
			}
			
			return builder.toString();
    	} catch(Exception e) {
			return null;
		}

    }
    
	private static String readFileNoWrap(File file){
		return readFileNoWrap(file, "GBK");
	}
	
	private static void log(String msg) {
		log(LogLevel.INFO, msg);
	}
	
	private static void log(LogLevel logLevel, String msg) {
		System.out.println("[" + logLevel.toString() + "] " + msg);
	}
	
	public static String getStringByStream(InputStream resStream) throws IOException {
		return getStringByStream(resStream, true);
	}
	
	public static String getStringByStream(InputStream resStream, boolean ignoreViewstate) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
		StringBuffer resBuffer = new StringBuffer();
		String resTemp = "";
		while((resTemp = br.readLine()) != null){
			resBuffer.append(resTemp).append("\r\n");
		}
		return resBuffer.toString();
	}
	
	private long previousMonitorTime = 0;
	private boolean threadRunStatus = true;
	private HttpClient client;
	private JFrame jframe;
	private byte[] lockFlag = new byte[0];
	/**
	 * @author Xiruo.Jiang
	 * @date 2012-11-29
	 * @param args
	 */
	public static void main(String[] args) {
		SystemService instance = new SystemService();
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
//		method.setRequestHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		method.setRequestHeader("referer", MAIN_PAGE);
//		method.setRequestHeader("accept-encoding", "deflate,sdch");
//		method.setRequestHeader("accept-language", "zh-CN,zh;q=0.8");
//		method.setRequestHeader("content-type", "application/x-www-form-urlencoded");
//		method.setRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
//		method.setRequestHeader("host", HOST);
//		method.setRequestHeader("connection", "Keep-Alive");
//		method.setRequestHeader("cache-control", "max-age=0");
//		method.setRequestHeader("cookie", "JSESSIONID=1391DD8632604C129017C615FFCF91A8");
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
				startMonitor();
//			}
//		});
	}
	
	private void startMonitor() {
		Calendar calendar = Calendar.getInstance();
		int minute = 0;
		int second = 0;
		int pauseSecond = 0;
		int flag = DEFAULT_SLEEP_MILLISECONDS / 1000;
		while(threadRunStatus) {
			calendar.setTime(new Date());
			minute = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
			if(minute % flag > 0 && (calendar.getTimeInMillis() - previousMonitorTime) < DEFAULT_SLEEP_MILLISECONDS * 120) {
				try {
					//暂停
					pauseSecond = (flag - (minute % flag)) * 30 - second;
					if(pauseSecond < 30) {
						pauseSecond = 30;
					}
					log(LogLevel.INFO, "时间未到，等待: " + (pauseSecond / 60) + "分" + (pauseSecond % 60) + "秒");
					Thread.sleep(pauseSecond * 1000);
				} catch(Exception ex) {}
			} else {
				try {
					requestRedirect();
				} catch(Exception e) {
					try {
						e.printStackTrace();
						//暂停
						log(LogLevel.WARN, "登录操作失败: " + e.toString() + ", 等待: " + (DEFAULT_SLEEP_MILLISECONDS / 1000) + " 秒...");
						Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
					} catch(Exception ex) {}
				}
				if(!ONLY_LOGIN) {
					while(true) {
						try {
							requestProducts();
							break;
						} catch(Exception e) {
							e.printStackTrace();
							try {
								//暂停
								log(LogLevel.WARN, "请求产品操作失败: " + e.toString() + ", 等待: " + (DEFAULT_SLEEP_MILLISECONDS / 1000) + " 秒...");
								Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
							} catch(Exception ex) {}
						}
					}
				}
				previousMonitorTime = calendar.getTimeInMillis();
				try {
					//暂停
					log(LogLevel.INFO, "扫描完成，等待: " + (DEFAULT_SLEEP_MILLISECONDS_ / 1000) + "秒...");
					Thread.sleep(DEFAULT_SLEEP_MILLISECONDS_);
				} catch(Exception ex) {}
			}
		}
	}
	
	private void requestRedirect() throws Exception {
		GetMethod get = null;
		int statusCode = 0;
		try {
			get = new GetMethod(REDIRECT_PAGE);
			setHeaders(get);
			statusCode = client.executeMethod(get);
		} catch(Exception e) {
			log(LogLevel.FATAL, "请求" + REDIRECT_PAGE + "异常: " + e.toString());
			return;
		} finally {
			if(null != get) {
				get.releaseConnection();
				get = null;
			}
		}
		if(statusCode == HttpStatus.SC_BAD_GATEWAY) {
			log(LogLevel.WARN, "网关异常导致无法登录...");
		} else {
			while(statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址 
				Header locationHeader = get.getResponseHeader("location");
				if (locationHeader != null) { 
					String location = "";
					location = locationHeader.getValue();
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
			try {
				get = new GetMethod(SIGN_PAGE);
				setHeaders(get);
				statusCode = client.executeMethod(get);
				if(statusCode == HttpStatus.SC_OK) {
					JSONObject json = JSONObject.fromObject(getStringByStream(get.getResponseBodyAsStream()));
					log("签到标识: " + json.getString("flag"));
					log("响应消息: " + json.getString("message"));
				}
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + SIGN_PAGE + "异常: " + e.toString());
			} finally {
				if(null != get) {
					get.releaseConnection();
					get = null;
				}
			}
		}
	}
	
	private void requestLogin() throws Exception {
		PostMethod post = null;
		int statusCode = 0;
		try {
			//登录页面    
			post = new PostMethod(LOGIN_PAGE);
			setHeaders(post);
			NameValuePair username = new NameValuePair("username", "jianglingfeng@gmail.com");
			NameValuePair password = new NameValuePair("password", "xiruojiang");
			post.setRequestBody(new NameValuePair[] {username, password});
			log("******************************登录******************************");
			statusCode = client.executeMethod(post);
		} catch(Exception e) {
			log(LogLevel.FATAL, "登录" + LOGIN_PAGE + "异常: " + e.toString());
			return;
		} finally {
			if(null != post) {
				post.releaseConnection();
				post = null;
			}
		}
		GetMethod get = null;
		if(statusCode == HttpStatus.SC_BAD_GATEWAY) {
			log(LogLevel.WARN, "网关异常导致无法登录...");
			try {
				get = new GetMethod(REDIRECT_PAGE);
				setHeaders(get);
				statusCode = client.executeMethod(get);
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + REDIRECT_PAGE + "异常: " + e.toString());
				return;
			} finally {
				if(null != get) {
					get.releaseConnection();
					get = null;
				}
			}
			if(statusCode == HttpStatus.SC_OK) {
				log(getStringByStream(get.getResponseBodyAsStream()));
				get.releaseConnection();
			} else {
				get.releaseConnection();
				while(statusCode == HttpStatus.SC_MOVED_PERMANENTLY
						|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					// 从头中取出转向的地址 
					Header locationHeader = get.getResponseHeader("location");
					String location = locationHeader.getValue();
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
			while(statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址 
				Header locationHeader = post.getResponseHeader("location");
				if (locationHeader != null) { 
					String location = locationHeader.getValue();
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
			try {
				get = new GetMethod(SIGN_PAGE);
				setHeaders(get);
				statusCode = client.executeMethod(get);
				if(statusCode == HttpStatus.SC_OK) {
					JSONObject json = JSONObject.fromObject(getStringByStream(get.getResponseBodyAsStream()));
					log("签到标识: " + json.getString("flag"));
					log("响应消息: " + json.getString("message"));
				}
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + SIGN_PAGE + "异常: " + e.toString());
			} finally {
				if(null != get) {
					get.releaseConnection();
					get = null;
				}
			}
		}
	}
	
	private void requestProducts() throws Exception {
		GetMethod get = null;
		String content = "<li class=\"fs\">" + 
				"<div class=\"nspCont\">" + 
				"<div class=\"nsp-title\">" + 
				"<a href=\"/shopProduct.php?id=763\" target=\"_blank\" title=\'【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\' >" + 
				"【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯 " + 
				"</a>" + 
				"</div>" + 
				"<div class=\"nsp-img\">" + 
				"<a href=\"/shopProduct.php?id=763\" target=\"_blank\" title=\'【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\' >" + 
				"<img src=\"http://filedata.oobang.com/shop/2012/11/28/4/4_20121128095117_1.jpg\" onerror=\"this.src=\'http://filedata.oobang.com/tuan123/images/no-image.jpg\'\" alt=\"【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\" />" + 
				"</a>" + 
				"</div>" + 
				"<div class=\"nsp-info\">" + 
				"<div class=\"price\">" + 
				"<img class=\"nshop-letyomoney\" src=\"http://bangimg.oobang.com/images/common/hidden.gif\" />" + 
				"<span>7,800</span>" + 
				"</div>" + 
				"<div class=\"buyer\">" + 
				"<span class=\"t1\">目前无货</span> 已换：5" + 
//				" 库存: <span>2</span> 已换：6 " +
				"</div>" + 
				"<div class=\"gosee\">" + 
				"<a href=\"/shopProduct.php?id=763\" title=\"去看看该兑换产品\" class=\"go go-end\" target=\"_blank\" >" + 
				"</a>" + 
				"<a href=\"/shopProduct.php?id=763\" title=\"查看该兑换产品详情\" target=\"_blank\" class=\"detail\">" + 
				"<span>详情</span>" + 
				"<img src=\"http://bangimg.oobang.com/images/common/hidden.gif\" />" + 
				"</a>" + 
				"</div>" + 
				"</div>" + 
				"</div>" + 
				"</li>";
		content = "<li class=\"fs\">" + 
				"<div class=\"nspCont\">" + 
				"<div class=\"nsp-title\">" + 
				"<a href=\"/shopProduct.php?id=763\" target=\"_blank\" title=\'【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\' >" + 
				"【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯 " + 
				"</a>" + 
				"</div>" + 
				"<div class=\"nsp-img\">" + 
				"<a href=\"/shopProduct.php?id=763\" target=\"_blank\" title=\'【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\' >" + 
				"<img src=\"http://filedata.oobang.com/shop/2012/11/28/4/4_20121128095117_1.jpg\" onerror=\"this.src=\'http://filedata.oobang.com/tuan123/images/no-image.jpg\'\" alt=\"【儿童毯】豆荚DOGA亲肤卡通拉舍尔儿童毯\" />" + 
				"</a>" + 
				"</div>" + 
				"<div class=\"nsp-info\">" + 
				"<div class=\"price\">" + 
				"<img class=\"nshop-letyomoney\" src=\"http://bangimg.oobang.com/images/common/hidden.gif\" />" + 
				"<span>7,800</span>" +
				"</div>" +
				"<div class=\"buyer\">" +
				" 库存: <span>2</span> 已换：6 " +
				"</div>" +
				"<div class=\"gosee\">" +
				"<a href=\"/score/shopProcess.php?t=buy&id=767\" title=\"去兑换该产品\" class=\"go\" target=\"_blank\" ></a>" +
				"<a href=\"/shopProduct.php?id=767\" title=\"查看该兑换产品详情\" target=\"_blank\" class=\"detail\">" +
				"<span>详情</span>" +
				"<img src=\"http://bangimg.oobang.com/images/common/hidden.gif\" />" +
				"</a>" +
				"</div>" +
				"</div>" +
				"</div>" +
				"</li>";
//		content = readFileNoWrap(new File("E:\\data\\wrapper\\letyoApp\\logs\\xxx.txt"));
		Pattern pattern = Pattern.compile(REGEXP_STRING);
		Matcher matcher = null;
//		int c = 0;
		int productId = 0;
		String productName = "";
		String productImageUrl = "";
		String productScore = "";
		String productStatus = "";
		productId = 0;
		int statusCode = 0;
		while(threadRunStatus) {
			try {
				get = new GetMethod(MONITOR_URL);
				setHeaders(get);
				statusCode = client.executeMethod(get);
				content = getStringByStream(get.getResponseBodyAsStream());
			} catch(Exception e) {
				log(LogLevel.FATAL, "请求" + REDIRECT_PAGE + "异常: " + e.toString());
				return;
			} finally {
				if(null != get) {
					get.releaseConnection();
					get = null;
				}
			}
			if(statusCode != HttpStatus.SC_OK) {
				return;
			}
			if(!content.equals("")) {
				matcher = pattern.matcher(content);
				if(matcher.find()) {
//					c = matcher.groupCount();
//					for(int i = 1; i <= c; i++) {
//						System.out.println(matcher.group(i).trim());
//					}
					productId = Xiruo.nullToInt(matcher.group(1));
					productName = Xiruo.nullToEmpty(matcher.group(2));
					productImageUrl = Xiruo.nullToEmpty(matcher.group(3));
					productScore = Xiruo.nullToEmpty(matcher.group(4));
					productStatus = Xiruo.nullToEmpty(matcher.group(5)).replaceAll("<[^>]+?>", "");
					if(productId != 0 && productStatus.indexOf("目前无货") < 0) {
						log("有新的换购礼品，弹出提示框...");
						tipMessage(productImageUrl, productId, productName, productScore, productStatus);
						synchronized(lockFlag) {
							lockFlag.wait();
						}
					} else {
						log("现在还没有新的换购礼品...");
					}
					log("[" + productId + "] [" + productName + "] [" + productScore + "] [" + productStatus + "]");
				} else {
					log(LogLevel.WARN, "程序已经失效，来优礼品页可能已经更新，请更新服务...");
				}
				break;
			} else {
				log(LogLevel.WARN, "获取产品页面内容失败，等待: " + (DEFAULT_SLEEP_MILLISECONDS / 3000) + "秒...");
				Thread.sleep(DEFAULT_SLEEP_MILLISECONDS);
			}
		}
	}
	
	private JLabel timeLabel;
	private JLabel imgLabel;
	private JLabel productLabel;
	private JLabel scoreLabel;
	private JLabel statusLabel;
	private int productId;
	
	private void tipMessage(final String imgUrl, 
			final int productId, 
			final String productName, 
			final String productScore,
			final String productStatus) throws Exception {
		if (jframe == null) {
			jframe = new JFrame();
			jframe.setTitle("来优有新礼品啦，赶紧换购吧");
			jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jframe.setLocationRelativeTo(null);
			jframe.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {  
					jframe.setVisible(false);
					synchronized(lockFlag) {
						lockFlag.notifyAll();
					}
				}  
			});
			Container container = jframe.getContentPane();
//			container.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			container.setLayout(new BorderLayout());
			JPanel jpanel = new JPanel();
			jpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
			container.add(jpanel, BorderLayout.CENTER);
			//时间
			timeLabel = new JLabel();
			jpanel.add(timeLabel);
			//产品名称
			productLabel = new JLabel();
			jpanel.add(productLabel);
			//产品图片
			imgLabel = new JLabel();
			jpanel.add(imgLabel);
			imgLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						refreshProductImage(imgLabel, imgUrl);
					} catch(Exception ex) {
						log(LogLevel.WARN, ex.toString());
					}
				}
			});
			//需要积分
			scoreLabel = new JLabel();
			jpanel.add(scoreLabel);
			//兑换状态
			statusLabel = new JLabel();
			jpanel.add(statusLabel);
			//按钮
			jpanel = new JPanel();
			jpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			JButton jbutton = new JButton();
			jbutton.setText("看看去");
			jbutton.setPreferredSize(new Dimension(120, 25));
			jbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Runtime.getRuntime().exec("cmd.exe /c start " + PRODUCT_PAGE + SystemService.this.productId);
					} catch(Exception ex) {}
				}
			});
			jpanel.add(jbutton);
			container.add(jpanel, BorderLayout.SOUTH);
		}
		this.productId = productId;
		timeLabel.setText("<html><font size='5'>" + Xiruo.getNow() + "</font></html>");
		productLabel.setText("<html><font size='5'>" + productName + "</font></html>");
		refreshProductImage(imgLabel, imgUrl);
		scoreLabel.setText("<html><font size='5'>" + productScore + "</font></html>");
		statusLabel.setText("<html><font size='5'>" + productStatus + "</font></html>");
		jframe.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jframe.getSize();
        jframe.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		jframe.setVisible(true);
		jframe.setAlwaysOnTop(true);
		jframe.setResizable(true);
	}
	
	private void refreshProductImage(JLabel jlabel, String imgUrl) throws Exception {
		GetMethod get = null;
		try {
			get = new GetMethod(imgUrl);
			setHeaders(get);
			client.executeMethod(get);
			BufferedImage bi = ImageIO.read(get.getResponseBodyAsStream());
			if(bi != null) {
				jlabel.setIcon(new ImageIcon(bi));
				jlabel.repaint();
			}
		} finally {
			if(get != null) {
				get.releaseConnection();
				get = null;
			}
		}
	}
}
