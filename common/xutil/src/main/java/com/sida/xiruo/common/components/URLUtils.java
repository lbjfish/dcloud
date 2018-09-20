package com.sida.xiruo.common.components;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.sida.xiruo.common.util.UtilConstants;

public class URLUtils {

	// default charset name
	private static String httpProtocolStr = "http";
	private static String httpsProtocolStr = "https";
	private static String protocolSuffix = "://";

	public static String encode(String url, String charSetName) {
		try {
			String str = URLEncoder.encode(url, charSetName);
			return str.replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encode(String url) {
		return encode(url, UtilConstants.DEFAULT_CHARSET);
	}

	public static String decode(String url, String charSetName) {
		try {
			return URLDecoder.decode(url, charSetName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String decode(String url) {
		return decode(url, UtilConstants.DEFAULT_CHARSET);
	}

	public static boolean isUseHttpProtocol(String url) {
		if (StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(url)) {
			if (StringUtils.startsWithIgnoreCase(url, httpProtocolStr
					+ protocolSuffix)) {
				return true;
			} else if (StringUtils.startsWithIgnoreCase(url, httpsProtocolStr
					+ protocolSuffix)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static String absoluteUrl(String fromBaseUrl, String toBaseUrl,
			String fromUrl) {
		int index = fromUrl.length() - fromBaseUrl.length();
		if (index == -1) {
			return fromUrl;
		} else {
			return toBaseUrl + fromUrl.substring(fromUrl.length() - index);
		}
	}

	public static String getParameterValue(String parameter, String fullUrl) {
		String[] paramValuePairs = StringUtils.split(fullUrl, '&', false);
		for (String str : paramValuePairs) {
			KeyValuePair pair = KeyValuePair.formString(str);
			if (pair != null && pair.getKey().equals(parameter)) {
				return URLUtils.decode(pair.getValue());
			}
		}
		return null;
	}

	public static List<KeyValuePair> getKeyValueListFromUrl(String fullUrl) {
		List<KeyValuePair> keyValues = new ArrayList<KeyValuePair>();
		String[] paramValuePairs = StringUtils.split(fullUrl, '&', false);
		for (String str : paramValuePairs) {
			KeyValuePair pair = KeyValuePair.formString(str);
			if (pair != null) {
				keyValues.add(pair);
			}
		}
		return keyValues;
	}
}
