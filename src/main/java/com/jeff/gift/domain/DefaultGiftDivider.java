package com.jeff.gift.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jeff.generic.domain.Divider;

/**
 * 균등 분배
 */
public enum DefaultGiftDivider implements Divider<Long, Integer, Long> {
	INSTANCE;

	@Override
	public Collection<Long> divide(Long amount, Integer people) {
		if (people == 0) {
			throw new IllegalArgumentException("people must greater than zero.");
		}
		
		List<Long> result = new ArrayList<>();
		
		long total = amount.longValue();
		long p = people.longValue();
		long each = total /  p;
		
		for ( ; total >= each && each > 0; total -= each) {
			result.add(each);
		}
		
		if (total > 0) {
			if (result.size() == 0) {
				result.add(0L);
			}
			result.set(0, result.get(0) + total);
		}
		
		return result;
	}
}
