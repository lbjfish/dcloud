package com.sida.xiruo.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileEncodingGBK2UTF8 {
	private static final String SOURCE_ENCODING = "GBK";
	private static final String TARGET_ENCODING = "UTF-8";
	public static void main(String[] args) {
		action(new File("E:\\project\\QPlan\\src\\"));
	}
	
	public static void action(File dir) {
		// 读取文件内容
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			File[] tempList = dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".java")) {
						return true;
					}
					return false;
				}
			});

			System.out.println("该目录下对象个数：" + tempList.length);
			StringBuilder builder = new StringBuilder("");
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					builder.delete(0, builder.length());
					System.out.println("文     件：" + tempList[i]);

					// read file
					if (tempList[i].exists()) {
						FileInputStream fi = new FileInputStream(tempList[i]);
						InputStreamReader isr = new InputStreamReader(fi,
								SOURCE_ENCODING);
						BufferedReader bfin = new BufferedReader(isr);
						String rLine = "";
						while ((rLine = bfin.readLine()) != null) {
							// write file
							builder.append(rLine).append("\r\n");
						}
						bfin.close();
						isr.close();
					}
					fos = new FileOutputStream(tempList[i]);
					osw = new OutputStreamWriter(fos, TARGET_ENCODING);
					osw.write(builder.toString());
					osw.close();
					fos.close();

				} else if (tempList[i].isDirectory()) {
					action(tempList[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
