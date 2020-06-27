package com.jeff.global.error.exception;

public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = -4195076423876878551L;

	private ErrorCode errorCode;
	private String  value;
	
	public GlobalException(ErrorCode errorCode, String value) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.value = value;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
	public String getValue() {
		return value;
	}
}
