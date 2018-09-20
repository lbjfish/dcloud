package com.sida.xiruo.common.components.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * <p>附件处理类</p>
 * @author Lucky
 * 6:22:42 PM Jul 22, 2009 
 */
public class AttachmentHandler {


	private static final Log logger = LogFactory.getLog(AttachmentHandler.class);
	/**
	* <p>解析邮件附件</p>
	* @param part JavaMail邮件
	* @return
	* @throws Exception
	*/
	protected static List<Attachment> extractAttachments(Part part) throws Exception{
		
		//附件名
		String fileName = null;
		//附件列表
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		try {
			fileName = StringUtils.isBlank(part.getFileName()) ?  null : MimeUtility.decodeText(part.getFileName()).trim();
		} catch (UnsupportedEncodingException e) {
			fileName = String.format("ATT%05d.dat",(attachmentList.size()+1));
		} catch (MessagingException e) {
			fileName = String.format("ATT%05d.dat",(attachmentList.size()+1));
		}
		
		if (part.isMimeType("multipart/*")) {//多媒体格式
			Multipart mp = (Multipart)part.getContent();
			for (int i = 0, c = mp.getCount(); i < c; i++)	{
				extractAttachments(mp.getBodyPart(i));
			}
		} else {
			String disposition = null;
			try {
				disposition = part.getDisposition();
			} 
			catch (MessagingException e) {
				if(part.getHeader("Content-Disposition") != null){
					String s = (String)part.getHeader("Content-Disposition")[0];
					if (StringUtils.isNotEmpty(s)){
						disposition = StringUtils.substringBefore(s+";",";");
					}
				}
			}
			if ( fileName != null || disposition != null && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE)) && fileName != null) {
				//将附件写进磁盘
				//存储文件名
				String saveFileName = fileName;
				
				//对于同名的文件，不停构造新文件名
				int i = 1;
				while(i < 10000){
					File file = new File(Global.getAttachmentDir() + saveFileName);				
					if(file.exists()){//文件名存在
						saveFileName = fileName + "_" + i;
						i++;
					}else{
						break;
					}
				}
				String fullFilePath = Global.getAttachmentDir() + saveFileName;
				logger.trace(String.format("save attachment , filepath: %s" , fullFilePath));
				IOUtil.saveFile(fullFilePath, part.getInputStream());
				Attachment attachment = new Attachment();
				attachment.setFileName(fileName);
				attachment.setFilePath(fullFilePath);
				
				if (attachment != null){
					attachmentList.add(attachment);
				}
			}
		}
		return attachmentList;
	}
	
}