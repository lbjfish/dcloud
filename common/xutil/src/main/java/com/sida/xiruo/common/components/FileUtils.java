package com.sida.xiruo.common.components;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.sida.xiruo.common.components.collection.CharStack;
import com.sida.xiruo.common.components.collection.CollectionsUtils;
import org.springframework.util.Assert;


public class FileUtils {
	
	public static final int BUFFER_SIZE = 4096;




	public static List<File> getFileList(String path){
		File file = new File(path);		
		return getFileList(file);
	}
	
	public static List<File> getFileList(File file){
		File[] files = file.listFiles();
		if(files == null || files.length < 1){
			return null;
		}
		List<File> fileList = new ArrayList<File>();
		for(File f : files){
			if(!f.isDirectory()){
				fileList.add(f);
			}else{
				fileList = (List<File>) CollectionsUtils.contact(fileList , getFileList(f.getPath()));
			}
		}
		return fileList;
	}
	
	public static StringBuilder readFileToString(String path){
		BufferedReader reader = null;
		StringBuilder sb = null; 
		try{
			reader = new BufferedReader(new FileReader(path));
			sb = new StringBuilder();
			int c;
			c = reader.read();			
			while(c!=-1){
				sb.append((char)c);
				c = reader.read();				
			}	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}
	


	public static StringBuilder readFileToString(String path, String encoding){
		BufferedReader reader = null;
		StringBuilder sb = null; 
		try{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),encoding));
			sb = new StringBuilder();
			int c;
			c = reader.read();			
			while(c!=-1){
				sb.append((char)c);
				c = reader.read();				
			}	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}
	
	public static void saveStringToFile(String path, String content){
		makeDir(path);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(writer);
		}
	}
	
	public static void saveStringToFile(String path, StringBuilder content){
		saveStringToFile(path, content.toString());
	}
	
	public static void saveStringToFile(String path, String content, String encoding){
		makeDir(path);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
			writer.write(content);	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(writer);
		}
	}
	
	public static void saveStringToFile(String path, StringBuilder content, String encoding){
		saveStringToFile(path, content.toString(), encoding);
	}


	public static boolean deleteFileOrFolder(String path){
		return deleteFileOrFolder(new File(path));
	}
	
	public static boolean deleteFileOrFolder(File file){
		if(!file.exists()){
			throw new java.lang.IllegalArgumentException("file not found");
		}
		File[] childs = file.listFiles();
		if(childs == null || childs.length <1){//element
			
		}else{//not element
			for(File child : childs){
				deleteFileOrFolder(child);
			}
		}
		return file.delete();
	}
	
	public static String getExtensionName(String filePath){
		if(StringUtils.isBlank(filePath)){
			return StringUtils.EMPTY;
		}
		int lastDotPos = filePath.lastIndexOf(".");
		int lastPathPos = filePath.lastIndexOf(File.separator);
		if(lastPathPos == -1){
			lastPathPos = filePath.lastIndexOf("/");
		}
		if(lastDotPos != -1){
			if(lastPathPos > lastDotPos){
				return  StringUtils.EMPTY;
			}else{
				return filePath.substring(lastDotPos + 1);
			}
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	public static String getExtensionName(File file){
		return getExtensionName(file.getName());
	}
	
	public static String getFileName(String filePath){
		if(StringUtils.isBlank(filePath)){
			return StringUtils.EMPTY;
		}
		CharStack stack = new CharStack();
		stack.pushString(filePath);
		return stack.popUntil(File.separatorChar,'/');
	}
	
	public static boolean makeDir(String path){
		File file = new File(path);
		if(file.isDirectory()){
			
		}else{
			file = file.getParentFile();
		}
		if(!file.exists()){
			return file.mkdirs();
		}
		return false;
	}
	
	public static void copy(File sourceFile, String toPath){
		copy(sourceFile.getPath(), toPath);
	}
	
	public static void copy(String fromPath, String toPath){
		makeDir(toPath);
		try{
			InputStream is = new BufferedInputStream(new FileInputStream(fromPath));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(toPath));
			copy(is, os);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void move(String fromPath, String toPath){
		copy(fromPath, toPath);
		FileUtils.deleteFileOrFolder(fromPath);
	}
	
	public static void move(File sourceFile, String toPath){
		move(sourceFile.getPath(),toPath);
	}


	public static int copy(File in, File out) throws IOException {
		return copy(new BufferedInputStream(new FileInputStream(in)),
		   new BufferedOutputStream(new FileOutputStream(out)));
	}


	public static void copy(byte[] in, File out) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		copy(inStream, outStream);
	}


	public static byte[] copyToByteArray(File in) throws IOException {
		return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
	}


	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}


	public static void copy(byte[] in, OutputStream out) throws IOException {
		try {
			out.write(in);
		}
		finally {
			IOUtils.closeQuietly(out);
		}
	}


	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}


	public static int copy(Reader in, Writer out) throws IOException {
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}


	public static void copy(String in, Writer out) throws IOException {
		try {
			out.write(in);
		}
		finally {
			IOUtils.closeQuietly(out);
		}
	}


	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}
	
	public static List<File> getClassFiles(String parent, String packageDesc){
		Assert.hasText(packageDesc);
		packageDesc = packageDesc.replaceAll(".", File.separator);
		if(packageDesc.endsWith("*")){
			packageDesc = packageDesc.replace("*", "");
		}
		return FileUtils.getFileList(packageDesc);
	}
	
	public static long getAudioDuration(File file) throws IOException {  
        long duration = -1;  
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };  
        RandomAccessFile randomAccessFile = null;  
        try {  
            randomAccessFile = new RandomAccessFile(file, "rw");  
            long length = file.length();//文件的长度  
            int pos = 6;//设置初始位置  
            int frameCount = 0;//初始帧数  
            int packedPos = -1;  
            byte[] datas = new byte[1];//初始数据值  
            while (pos <= length) {  
                randomAccessFile.seek(pos);  
                if (randomAccessFile.read(datas, 0, 1) != 1) {  
                    duration = length > 0 ? ((length - 6) / 650) : 0;  
                    break;  
                }  
                packedPos = (datas[0] >> 3) & 0x0F;  
                pos += packedSize[packedPos] + 1;  
                frameCount++;  
            }  
            duration += frameCount * 20;//帧数*20  
        } finally {  
            if (randomAccessFile != null) {  
                randomAccessFile.close();  
            }  
        }  
        return duration;  
    }  
}