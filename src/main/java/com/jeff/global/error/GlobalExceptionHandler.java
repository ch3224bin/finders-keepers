package com.jeff.global.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jeff.global.error.exception.GlobalException;
import com.jeff.global.error.exception.UnAuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> giftException(GlobalException ex) {
		ApiError apiError =  new ApiError(ex.getErrorCode().getStatus(), ex.getLocalizedMessage(), ex.getErrorCode().getCode(), ex.getValue());
	    return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), null);
    }
	
	@ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Object> unAuthentication(UnAuthenticationException ex) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ApiError apiError =  new ApiError(status, ex.getLocalizedMessage(), String.valueOf(status.value()), "");
	    return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), null);
    }
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	     
	    ApiError apiError =  new ApiError(status, ex.getLocalizedMessage(), String.valueOf(status.value()), errors);
	    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, 
	  HttpStatus status, WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	     
	    ApiError apiError =  new ApiError(status, ex.getLocalizedMessage(), String.valueOf(status.value()), errors);
	    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
}
