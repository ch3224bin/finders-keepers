package com.jeff.global.error.exception;

public class UnAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = -2231432399559627285L;
	
	public UnAuthenticationException(String message) {
		super(message);
	}
}
