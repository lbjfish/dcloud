package com.sida.xiruo.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

public class RemoteFileUtil {
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	//根据URL获取远程文件
	public static void writeAudioToDisk(byte[] img, String fileName, String localPath){
		try {
			File file = new File(localPath + File.separator + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] getAudioFromNetByUrl(String strUrl){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] btImg = readInputStream(inStream);
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
	
	//上传素材至微信服务器
	public static String send(String url, String filePath) throws IOException {
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("");
		}

		
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		OutputStream out = new DataOutputStream(con.getOutputStream());
		out.write(head);

		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("");
		} finally {
			if (reader != null) {
				reader.close();
			}

		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		String mediaId = jsonObj.getString("media_id");
		System.out.println("mediaId===========" + mediaId);
		return mediaId;
	}
	
//	public static JSONObject getAccessToken(String appId, String appSecret) {       
//    	String url = access_token_url.replace("APPID",appId).replace("APPSECRET",appSecret);
//        return httpRequest(url, "GET", null);
//    	
//    }
	
	
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
                TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                URL url = new URL(requestUrl);
                HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
                httpUrlConn.setSSLSocketFactory(ssf);

                httpUrlConn.setDoOutput(true);
                httpUrlConn.setDoInput(true);
                httpUrlConn.setUseCaches(false);
                httpUrlConn.setRequestMethod(requestMethod);

                if ("GET".equalsIgnoreCase(requestMethod))
                        httpUrlConn.connect();

                if (null != outputStr) {
                        OutputStream outputStream = httpUrlConn.getOutputStream();
                        outputStream.write(outputStr.getBytes("UTF-8"));
                        outputStream.close();
                }

                InputStream inputStream = httpUrlConn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                        buffer.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                httpUrlConn.disconnect();
                jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (Exception e) {
        	System.out.println("https request error:{}"+e.getMessage());
        }
        return jsonObject;
    }
	
//	public static void main(String[] args) {
//		byte[] btImg = RemoteFileUtil.getImageFromNetByUrl("http://192.168.8.165:8100/xweb/upload/sucai/audio/audio.amr");
//		if(null != btImg && btImg.length > 0){
//			String fileName = "audio2.amr";
//			RemoteFileUtil.writeImageToDisk(btImg, fileName, "//Users//jinyou//Downloads");
//		}
//	}
}
