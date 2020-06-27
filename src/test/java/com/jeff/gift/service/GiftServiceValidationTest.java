package com.jeff.gift.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jeff.gift.domain.Gift;
import com.jeff.gift.domain.GiftRepository;
import com.jeff.gift.domain.Token;
import com.jeff.gift.domain.TokenRepository;
import com.jeff.gift.exception.ExpiredTokenException;
import com.jeff.gift.exception.GreedyPersonException;
import com.jeff.gift.exception.MyFundsException;
import com.jeff.gift.exception.NotMyTokenException;
import com.jeff.gift.exception.SoldOutException;
import com.jeff.gift.exception.TimeHasPassedException;
import com.jeff.gift.exception.WrongRoomTokenException;
import com.jeff.global.context.UserInfoContext;

public class GiftServiceValidationTest {

	@BeforeEach
	public void setUp() {
		UserInfoContext.setUserInfo("1234", "1234");
	}
	
	@Test
	public void 뿌리기_당_한_사용자는_한번만_받을_수_있습니다() {
		GiftRepository giftRepository = new MockGiftRepository(new Gift(new Token("1234", 10), 10000L));
		TokenRepository tokenRepository = new MockTokenRepository(new Token("1243", 10));
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(GreedyPersonException.class, () -> giftService.gotcha(""));
	}
	
	@Test
	public void 자신이_뿌리기한_건은_자신이_받을_수_없습니다() {
		GiftRepository giftRepository = new MockGiftRepository(null);
		TokenRepository tokenRepository = new MockTokenRepository(new Token("1243", 10));
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(MyFundsException.class, () -> giftService.gotcha(""));
	}
	
	@Test
	public void 뿌린기가_호출된_대화방과_동일한_대화방에_속한_사용자만이_받을_수_있습니다() {
		UserInfoContext.setUserInfo("1234", "2345");
		Token token = new Token("1243", 10);
		
		UserInfoContext.setUserInfo("3456", "1234");
		
		GiftRepository giftRepository = new MockGiftRepository(null);
		TokenRepository tokenRepository = new MockTokenRepository(token);
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(WrongRoomTokenException.class, () -> giftService.gotcha(""));
	}
	
	@Test
	public void 뿌린_건은_10분간만_유효합니다() {
		UserInfoContext.setUserInfo("1234", "2345");
		Token token = new Token("1243", -10);
		
		UserInfoContext.setUserInfo("3456", "2345");
		
		GiftRepository giftRepository = new MockGiftRepository(null);
		TokenRepository tokenRepository = new MockTokenRepository(token);
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(ExpiredTokenException.class, () -> giftService.gotcha(""));
	}
	
	@Test
	public void 모두_소진() {
		UserInfoContext.setUserInfo("1234", "2345");
		Token token = new Token("1243", 10);
		UserInfoContext.setUserInfo("3456", "2345");
		
		GiftRepository giftRepository = new MockGiftRepository(null, Collections.emptyList());
		TokenRepository tokenRepository = new MockTokenRepository(token);
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(SoldOutException.class, () -> giftService.gotcha(""));
	}
	
	@Test
	public void 뿌린_사람_자신만_조회를_할_수_있습니다() {
		UserInfoContext.setUserInfo("1234", "2345");
		Token token = new Token("1243", 10);
		UserInfoContext.setUserInfo("3456", "2345");
		
		GiftRepository giftRepository = new MockGiftRepository(null, Collections.emptyList());
		TokenRepository tokenRepository = new MockTokenRepository(token);
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		
		assertThrows(NotMyTokenException.class, () -> giftService.getReceipt(""));
	}
	
	@Test
	public void 뿌린_건에_대한_조회는_7일_동안_할_수_있습니다() {
		Token token = new Token("1243", 10);
		
		GiftRepository giftRepository = new MockGiftRepository(null, Collections.emptyList());
		TokenRepository tokenRepository = new MockTokenRepository(token);
		GiftService giftService = new GiftService(null, giftRepository, tokenRepository);
		giftService.setReadDays(-7);
		
		assertThrows(TimeHasPassedException.class, () -> giftService.getReceipt(""));
	}
	
	class MockGiftRepository implements GiftRepository {
		Gift gift;
		List<Gift> gifts;
		
		MockGiftRepository (Gift gift) {
			this.gift = gift;
			this.gifts = List.of(new Gift(new Token("123", 10), 10000L));
		}
		
		MockGiftRepository (Gift gift, List<Gift> gifts) {
			this.gift = gift;
			this.gifts = gifts;
		}

		@Override
		public List<Gift> findAll() {
			return null;
		}

		@Override
		public List<Gift> findAll(Sort sort) {
			return null;
		}

		@Override
		public List<Gift> findAllById(Iterable<Long> ids) {
			return null;
		}

		@Override
		public <S extends Gift> List<S> saveAll(Iterable<S> entities) {
			return null;
		}

		@Override
		public void flush() {
			
		}

		@Override
		public <S extends Gift> S saveAndFlush(S entity) {
			return null;
		}

		@Override
		public void deleteInBatch(Iterable<Gift> entities) {
			
		}

		@Override
		public void deleteAllInBatch() {
			
		}

		@Override
		public Gift getOne(Long id) {
			return null;
		}

		@Override
		public <S extends Gift> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Gift> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public Page<Gift> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Gift> S save(S entity) {
			return null;
		}

		@Override
		public Optional<Gift> findById(Long id) {
			return null;
		}

		@Override
		public boolean existsById(Long id) {
			return false;
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(Long id) {
			
		}

		@Override
		public void delete(Gift entity) {
			
		}

		@Override
		public void deleteAll(Iterable<? extends Gift> entities) {
			
		}

		@Override
		public void deleteAll() {
			
		}

		@Override
		public <S extends Gift> Optional<S> findOne(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Gift> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Gift> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends Gift> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public Optional<Gift> findByTokenAndKeepers(Token token, String userId) {
			return Optional.ofNullable(gift);
		}

		@Override
		public List<Gift> findByTokenAndEarnDateIsNull(Token tokenEntity) {
			return gifts;
		}

		@Override
		public List<Gift> findByToken(Token token) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	class MockTokenRepository implements TokenRepository {
		Token tokenEntity;
		MockTokenRepository (Token tokenEntity) {
			this.tokenEntity = tokenEntity;
		}
		
		@Override
		public List<Token> findAll() {
			return null;
		}

		@Override
		public List<Token> findAll(Sort sort) {
			return null;
		}

		@Override
		public List<Token> findAllById(Iterable<Long> ids) {
			return null;
		}

		@Override
		public <S extends Token> List<S> saveAll(Iterable<S> entities) {
			return null;
		}

		@Override
		public void flush() {
			
		}

		@Override
		public <S extends Token> S saveAndFlush(S entity) {
			return null;
		}

		@Override
		public void deleteInBatch(Iterable<Token> entities) {
			
		}

		@Override
		public void deleteAllInBatch() {
			
		}

		@Override
		public Token getOne(Long id) {
			return null;
		}

		@Override
		public <S extends Token> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Token> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public Page<Token> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Token> S save(S entity) {
			return null;
		}

		@Override
		public Optional<Token> findById(Long id) {
			return null;
		}

		@Override
		public boolean existsById(Long id) {
			return false;
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(Long id) {
			
		}

		@Override
		public void delete(Token entity) {
			
		}

		@Override
		public void deleteAll(Iterable<? extends Token> entities) {
			
		}

		@Override
		public void deleteAll() {
			
		}

		@Override
		public <S extends Token> Optional<S> findOne(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Token> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Token> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends Token> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public Optional<Token> findByToken(String token) {
			return Optional.of(tokenEntity);
		}
		
	}
}
