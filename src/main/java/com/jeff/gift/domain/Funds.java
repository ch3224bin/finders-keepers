package com.jeff.gift.domain;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.jeff.generic.domain.Divider;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Funds {
	@NotNull
	@Min(value = 1)
	private Long amount;
	
	@NotNull
	@Min(value = 1)
	private Integer people;
	
	public Funds(Long amount, Integer people) {
		this.amount = amount;
		this.people = people;
	}
	
	public Collection<Gift> split(Divider<Long, Integer, Long> divider, Function<Long, Gift> mapper) {
		return divider.divide(amount, people).stream()
				.map(mapper).collect(Collectors.toList());
	}
}
