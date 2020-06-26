package com.jeff.gift.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeff.gift.domain.DefaultGiftDivider;
import com.jeff.gift.domain.Funds;
import com.jeff.gift.domain.Gift;
import com.jeff.gift.domain.GiftRepository;
import com.jeff.gift.domain.Token;
import com.jeff.gift.domain.TokenRepository;
import com.jeff.gift.factory.TokenFactory;

@Transactional
@Service
public class GiftService {
	
	private final TokenFactory tokenFactory;
	private final GiftRepository giftRepository;
	private final TokenRepository tokenRepository;
	
	public GiftService(TokenFactory tokenFactory, GiftRepository giftRepository, TokenRepository tokenRepository) {
		this.tokenFactory = tokenFactory;
		this.giftRepository = giftRepository;
		this.tokenRepository = tokenRepository;
	}

	public Token createGifts(final Funds funds) {
		
		Token token = tokenFactory.create();
		
		Collection<Gift> gifts = funds.split(DefaultGiftDivider.INSTANCE
				, amount -> new Gift(token, amount));
		
		tokenRepository.save(token);
		giftRepository.saveAll(gifts);
		
		return token;
	}
}
