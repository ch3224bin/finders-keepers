package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class MyFundsException extends GlobalException {
	private static final long serialVersionUID = -2676106702180922321L;

	public MyFundsException(String userId) {
		super(ErrorCode.MY_FUNDS, String.format("userId : %s", userId));
	}

}
