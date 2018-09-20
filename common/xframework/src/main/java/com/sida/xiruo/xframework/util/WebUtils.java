package com.sida.xiruo.xframework.util;

public class WebUtils {
	/**
	 * 通过app端上传的图片路径转换成缩略图路径
	 * 1.1版本暂时使用tomcat放置图片
	 * 以后版本会使用静态服务器
	 * @param filePath
	 * @return
	 */
	public static String getPreviewImgPath(String filePath) {
		int index = filePath.lastIndexOf("/")+1;
		StringBuilder preview = new StringBuilder(
				filePath.substring(0, index).replace("files", "preview"))
				.append("prev_")
				.append(filePath.substring(index, filePath.lastIndexOf("."))) 
				.append(".jpg");
		return preview.toString();
	}
	/**
	 * 通过管理后台上传的图片路径转换成缩略图路径
	 * 1.1版本暂时使用tomcat放置图片
	 * 以后版本会使用静态服务器
	 * @param filePath
	 * @return
	 */
	public static String getPreviewImgPath2(String filePath) {
		int index = filePath.lastIndexOf("/")+1;
		int stuffIndex = filePath.lastIndexOf(".")+1;
		StringBuilder preview = new StringBuilder(
				filePath.substring(0, index).replace("files", "preview"))
				.append("prev_")
				.append(filePath.substring(index,stuffIndex))
				.append("jpg");//生成的略缩图全部是.jpg后缀的, 另外index不用减1了, 因为有些路径后面只有单根/,不是所有都是双//
		return preview.toString();
	}
}
