package com.sida.xiruo.common.components;

public class CharUtils {

	public static boolean inArray(char c, char... cs) {
		if (cs == null) {
			return false;
		}
		for (char ch : cs) {
			if (ch == c) {
				return true;
			}
		}
		return false;
	}

	public static boolean notInArray(char c, char... cs) {
		return !CharUtils.inArray(c, cs);
	}

	public static char toChar(String str) {
		if (str.length() < 2) {
			return str.charAt(0);
		} else {
			throw new IllegalArgumentException(String.format(
					"String %s can not convert to a Character", str));
		}
	}
}
