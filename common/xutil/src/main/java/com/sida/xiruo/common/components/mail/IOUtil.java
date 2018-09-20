package com.sida.xiruo.common.components.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>IO操作基础类</p>
 * @author Lucky
 * 5:30:03 PM Jul 21, 2009 
 */
public class IOUtil {
	
	private static final Log logger = LogFactory.getLog(IOUtil.class);


	/**
	* <p>存储文件</p>
	* @param filePath 文件路径
	* @param in 文件输入流
	*/
	public static void saveFile(String filePath , InputStream in){
		FileOutputStream out =null;
		//存储附件的IO操作
		try {
			if ( in.available() > 0 ){
				out= new FileOutputStream(filePath);
				byte[] data = new byte[in.available()];
				in.read(data);
				out.write(data);
				out.close();
			}
			else{
				byte[] data = IOUtils.toByteArray(in);
				FileUtils.writeByteArrayToFile(new File(filePath), data);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}finally {
			try{
				if(out!=null) out.close();
			}catch(Exception e){}
		}
	}
}