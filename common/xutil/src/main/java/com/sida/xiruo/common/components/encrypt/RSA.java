package com.sida.xiruo.common.components.encrypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA encode and decode operation
 * 
 * @author L ky
 * 
 */
public class RSA {

	// RSA algorithm name
	private final static String Algorithm = "RSA";

	/**
	 * RSA decode input string, privateKey using Base64 encrypt
	 * 
	 * @param privateKey
	 * @param stext
	 * @return
	 */
	public static String decode(String privateKey, String stext) {
		return new String(decode(Base64.decode(privateKey.getBytes()), Base64
				.decode(stext.getBytes())));
	}

	/**
	 * RSA encode input string, publicKey using Base64 encrypt
	 * 
	 * @param p
	 *            licKey
	 * @param text
	 * @return
	 */
	public static String encode(String publicKey, String text) {
		return new String(Base64.encode(encode(Base64.decode(publicKey
				.getBytes()), text.getBytes())));
	}

	/**
	 * RSA decode input bytes
	 * 
	 * @param privateKey
	 * @param stext
	 * @return
	 */
	public static byte[] decode(byte[] privateKey, byte[] stext) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyf = KeyFactory.getInstance(Algorithm);
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, prikey);
			return cipher.doFinal(stext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA encode input bytes
	 * 
	 * @param p
	 *            licKey
	 * @param text
	 * @return
	 */
	public static byte[] encode(byte[] publicKey, byte[] text) {
		try {
			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
					publicKey);
			java.security.KeyFactory keyFactory = java.security.KeyFactory
					.getInstance(Algorithm);
			java.security.PublicKey pubKey = keyFactory
					.generatePublic(bobPubKeySpec);
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(text);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
