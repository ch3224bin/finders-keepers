package com.jeff.gift.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.jeff.global.context.UserInfoContext;

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
	
	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createdDate;
	
	@Column(name = "CREATE_USER", nullable = false)
	private String createUser;
	
	@Column(name = "EXPIRE_DATE", nullable = false)
	private LocalDateTime expireDate;
	
	@Column(name = "ROOM_ID", nullable = false)
	private String roomId;
	
	public Token(String token, int expireMin) {
		this.token = token;
		this.createUser = UserInfoContext.getUserInfo().getUserId();
		this.createdDate = LocalDateTime.now();
		this.expireDate = createdDate.plusMinutes(expireMin);
		this.roomId = UserInfoContext.getUserInfo().getRoomId();
	}

	@Override
	public String toString() {
		return token;
	}

	public boolean expired() {
		return expireDate.isBefore(LocalDateTime.now());
	}
}
