/**
 * @(#) SystemUtil.java
 * Copyright (c) 2005-2010 XX��˾
 */

package com.sida.xiruo.common.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.imageio.ImageIO;

import com.sida.xiruo.common.components.encrypt.MD5;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sida.xiruo.common.components.encrypt.Base64;

/**
 * Comments : 
 * Author : jianglingfeng
 * Create Date : 2007-11-1
 * Modification history : 
 *   Sr Date Modified By Why & What is modified
 * 
 * @version 
 */

public abstract class SystemUtil {
//	private static final Logger LOG = Logger.getLogger(SystemUtil.class);
	
	public static int FIRST_UPPER_CASE=0;
	public static int FIRST_LOWER_CASE=1;
	
	public final static java.text.SimpleDateFormat sdformat =
		new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static java.text.SimpleDateFormat tableformat =
		new java.text.SimpleDateFormat("yyyyMM");
	public final static java.text.SimpleDateFormat yuefenFormat =
		new java.text.SimpleDateFormat("yyyy-MM");
	public final static java.text.SimpleDateFormat riqiFormat =
		new java.text.SimpleDateFormat("yyyy-MM-dd");
	  
	

	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-1
	 * @param dbEntity
	 * @param transferEntity
	 * @see
	 */
	public static void db2Transfer(Object dbEntity, Object transferEntity) {
		Class<? extends Object> dbClass = dbEntity.getClass();
		int i = 0;
		while(!dbClass.equals(java.lang.Object.class) && i < 10) {
			db2Transfer(dbEntity, transferEntity, dbClass);
			dbClass = dbClass.getSuperclass();
			i++;
		}
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param dbEntity
	 * @param transferEntity
	 * @param dbClass
	 * @see
	 */
	private static void db2Transfer(Object dbEntity, Object transferEntity, Class<? extends Object> dbClass) {
		final Class<? extends Object> transferClass = transferEntity.getClass();
		final Field[] fields = dbClass.getDeclaredFields();
		Method getMtd = null;
		Method setMtd = null;
		long lTime = 0;
		for(Field field:fields) {
			try {
				setMtd = transferClass.getMethod(
						"set" + convertName(field.getName(), FIRST_UPPER_CASE)
						, new Class[] {field.getType()});
				getMtd = dbClass.getMethod("get" + convertName(field.getName(), FIRST_UPPER_CASE)
						, new Class[] {});
				
				if(field.getType().equals(java.util.Date.class)) {
					lTime = ((java.util.Date)getMtd.invoke(dbEntity, new Object[] {})).getTime();
					setMtd.invoke(transferEntity, new Object[] {lTime});
				} else {
					setMtd.invoke(transferEntity, new Object[] {getMtd.invoke(dbEntity, new Object[] {})});
				}
			} catch(Exception e) {
				continue;
			}
		}
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-1
	 * @param dbEntity
	 * @param transferEntity
	 * @see
	 */
	public static void transfer2DB(Object transferEntity, Object dbEntity) {
		Class<? extends Object> transferClass = transferEntity.getClass();
		int i = 0;
		while(!transferClass.equals(java.lang.Object.class) && i < 10) {
			transfer2DB(transferEntity, dbEntity, transferClass);
			transferClass = transferClass.getSuperclass();
			i++;
		}
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param transferEntity
	 * @param dbEntity
	 * @param transferClass
	 * @see
	 */
	public static void transfer2DB(Object transferEntity, Object dbEntity, Class<? extends Object> transferClass) {
		final Class<? extends Object> dbClass = dbEntity.getClass();
		final Field[] fields = transferClass.getDeclaredFields();
		Method getMtd = null;
		Method setMtd = null;
		Constructor<? extends Object> constructor = null;
		long lTime = 0;
		for(Field field:fields) {
			try {
				setMtd = dbClass.getMethod(
						"set" + convertName(field.getName(), FIRST_UPPER_CASE)
						, new Class[] {field.getType()});
				getMtd = transferClass.getMethod("get" + convertName(field.getName(), FIRST_UPPER_CASE)
						, new Class[] {});
				
				if(field.getType().equals(java.util.Date.class)) {
					lTime = (java.lang.Long)getMtd.invoke(transferEntity, new Object[] {});
					constructor = field.getType().getConstructor(new Class[] {java.lang.Long.class});
					setMtd.invoke(dbEntity, new Object[] {constructor.newInstance(new Object[] {lTime})});
				} else {
					setMtd.invoke(dbEntity, new Object[] {getMtd.invoke(transferEntity, new Object[] {})});
				}
			} catch(Exception e) {
				continue;
			}
		}
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-1
	 * @param oldName
	 * @param firstCase
	 * @return
	 * @see
	 */
	public static String convertName(String oldName,int firstCase) {
	    if(firstCase == FIRST_LOWER_CASE) {
	    	return oldName.toLowerCase();
	    }
	    return oldName.substring(0,1).toUpperCase()+oldName.substring(1);
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param entity
	 * @return
	 * @see
	 */
	public static String entity2String(Object entity) {
		Class<? extends Object> entityClass = entity.getClass();
		final StringBuffer buffer = new StringBuffer();
		int i = 0;
		while(!entityClass.equals(java.lang.Object.class) && i < 10) {
			buffer.append(entity2String(entity, entityClass));
			entityClass = entityClass.getSuperclass();
			i++;
		}
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param entity
	 * @param entityClass
	 * @return
	 * @see
	 */
	private static String entity2String(Object entity, Class<? extends Object> entityClass) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("[ * classType -- " + entityClass.getName() + " * ]:\n");
		final Field[] fields = entityClass.getDeclaredFields();
		Method getMtd = null;
		for(Field field:fields) {
			try {
				getMtd = entityClass.getMethod("get" + convertName(field.getName(), FIRST_UPPER_CASE)
						, new Class[] {});
				buffer.append(field.getName() + " = ");
				if(field.getType().equals(java.util.Date.class)) {
					buffer.append(sdformat.format((java.util.Date)getMtd.invoke(entity, new Object[] {})));
				} else {
					buffer.append(getMtd.invoke(entity, new Object[] {}));					
				}
				buffer.append(",\n");
			} catch(NoSuchMethodException e) {
				continue;
			} catch(Exception e) {
				buffer.append(",\n");
			}
		}
		
		return buffer.toString();
	}
	
    /**
     * 
     * @param out
     * @param document
     * @throws IOException
     */
    public static void createXMLOutput(OutputStream out
            , Document document) throws IOException {
        OutputFormat format = OutputFormat.createCompactFormat();
        //format.setSuppressDeclaration(true);
        format.setEncoding("UTF-8");
        format.setIndent("\t");
        format.setLineSeparator("\r\n");
        format.setTrimText(true);
        format.setNewlines(true);
        XMLWriter output = new XMLWriter(out,format);
        output.write(document);
        output.close();
    }
    
    /**
     * 
     * @param out
     * @param document
     * @throws IOException
     */
    public static void createXMLOutput(Writer out
            , Document document) throws IOException {
        OutputFormat format = OutputFormat.createCompactFormat();
        //format.setSuppressDeclaration(true);
        format.setEncoding("UTF-8");
        format.setIndent("\t");
        format.setLineSeparator("\r\n");
        format.setTrimText(true);
        format.setNewlines(true);
        XMLWriter output = new XMLWriter(out,format);
        output.write(document);
        output.close();
    }
    
    /**
     * 
     * @author jianglingfeng
     * @date 2008-4-30
     * @param source
     * @return
     * @see
     */
    public static String filterHTML(final String source) {
    	String s = source;
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll("\\\"", "&quot;");
		s = s.replaceAll("\\n", "");
		s = s.replaceAll("\\r\\n", "<br />");
		s = s.replaceAll("\\r", "<br />");
		s = s.replaceAll(String.valueOf((char)9),"&nbsp;");
    	return s;
    }
    
    /**
     * 
     * @author jianglingfeng
     * @date 2008-4-8
     * @return
     * @see
     */
    public static String getTodayFolder() {
    	final Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/";
    }
    
    /**
     * 
     * @author jianglingfeng
     * @date 2008-4-8
     * @return
     * @see
     */
    public static String getMonthFolder() {
    	final Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/";
    }
    
    /**
     * 
     * @author jianglingfeng
     * @date 2008-6-18
     * @param source
     * @param len
     * @param s
     * @return
     * @see
     */
    public static String substring(final String source, final int len, final String s) {
    	final byte[] temp = source.getBytes();
    	if(len >= temp.length) {
    		return source;
    	}
    	
    	final byte[] byteArray = new byte[len];
        int ii = 0;
        for(int i = 0; i < len; i++) {    
            byteArray[i] = temp[i];
            
            if(temp[i] < 0){
                ii++;                
            }
        }
        
        if(ii%2 == 1){
            byteArray[len-1] = ' ';
        }
        
        return new String(byteArray).trim() + s;
    }
    
    /**
     * 复制文件
     * @author xjiang
     * @date 2010-8-18
     * @param source
     * @param target
     * @see
     */
    public static void copyFile(File source, File target) {// copy 文件   
        FileInputStream inFile = null;   
        FileOutputStream outFile = null;   
        try {   
        	target.getParentFile().mkdirs();
            inFile = new FileInputStream(source);   
            outFile = new FileOutputStream(target);   
            byte[] buffer = new byte[1024];   
            int i = 0;   
            while ((i = inFile.read(buffer)) != -1) {   
                outFile.write(buffer, 0, i);   
            }   
            inFile.close();   
            outFile.close();   
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (inFile != null) {   
                    inFile.close();   
                }   
                if (outFile != null) {   
                    outFile.close();   
                }   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }   
  
    /**
     * 复制目录   
     * @author xjiang
     * @date 2010-8-18
     * @param source
     * @param target
     * @see
     */
    public static void copyDict(File source, File target) {   
        File[] file = source.listFiles();// 得到源文件下的文件项目   
        for (int i = 0; i < file.length; i++) {   
            if (file[i].isFile()) {// 判断是文件   
                File sourceDemo = new File(source.getAbsolutePath() + "/"  
                        + file[i].getName());   
                File destDemo = new File(target.getAbsolutePath() + "/"  
                        + file[i].getName());   
                copyFile(sourceDemo, destDemo);   
            }   
            if (file[i].isDirectory()) {// 判断是文件夹   
                File sourceDemo = new File(source.getAbsolutePath() + "/"  
                        + file[i].getName());   
                File destDemo = new File(target.getAbsolutePath() + "/"  
                        + file[i].getName());   
                destDemo.mkdir();// 建立文件夹   
                copyDict(sourceDemo, destDemo);   
            }   
        }// end copyDict   
    }   

    /**
     * 
     * @author xjiang
     * @date 2010-8-28
     * @param src
     * @param fileName
     * @param folderPath
     * @param requireRename
     * @return
     * @throws Exception
     * @see
     */
    public static List<String> copy(File src, String fileName, String folderPath, boolean requireRename) throws Exception {
		return copy(new File[] {src}, new String[] {fileName}, folderPath, requireRename);
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2008-3-17
	 * @param srcs
	 * @param fileName
	 * @param folderPath
	 * @return
	 * @see
	 */
	public static List<String> copy(File srcs[], String fileName[], String folderPath) throws Exception {
		return copy(srcs, fileName, folderPath, true);
	}
	
    /**
     * 
     * @author jianglingfeng
     * @date 2008-3-17
     * @param srcs
     * @param fileName
     * @param folderPath
     * @see
     */
	public static List<String> copy(File srcs[], String fileName[], String folderPath, boolean requireDoFileName)
		throws Exception {
		final List<String> fileNameList = new ArrayList<String>();
		final String targetPathes[] = new String[srcs.length];
    	try {
//    		InputStream in = null ;
//    		OutputStream out = null ;
    		String dstPath = "";
    		int fileCount = srcs.length;
//    		int bufferSize = 1024;
    		String newFileName = "";
    		for(int i = 0 ; i < fileCount; i++) {
//    			bufferSize = 1024;
//	    		try {
	    			if(fileName == null) {
	    				newFileName = srcs[i].getName();
	    			} else if(requireDoFileName) {
	    				newFileName = doFileName(fileName[i], i);
	    			} else {
	    				newFileName = fileName[i];
	    			}
	    			fileNameList.add(newFileName);
	    			dstPath = folderPath + newFileName;
	    			targetPathes[i] = dstPath;
//	    			bufferSize = srcs[i].length() > bufferSize ? bufferSize : (int)srcs[i].length();
	    			copyFile(srcs[i], new File(dstPath));
//	    			in = new BufferedInputStream(new FileInputStream(srcs[i]), bufferSize);
//	    			out = new BufferedOutputStream(new FileOutputStream(dstPath), bufferSize);
//	                byte [] buffer = new byte [bufferSize];
//	                while(in.read(buffer) > 0 )  {
//	                	out.write(buffer);
//	                }
//	    		} finally  {
//	    			if ( null != in)  {
//	    				in.close();
//	    			}
//	    			if ( null != out)  {
//	                   out.close();
//	    			} 
//	    		} 
    		}
    		
    		//加水印
    		//insertWatermark(targetPathes);
    	} catch (Exception e)  {
    		e.printStackTrace();
    	}
    	
    	return fileNameList;
    }
	
    /**
     * 将文件名中插入当前时间以区别不同的文件
     * @author jianglingfeng
     * @date 2008-3-13
     * @param fileName
     * @param i
     * @return
     * @see
     */
    public static String doFileName(String fileName, int i)  {
        int index = fileName.lastIndexOf(".");
        if(index < 0) {
        	return fileName + "_" + System.currentTimeMillis();
        }
        final String name = fileName.substring(0, index) + "_" + (System.currentTimeMillis() + i) + fileName.substring(index);
        return name;
    }
    
    public static String doYuefenEnd(String yEnd) {
    	try {
	    	Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(yuefenFormat.parse(yEnd));
	    	calendar.add(Calendar.MONTH, 1);
	    	calendar.add(Calendar.DAY_OF_YEAR, -1);
	    	return riqiFormat.format(calendar.getTime());
    	} catch(Exception e) {
//    		LOG.error(e);
    		return yEnd;
    	}
    }
    
    public static boolean matchRegex(String input, String regex) {
    	boolean flag = false;
    	try {
	    	flag = Pattern.matches(regex, input);
    	} catch(PatternSyntaxException e) {
//    		LOG.info(e.toString());
    	}
    	return flag;
    }
    
    public static String readFileNoWrap(File file, String encoding) {
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    		StringBuilder builder = new StringBuilder("");
			String tmp = "";
			while((tmp = reader.readLine()) != null) {
				builder.append(tmp);
			}
			
			return builder.toString();
    	} catch(Exception e) {
			return null;
		} finally {
			if(null != reader) {
				try {
					reader.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
				reader = null;
			}
		}
    }
    
	public static String readFileNoWrap(File file){
		return readFileNoWrap(file, "GBK");
	}
	
	public static String readFileEspecially(File file, String start, String end) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			StringBuilder builder = new StringBuilder("");
			String tmp = "";
			boolean flag = false;
			while((tmp = reader.readLine()) != null) {
				if(!flag) {
					if(start.equals(filterString(tmp))) {
						flag = true;
					}
				} else {
					builder.append(tmp);
					if(end.equals(filterString(tmp))) {
						break;
					}
				}
			}
			
			return builder.toString();
		} catch(Exception e) {
			return null;
		} finally {
			if(null != reader) {
				try {
					reader.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
				reader = null;
			}
		}
	}
	
	public static String filterString(String source) {
		String target = source.replaceAll("\t", "").trim();
		return target;
	}
	
	/**
	 * 
	 * 描述   
	 * @param runnable
	 */
	public static void startNewThread(Runnable runnable) {
		startNewThread(runnable, false);
	}
	
	/**
	 * 
	 * 描述   
	 * @param runnable
	 * @param requireJoin
	 */
	public static void startNewThread(Runnable runnable, boolean requireJoin) {
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
		if(requireJoin) {
			try {
				thread.join();
			} catch(Exception e) {
//				LOG.error(e);
			}
		}
	}
	
	/**
	 * 生成缩略图
	 * @author jianglingfeng
	 * @date 2008-7-23
	 * @param imgName
	 * @param folderPath
	 * @param previewFolderPath
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static String createPreviewImage(final String imgName
			, final String folderPath
			, final String previewFolderPath) throws Exception {
		final List<String> imgList = new ArrayList<String>();
		imgList.add(imgName);
		return createPreviewImage(imgList, folderPath, previewFolderPath).get(0);
	}
	
	/**
	 * 生成缩略图
	 * @author jianglingfeng
	 * @date 2008-7-22
	 * @param imgList
	 * @param folderPath
	 * @param previewFolderPath
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static List<String> createPreviewImage(final List<String> imgList
			, final String folderPath
			, final String previewFolderPath) throws Exception {
		final List<String> previewNameList = new ArrayList<String>();
		String previewName = "";
		File sourceImgFile = new File(folderPath);
		if(!sourceImgFile.exists()) {
			sourceImgFile.mkdirs();
		}
		sourceImgFile = new File(previewFolderPath);
		if(!sourceImgFile.exists()) {
			sourceImgFile.mkdirs();
		}
		
		int sWidth = 0;
		int sHeight = 0;
		int tWidth = 0;
		int tHeight = 0;
		File targetImgFile = null;
		BufferedImage sourceImg = null;
		BufferedImage targetImg = null;
        Graphics2D g2d = null;
        FileOutputStream out = null;
        final String default_suffix = "prev_";
        final RenderingHints qualityHints =
        	new RenderingHints(RenderingHints.KEY_ANTIALIASING
        		, RenderingHints.VALUE_ANTIALIAS_ON);

		qualityHints.put(RenderingHints.KEY_RENDERING,
		        RenderingHints.VALUE_RENDER_QUALITY);
		
		int index = 0;
		String fileName = "";
//		ThumbnailConvert tc = new ThumbnailConvert();
		for(String imgFileName:imgList) {
			previewName = "";
			fileName = imgFileName.toLowerCase();
			index = fileName.lastIndexOf(".");
			if(index >= 0 && UtilConstants.ALLOW_PREVIEW_TYPE.indexOf("," + fileName.substring(index + 1) + ",") >= 0) {
				sourceImgFile = new File(folderPath + imgFileName);
				if(sourceImgFile.exists()) {
					previewName = default_suffix + transferJPGSuffix(imgFileName);
					targetImgFile = new File(previewFolderPath + previewName);
//				     tc.setCMYK_COMMAND(targetImgFile.getPath());//用于把cmyk转成rgb
					sourceImg = ImageIO.read(sourceImgFile);
					sWidth = sourceImg.getWidth();
					sHeight = sourceImg.getHeight();
					if(sWidth < UtilConstants.UPLOAD_PREVIEW_WIDTH
							&& sHeight < UtilConstants.UPLOAD_PREVIEW_HEIGHT) {
						tWidth = sWidth;
						tHeight = sHeight;
					} else if(sWidth > sHeight) {
						tWidth = UtilConstants.UPLOAD_PREVIEW_WIDTH;
						tHeight = UtilConstants.UPLOAD_PREVIEW_WIDTH * sHeight / sWidth;
					} else {
						tHeight = UtilConstants.UPLOAD_PREVIEW_HEIGHT;
						tWidth = UtilConstants.UPLOAD_PREVIEW_HEIGHT * sWidth / sHeight;
					}
					targetImg = new BufferedImage(tWidth
							, tHeight
							, BufferedImage.TYPE_INT_RGB);
					g2d = targetImg.createGraphics();
					g2d.setRenderingHints(qualityHints);
					
					g2d.drawImage(sourceImg
							, 0
							, 0
							, tWidth
							, tHeight
							, null);
					
					out = new FileOutputStream(targetImgFile);
					ImageIO.write(targetImg, "JPEG", out);
					out.close();
					sourceImg.flush();
					targetImg.flush();
				}
			}
			
			previewNameList.add(previewName);
		}
		
		return previewNameList;
	}
	
	/**
	 * 
	 * @author jianglingfeng
	 * @date 2008-7-22
	 * @param
	 * @return
	 * @see
	 */
	public static String transferJPGSuffix(final String fileName) {
		final int index = fileName.lastIndexOf(".");
		if(index >= 0) {
			return fileName.substring(0, index + 1) + "jpg";
		} else {
			return fileName;
		}
	}
	
	public static int statisticIntArray(int[] is) {
		int result = 0;
		for(int i : is) {
			result += i;
		}
		return result;
	}
	
	public static String getTimeSpanDisplay(long span) {
		StringBuilder builder = new StringBuilder();
		if(span <= 1000) {
			builder.append(span).append("毫秒");
		} else if(span / 1000 < 60) {
			builder.append(ArithUtil.div(span, 1000, 3)).append("秒");
		} else if(span / (1000 * 60) < 60) {
			long minute = span / (1000 * 60);//分钟
			long second = span / 1000;//秒
			builder
			.append(minute)
			.append("分钟")
			.append(second % 60)
			.append("秒");
		} else if(span / (1000 * 60 * 60) < 24) {
			long hour = span / (1000 * 60 * 60);//小时
			long minute = span / (1000 * 60);//分钟
			long second = span / 1000;//秒
			builder
			.append(hour)
			.append("小时")
			.append(minute % 60)
			.append("分钟")
			.append(second % 60)
			.append("秒");
		} else {
			long date = span / (1000 * 60 * 60 * 24);//天
			long hour = span / (1000 * 60 * 60);//小时
			long minute = span / (1000 * 60);//分钟
			long second = span / 1000;//秒
			builder
			.append(date)
			.append("天")
			.append(hour % 24)
			.append("小时")
			.append(minute % 60)
			.append("分钟")
			.append(second % 60)
			.append("秒");
		}
		
		return builder.toString();
	}
}
