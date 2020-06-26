package com.jeff.gift.util;

import org.junit.jupiter.api.Test;

public class TokenUtilsTest {
	
	@Test
	public void test() {
		System.out.println(TokenUtils.toCustomString(1L, 3));
		System.out.println(TokenUtils.toCustomString(2L, 3));
	}
}
