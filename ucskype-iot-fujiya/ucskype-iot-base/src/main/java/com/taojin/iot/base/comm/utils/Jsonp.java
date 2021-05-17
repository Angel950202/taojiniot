package com.taojin.iot.base.comm.utils;


public class Jsonp {
	public String toJsonp(String str, String callback, String method) {
		return (callback != null) ? callback + "({\"" + method + "\":" + str
				+ "})" : "{\"" + method + "\":" + str + "}";
	}

	public String toJsonp(String[] str, String callback, String[] methods) {
		String result = "";
		if (callback != null) {
			result = callback + "({\"";
			for (int i = 0; i < methods.length; i++) {
				result += methods[i] + "\":" + str[i];
				if (i != methods.length - 1) {
					result += ",\"";
				}
			}
			result += "})";
		} else {
			result = "{\"";
			for (int i = 0; i < methods.length; i++) {
				result += methods[i] + "\":" + str[i];
				if (i != methods.length - 1) {
					result += ",\"";
				}
			}
			result += "}";
		}
		return result;
	}

	public String toJsonp(String str, int total, String callback, String method) {
		return (callback != null) ? callback + "({\"success\":true,\"total\":"
				+ total + ",\"" + method + "\":" + str + "})"
				: "{\"success\":true,\"total\":" + total + ",\"" + method
						+ "\":" + str + "}";
	}
}

