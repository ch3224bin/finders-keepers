package com.jeff.gift.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;

public class DefaultGiftDividerTest {

	@Test
	public void 나눈_금액의_합이_총액과_같아야한다() {
		DefaultGiftDivider divider = DefaultGiftDivider.INSTANCE;
		
		Long amount = Long.valueOf(10000L);
		Collection<Long> result = divider.divide(amount, 3);
		Long sum = result.stream().reduce((a,b) -> a + b).get();
		
		assertEquals(amount, sum);
		
		
		amount = Long.valueOf(1L);
		result = divider.divide(amount, 3);
		sum = result.stream().reduce((a,b) -> a + b).get();
		
		assertEquals(amount, sum);
	}
}
