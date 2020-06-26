package com.jeff.gift.util;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class TokenUtils {
	
	private static final int[] codes;
	
	static {
		IntPredicate uriSafetyFilter = i -> i != 38 && i != 61;
		codes = IntStream.range(33, 126).filter(uriSafetyFilter)
			.toArray();
	}
	
	public static String toCustomString(Long value, int lpad) {
		int base = codes.length;
		StringBuilder answer = new StringBuilder();
		while (value > 0) {
			answer.insert(0, (char)codes[(int)(value % base)]);
			value = value / base;
		}
		
		return StringUtils.leftPad(answer.toString(), lpad, "0");
	}
}
