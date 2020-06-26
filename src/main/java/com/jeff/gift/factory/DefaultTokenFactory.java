package com.jeff.gift.factory;

import org.springframework.stereotype.Component;

import com.jeff.gift.domain.Token;

@Component
public class DefaultTokenFactory implements TokenFactory {
	private final TokenCreater tokenCreater;
	
	public DefaultTokenFactory(TokenCreater tokenCreater) {
		this.tokenCreater = tokenCreater;
	}

	@Override
	public Token create() {
		return tokenCreater.create();
	}
}
