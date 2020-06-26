package com.jeff.gift.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "GIFT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class Gift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "TOKEN_ID", nullable = false, updatable = false)
	private Token token;
	
	@Column(name = "AMOUNT", nullable = false)
	private long amount;
	
	public Gift(Token token, long amount) {
		this.token = token;
		this.amount = amount;
	}
}
