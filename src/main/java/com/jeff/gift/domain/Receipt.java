package com.jeff.gift.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Receipt {
	private LocalDateTime createdDate;
	private long amount;
	private long givenAmount;
	private Collection<GiftResponse> happyPeople;
	
	@Builder
	public Receipt(LocalDateTime createdDate, long amount, long givenAmount, Collection<GiftResponse> happyPeople) {
		this.createdDate = createdDate;
		this.amount = amount;
		this.givenAmount = givenAmount;
		this.happyPeople = happyPeople;
	}

	public static Receipt of(Token token, List<Gift> gifts) {
		return Receipt.builder()
				.createdDate(token.getCreatedDate())
				.amount(gifts.stream()
						.map(gift -> gift.getAmount())
						.reduce((a, b) -> a + b).get())
				.givenAmount(gifts.stream()
						.filter(gift -> gift.getEarnDate() != null)
						.map(gift -> gift.getAmount())
						.reduce((a, b) -> a + b).orElseGet(() -> 0L))
				.happyPeople(gifts.stream()
						.filter(gift -> gift.getEarnDate() != null)
						.map(GiftResponse::of)
						.collect(Collectors.toList()))
				.build();
	}
}
