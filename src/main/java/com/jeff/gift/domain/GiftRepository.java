package com.jeff.gift.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
	
	Optional<Gift> findByTokenAndKeepers(Token token, String userId);

	List<Gift> findByTokenAndEarnDateIsNull(Token token);
	
	List<Gift> findByToken(Token token);
}
