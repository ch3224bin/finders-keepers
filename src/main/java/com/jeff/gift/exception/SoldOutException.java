package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class SoldOutException extends GlobalException {
	private static final long serialVersionUID = -9038523174508248551L;
	
	public SoldOutException(String token) {
		super(ErrorCode.SOLD_OUT, String.format("token : %s", token));
	}
}
