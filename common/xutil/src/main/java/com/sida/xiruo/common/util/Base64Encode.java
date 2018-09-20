/**
 * 
 */
package com.sida.xiruo.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @author xjiang
 *
 */
public final class Base64Encode {
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-24
	 * @param input
	 * @return
	 * @see
	 */
	public static String encode(String input) {
		try {
			final Base64 base64 = new Base64();
	        byte[] bytes = base64.encode(input.getBytes(UtilConstants.DEFAULT_CHARSET));
	        String encodeString = new String(bytes, UtilConstants.DEFAULT_CHARSET);
	        return encodeString;
		} catch(Exception e) {
			e.printStackTrace(System.err);
			return input;
		}
	}
	
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-24
	 * @param input
	 * @return
	 * @see
	 */
	public static String decode(String input) {
		try {
			final Base64 base64 = new Base64();
	        byte[] bytes = base64.decode(input.getBytes(UtilConstants.DEFAULT_CHARSET));
	        String decodeString = new String(bytes, UtilConstants.DEFAULT_CHARSET);
	        return decodeString;
		} catch(Exception e) {
			e.printStackTrace(System.err);
			return input;
		}
	}
	

	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-24
	 * @param input
	 * @return
	 * @see
	 */
	public static String urlEncode(String input) {
		return encode(input).replaceAll("\\+", "!").replaceAll("\\/", "-");
	}
	
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-24
	 * @param input
	 * @return
	 * @see
	 */
	public static String urlDecode(String input) {
		return decode(input.replaceAll("!", "\\+").replaceAll("-", "\\/"));
	}
}
