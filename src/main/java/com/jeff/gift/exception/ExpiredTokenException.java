package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class ExpiredTokenException extends GlobalException {
	private static final long serialVersionUID = -3806676178455264651L;

	public ExpiredTokenException(String token) {
		super(ErrorCode.EXPIRED_TOKEN, String.format("token : %s", token));
	}

}
