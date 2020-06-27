package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class TokenNotFoundException extends GlobalException {

	private static final long serialVersionUID = -3062583694289555308L;

	public TokenNotFoundException(String token) {
		super(ErrorCode.TOKEN_NOT_FOUND, String.format("token : %s", token));
	}
}
