package com.sida.xiruo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpMethodParams;

public abstract class AbstractUtil {
	protected final static boolean SERVICE_FLAG = false;
	protected static enum LogLevel {
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
	
	protected String host;
	protected int port;
	protected String username;
	protected String password;
	protected String mainPage;
	protected HttpClient client;
	
	protected void log(String msg) {
		log(LogLevel.INFO, msg);
	}
	
	protected void log(LogLevel logLevel, String msg) {
		System.out.println("[" + host + (port == 80 ? "" : (":" + port)) + "][" + username + "][" + Xiruo.getNow() + "] [" + logLevel.toString() + "] " + msg);
	}
	
	protected static String getStringByStream(InputStream resStream) throws IOException {
		return getStringByStream(resStream, true, "GBK");
	}
	
	protected static String getStringByStream(InputStream resStream, String encoding) throws IOException {
		return getStringByStream(resStream, true, encoding);
	}
	
	protected static String getStringByStream(InputStream resStream, boolean ignoreViewstate, String encoding) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(resStream, encoding));
		StringBuffer resBuffer = new StringBuffer();
		String resTemp = "";
		while((resTemp = br.readLine()) != null){
			resBuffer.append(resTemp).append("\r\n");
		}
		return resBuffer.toString();
	}
	
	protected void initHttpClient() {
		if(client == null) {
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			client = new HttpClient(connectionManager);
			client.getHostConfiguration().setHost(host, port, "http"); //配置服务器参量 
			client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); //配置Cookie策略为浏览器默认策略
			client.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
			client.getParams().setParameter("http.protocol.single-cookie-header", true); 
			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getParams().setParameter("http.protocol.content-charset","UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);
		}
	}
	
	protected void setHeaders(HttpMethod method) {
		method.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.NETSCAPE);
		method.setRequestHeader("accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		method.setRequestHeader("referer", mainPage);
		method.setRequestHeader("HTTP_REFERER", mainPage);
		method.setRequestHeader("accept-encoding", "UTF-8,GBK,GB2312,deflate");
		method.setRequestHeader("accept-language", "zh-CN");
		method.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		method.setRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; staticlogin:product=cboxf2010&act=login&info=ZmlsZW5hbWU9UG93ZXJXb3JkMjAxME94Zl9VbHRpbWF0ZS5leGUmbWFjPUY4RDgxQUY2MTgwQjQ3REQ5MUI5QkM4NjU3MzAwRkU3JnBhc3Nwb3J0PSZ2ZXJzaW9uPTIwMTAuNi4zLjYuMiZjcmFzaHR5cGU9MQ==&verify=b226e2d473bb8dbbb37568db14fee0e4; staticlogin:product=cboxf2010&act=login&info=ZmlsZW5hbWU9UG93ZXJXb3JkMjAxME94Zl9TcGVjaWFsLmV4ZSZtYWM9RjhEODFBRjYxODBCNDdERDkxQjlCQzg2NTczMDBGRTcmcGFzc3BvcnQ9JnZlcnNpb249MjAxMC42LjMuNS4yJmNyYXNodHlwZT0x&verify=94c65611a34cfb4964638346b072d76e; staticlogin:product=cboxf09&act=login&info=ZmlsZW5hbWU9UG93ZXJ3b3JkMjAwOU94Zi4yNTI2OS40MDExLmV4ZSZtYWM9RjhEODFBRjYxODBCNDdERDkxQjlCQzg2NTczMDBGRTcmcGFzc3BvcnQ9JnZlcnNpb249MjAwOS4wNS4yNS4zLjI3MiZjcmFzaHR5cGU9MQ==&verify=32e210faa8ce04f90dfc4225f98e80ac; aff-kingsoft-ciba; TheWorld)");
		method.setRequestHeader("host", host);
		method.setRequestHeader("connection", "Keep-Alive");
		method.setRequestHeader("cache-control", "no-cache");
	}
}
