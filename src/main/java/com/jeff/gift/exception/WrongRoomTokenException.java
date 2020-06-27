package com.jeff.gift.exception;

import com.jeff.global.error.exception.ErrorCode;
import com.jeff.global.error.exception.GlobalException;

public class WrongRoomTokenException extends GlobalException {
	private static final long serialVersionUID = -4605610588134391754L;

	public WrongRoomTokenException(String roomId) {
		super(ErrorCode.WRONG_ROOM, String.format("roomId : %s", roomId));
	}

}
