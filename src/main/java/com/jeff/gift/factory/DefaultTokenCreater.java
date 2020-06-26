package com.jeff.gift.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeff.gift.domain.Token;
import com.jeff.gift.domain.TokenRepository;
import com.jeff.gift.util.TokenUtils;

@Component
public class DefaultTokenCreater implements TokenCreater {
	
	private static final int TOKEN_LENGTH = 3;
	
	private int expireDays;
	private long secret;
	private final TokenRepository tokenRepository;
	
	public DefaultTokenCreater(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	@Value("${config.token.expire-days}")
	public void setExpireDays(int expireDays) {
		this.expireDays = expireDays;
	}

	@Value("${config.token.secret}")
	public void setSecret(long secret) {
		this.secret = secret;
	}

	@Override
	public Token create() {
		Token token = new Token(expireDays);
		tokenRepository.save(token);
		
		long value = token.getId() * secret;
		TokenUtils.toCustomString(value, TOKEN_LENGTH);
		
		return token;
	}

}
