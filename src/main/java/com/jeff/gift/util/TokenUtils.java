package com.jeff.gift.util;

import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class TokenUtils {
	
	private static final int[] codes;
	private static final int FULL_LENGTH = 6;
	
	static {
		Set<Integer> exclude = Set.of(22, 27, 38, 60, 61); // " ' ` & =
		IntPredicate urlSafetyFilter = i ->  !exclude.contains(i);
		codes = IntStream.range(33, 126).filter(urlSafetyFilter)
			.toArray();
	}
	
	public static String lpadFullLength(Long value) {
		return StringUtils.leftPad(String.valueOf(value), FULL_LENGTH, "0");
	}
	
	public static String switchSecondAndLast(String str) {
		char[] arr = str.toCharArray();
		int second = 1;
		int last = arr.length - 1;
		char temp = arr[second];
		arr[second] = arr[last];
		arr[last] = temp;
		return String.valueOf(arr);
	}
	
	public static String toCustomString(Long value) {
		int base = codes.length;
		StringBuilder answer = new StringBuilder();
		while (value > 0) {
			answer.insert(0, (char)codes[(int)(value % base)]);
			value = value / base;
		}
		
		return answer.toString();
	}
}
