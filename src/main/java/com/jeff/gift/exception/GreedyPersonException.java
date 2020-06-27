package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class GreedyPersonException extends GlobalException {
	private static final long serialVersionUID = -730157317801297106L;

	public GreedyPersonException(String userId) {
		super(ErrorCode.GREEDY_PERSON, String.format("userId : %s", userId));
	}
}
