package com.jeff.gift.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

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
	
	@Column(name = "EARN_DATE")
	private LocalDateTime earnDate;
	
	@Column(name = "KEEPERS")
	private String keepers;
	
	@Version
	@Column(name = "VERSION")
	private int version;
	
	public Gift(Token token, long amount) {
		this.token = token;
		this.amount = amount;
	}
	
	public void gotcha(String keepers) {
		this.keepers = keepers;
		this.earnDate = LocalDateTime.now();
	}
}
