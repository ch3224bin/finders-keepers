package com.jeff.gift.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeff.gift.domain.DefaultGiftDivider;
import com.jeff.gift.domain.Funds;
import com.jeff.gift.domain.Gift;
import com.jeff.gift.domain.GiftRepository;
import com.jeff.gift.domain.Receipt;
import com.jeff.gift.domain.Token;
import com.jeff.gift.domain.TokenRepository;
import com.jeff.gift.exception.ExpiredTokenException;
import com.jeff.gift.exception.GreedyPersonException;
import com.jeff.gift.exception.MyFundsException;
import com.jeff.gift.exception.NotMyTokenException;
import com.jeff.gift.exception.SoldOutException;
import com.jeff.gift.exception.TimeHasPassedException;
import com.jeff.gift.exception.TokenNotFoundException;
import com.jeff.gift.exception.WrongRoomTokenException;
import com.jeff.gift.factory.TokenFactory;
import com.jeff.global.context.UserInfoContext;

@Transactional
@Service
public class GiftService {
	
	@PersistenceContext
	private EntityManager em;
	
	private int readDays;
	
	private final TokenFactory tokenFactory;
	private final GiftRepository giftRepository;
	private final TokenRepository tokenRepository;
	
	public GiftService(TokenFactory tokenFactory, GiftRepository giftRepository, TokenRepository tokenRepository) {
		this.tokenFactory = tokenFactory;
		this.giftRepository = giftRepository;
		this.tokenRepository = tokenRepository;
	}
	
	@Value("${config.token.read-days}")
	public void setReadDays(int readDays) {
		this.readDays = readDays;
	}

	public Token createGifts(final Funds funds) {
		Token token = tokenFactory.create();
		
		Collection<Gift> gifts = funds.split(DefaultGiftDivider.INSTANCE
				, amount -> new Gift(token, amount));
		
		tokenRepository.save(token);
		giftRepository.saveAll(gifts);
		
		return token;
	}

	public Gift gotcha(String token) {
		Token tokenEntity = getToken(token);
		List<Gift> gifts = giftRepository.findByTokenAndEarnDateIsNull(tokenEntity);
		
		validateGotcha(tokenEntity, gifts);
		
		Gift gift = gifts.get(new Random().nextInt(gifts.size()));
		em.lock(gift, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
		gift.gotcha(UserInfoContext.getUserInfo().getUserId());
		em.persist(gift);
		
		return gift;
	}
	
	public Receipt getReceipt(String token) {
		Token tokenEntity = getToken(token);
		validateReceipt(tokenEntity);
		
		List<Gift> gifts = giftRepository.findByToken(tokenEntity);
		return Receipt.of(tokenEntity, gifts);
	}
	
	Token getToken(String token) {
		return tokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundException(token));
	}
	
	void validateGotcha(Token token, List<Gift> gifts) {
		String userId = UserInfoContext.getUserInfo().getUserId();
		String roomId = UserInfoContext.getUserInfo().getRoomId();
		boolean hasGift = giftRepository.findByTokenAndKeepers(token, userId)
				.isPresent();
			
		if (hasGift) {
			throw new GreedyPersonException(userId);
		}
		
		if (Objects.equals(token.getCreateUser(), userId)) {
			throw new MyFundsException(userId);
		}
		
		if (!Objects.equals(token.getRoomId(), roomId)) {
			throw new WrongRoomTokenException(roomId);
		}
		
		if (token.expired()) {
			throw new ExpiredTokenException(token.getToken());
		}
		
		if (gifts.size() == 0) {
			throw new SoldOutException(token.getToken());
		}
	}
	
	void validateReceipt(Token token) {
		String userId = UserInfoContext.getUserInfo().getUserId();
		
		if (!Objects.equals(token.getCreateUser(), userId)) {
			throw new NotMyTokenException(token.getToken());
		}
		
		if (token.getCreatedDate().isAfter(token.getCreatedDate().plusDays(readDays))) {
			throw new TimeHasPassedException(token.getToken());
		}
	}
}
