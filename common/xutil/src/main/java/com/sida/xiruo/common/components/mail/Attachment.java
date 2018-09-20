package com.sida.xiruo.common.components.mail;

import com.sida.xiruo.common.components.FileUtils;


/**
 * <p>附件</p>
 * @author Lucky
 * 11:06:54 AM Jun 24, 2009 
 */
public class Attachment {


	//文件名
	private String fileName;
	//文件大小
	private int fileSize;
	//文件路径
	private String filePath;
	
	/**
	* <p>获取文件名</p>
	* @return
	*/
	public String getFileName() {
		if(this.fileName == null){
			this.fileName = FileUtils.getFileName(filePath);
		}
		return fileName;
	}
	
	/**
	* <p>设置文件名</p>
	* @param fileName	文件名
	*/
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	* <p>获取文件大小</p>
	* @return
	*/
	public int getFileSize() {
		return fileSize;
	}
	
	/**
	* <p>设置文件大小</p>
	* @param fileSize 文件大小
	*/
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	/**
	* @return the filePath
	*/
	public String getFilePath() {
		return filePath;
	}


	/**
	* @param filePath the filePath to set
	*/
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


}
