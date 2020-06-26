package com.jeff.gift.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TOKEN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "CREATE_DATE", nullable = false)
	private LocalDateTime createDate;
	
	@Column(name = "CREATE_USER", nullable = false)
	private String createUser;
	
	@Column(name = "EXPIRE_DATE", nullable = false)
	private LocalDateTime expireDate;
	
	public Token(int expireDay) {
		this.expireDate = LocalDateTime.now().plusDays(expireDay);
	}

	@Override
	public String toString() {
		return token;
	}
}
