package com.jeff.gift.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TokenUtilsTest {
	
	@Test
	public void test() {
		assertEquals("\"", TokenUtils.toCustomString(1L));
		assertEquals("#", TokenUtils.toCustomString(2L));
	}
}
