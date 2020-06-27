package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class TimeHasPassedException extends GlobalException {
	private static final long serialVersionUID = -7099397662597692808L;

	public TimeHasPassedException(String token) {
		super(ErrorCode.TIME_HAS_PASSED, String.format("token : %s", token));
	}

}
