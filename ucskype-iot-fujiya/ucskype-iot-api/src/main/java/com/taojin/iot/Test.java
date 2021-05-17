package com.taojin.iot;

import org.apache.commons.codec.digest.DigestUtils;

public class Test {

	public static void main(String[] args) {
		String string = DigestUtils.md5Hex("123456");
		
		System.out.println(string);
	}
}
