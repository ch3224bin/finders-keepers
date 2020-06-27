package com.jeff.gift.factory;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jeff.gift.domain.Sequence;
import com.jeff.gift.domain.SequenceRepository;
import com.jeff.gift.domain.Token;
import com.jeff.gift.util.TokenUtils;

@Component
public class DefaultTokenCreater implements TokenCreater {
	
	private static final int TOKEN_LENGTH = 3;
	
	private int expireMin;
	private long secret;
	private final SequenceRepository sequenceRepository;
	
	public DefaultTokenCreater(SequenceRepository sequenceRepository) {
		this.sequenceRepository = sequenceRepository;
	}
	
	@Value("${config.token.expire-min}")
	public void setExpireDays(int expireMin) {
		this.expireMin = expireMin;
	}

	@Value("${config.token.secret}")
	public void setSecret(long secret) {
		this.secret = secret;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Token create() {
		return Optional.of(sequenceRepository.save(new Sequence()).getId())
			.map(value -> value * secret)
			.map(value -> TokenUtils.lpadFullLength(value))
			.map(value -> TokenUtils.switchSecondAndLast(value))
			.map(value -> TokenUtils.toCustomString(Long.valueOf(value)))
			.map(value -> StringUtils.leftPad(value, TOKEN_LENGTH, "0"))
			.map(value -> new Token(value, expireMin))
			.get();
	}

}
