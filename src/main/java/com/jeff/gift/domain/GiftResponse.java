package com.jeff.gift.domain;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class GiftResponse {
	private final long amount;
	private final String keepers;
	
	private GiftResponse (long amount, String keepers) {
		this.amount = amount;
		this.keepers = keepers;
	}
	
	public static GiftResponse of(Gift gift) {
		return new GiftResponse(gift.getAmount(), gift.getKeepers());
	}
}
