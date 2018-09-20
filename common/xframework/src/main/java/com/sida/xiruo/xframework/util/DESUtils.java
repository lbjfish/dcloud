package com.sida.xiruo.xframework.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtils
{
    private static Key key;
    private static String KEY_STR="005795db-88f6-428e-ad0d-0610204a37e3";

    static{
        try
        {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom= SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator=null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对字符串进行加密，返回BASE64的加密字符串
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getEncryptString(String str){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try
        {
            byte[] strBytes = str.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return base64Encoder.encode(encryptStrBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * 对BASE64加密字符串进行解密
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDecryptString(String str){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try
        {
            byte[] strBytes = base64Decoder.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return new String(encryptStrBytes,"UTF-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args)
    {
            System.out.println(getDecryptString("hmlrHjgCVjc27XCa3Q15vw==="));//解密
            System.out.println(getDecryptString("bWHJNQPhvCHw0i+r48LHnQ=="));//解密
        String name ="clz_manager";
        String password="Clz_Zd16FV-b9";
        String encryname = getEncryptString(name);//加密用户名
        String encrypassword = getEncryptString(password);//加密密码
        System.out.println("encryname:"+encryname);
        System.out.println("encrypassword:"+encrypassword);

        System.out.println(getDecryptString(encryname));//解密
        System.out.println(getDecryptString(encrypassword));
    }
}