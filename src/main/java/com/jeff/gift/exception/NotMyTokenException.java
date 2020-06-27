package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class NotMyTokenException extends GlobalException {
	private static final long serialVersionUID = -1514261822620035994L;

	public NotMyTokenException(String token) {
		super(ErrorCode.NOT_MY_TOKEN, String.format("token : %s", token));
	}

}
