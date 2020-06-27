package com.jeff.gift.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jeff.gift.domain.Sequence;
import com.jeff.gift.domain.SequenceRepository;
import com.jeff.global.context.UserInfoContext;

public class DefaultTokenCreaterTest {
	
	@BeforeEach
	public void setUp() {
		UserInfoContext.setUserInfo("1234", "1234");
	}
	
	@Test
	public void 토큰_생성_테스트() {
		
		DefaultTokenCreater creator = new DefaultTokenCreater(new MockSequenceRepository(1L));
		creator.setSecret(29);
		creator.setExpireDays(3);
		
		assertEquals(",r7", creator.create().getToken());
		
		creator = new DefaultTokenCreater(new MockSequenceRepository(2L));
		creator.setSecret(29);
		creator.setExpireDays(3);
		
		assertEquals("+_`", creator.create().getToken());
	}
	
	class MockSequenceRepository implements SequenceRepository {
		long value;
		MockSequenceRepository(long value) {
			this.value = value;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <S extends Sequence> S save(S entity) {
			return (S) new Sequence() {
				@Override
				public long getId() {
					return value;
				}
			};
		}

		@Override
		public List<Sequence> findAll() {
			return null;
		}

		@Override
		public List<Sequence> findAll(Sort sort) {
			return null;
		}

		@Override
		public List<Sequence> findAllById(Iterable<Long> ids) {
			return null;
		}

		@Override
		public <S extends Sequence> List<S> saveAll(Iterable<S> entities) {
			return null;
		}

		@Override
		public void flush() {
			
		}

		@Override
		public <S extends Sequence> S saveAndFlush(S entity) {
			return null;
		}

		@Override
		public void deleteInBatch(Iterable<Sequence> entities) {
			
		}

		@Override
		public void deleteAllInBatch() {
			
		}

		@Override
		public Sequence getOne(Long id) {
			return null;
		}

		@Override
		public <S extends Sequence> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Sequence> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public Page<Sequence> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public Optional<Sequence> findById(Long id) {
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
		public void delete(Sequence entity) {
			
		}

		@Override
		public void deleteAll(Iterable<? extends Sequence> entities) {
			
		}

		@Override
		public void deleteAll() {
			
		}

		@Override
		public <S extends Sequence> Optional<S> findOne(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Sequence> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Sequence> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends Sequence> boolean exists(Example<S> example) {
			return false;
		}
	}
}
