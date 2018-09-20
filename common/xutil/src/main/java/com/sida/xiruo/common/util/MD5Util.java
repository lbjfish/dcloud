package com.sida.xiruo.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author jianglfa
 * @version 1.0
 * @created Jun 11, 2011 10:33:24 AM
 */
public final class MD5Util {
	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 获取文件夹中文件的MD5值
	 * 
	 * @param dir
	 * @param listChild
	 *            ;true递归子目录中的文件
	 * @return
	 */
	public static Map<String, String> getDirMD5(File dir, boolean listChild) {
		if (!dir.isDirectory()) {
			return null;
		}
		// <filepath,md5>
		Map<String, String> map = new HashMap<String, String>();
		String md5;
		File files[] = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory() && listChild) {
				map.putAll(getDirMD5(f, listChild));
			} else {
				md5 = getFileMD5(f);
				if (md5 != null) {
					map.put(f.getPath(), md5);
				}
			}
		}
		return map;
	}

    public static String encrypt(String origString) {    
        String origMD5 = null;    
        try {    
            MessageDigest md5 = MessageDigest.getInstance("MD5");    
            byte[] result = md5.digest(origString.getBytes());    
            origMD5 = byteArray2HexStr(result);    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return origMD5;    
    }    
       
    private static String byteArray2HexStr(byte[] bs) {    
        StringBuffer sb = new StringBuffer();    
        for (byte b : bs) {    
            sb.append(byte2HexStr(b));    
        }    
        return sb.toString();    
    }    
       
    private static String byte2HexStr(byte b) {    
        String hexStr = null;    
        int n = b;    
        if (n < 0) {    
            // 若需要自定义加密,请修改这个移位算法即可    
            n = b & 0x7F + 128;    
        }    
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);    
        return hexStr.toUpperCase();    
    }    
       
    public static String encrypt(String origString, int times) {    
        String md5 = encrypt(origString);    
        for (int i = 0; i < times - 1; i++) {    
            md5 = encrypt(md5);    
        }    
        return encrypt(md5);    
    }    
       
    public static boolean verifyPassword(String inputStr, String MD5Code) {    
        return encrypt(inputStr).equals(MD5Code);    
    }    
       
    public static boolean verifyPassword(String inputStr, String MD5Code,    
            int times) {    
        return encrypt(inputStr, times).equals(MD5Code);    
    }
	public static String MD5PWD(String str){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(str, null);
	}
}
