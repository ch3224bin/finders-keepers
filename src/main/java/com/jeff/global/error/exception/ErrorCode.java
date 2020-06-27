package com.jeff.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	TOKEN_NOT_FOUND ("TOKEN_ERROR_01", "해당 토큰에 대한 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	GREEDY_PERSON ("GIFT_ERROR_01", "뿌리기 당 한 사용자는 한번만 받을 수 있습니다.", HttpStatus.FORBIDDEN),
	MY_FUNDS ("GIFT_ERROR_02", "자신이 뿌리기한 건은 자신이 받을 수 없습니다.", HttpStatus.FORBIDDEN),
	WRONG_ROOM ("GIFT_ERROR_03", "뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.", HttpStatus.FORBIDDEN),
	EXPIRED_TOKEN ("GIFT_ERROR_04", "뿌린 건은 10분간만 유효합니다.", HttpStatus.FORBIDDEN),
	SOLD_OUT ("GIFT_ERROR_05", "뿌리기가 전부 소진되었습니다.", HttpStatus.FORBIDDEN),
	NOT_MY_TOKEN ("GIFT_ERROR_06", "뿌린 사람 자신만 조회를 할 수 있습니다.", HttpStatus.FORBIDDEN),
	TIME_HAS_PASSED ("GIFT_ERROR_07", "뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.", HttpStatus.FORBIDDEN);
	
	private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
