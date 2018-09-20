package com.sida.xiruo.common.components;

import java.io.UnsupportedEncodingException;

import com.sida.xiruo.common.util.UtilConstants;

public class BytesUtils {

	public static byte[] toBytes(String str, String charSetName) {
		if (str != null) {
			try {
				return str.getBytes(charSetName);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalArgumentException("input string must not be null");
		}
	}

	public static byte[] toBytes(String str) {
		return toBytes(str, UtilConstants.DEFAULT_CHARSET);
	}

	public static byte[] toBytes(StringBuilder sb, String charSetName) {
		return toBytes(sb.toString(), charSetName);
	}

	public static byte[] toBytes(StringBuilder sb) {
		return toBytes(sb, UtilConstants.DEFAULT_CHARSET);
	}
}
